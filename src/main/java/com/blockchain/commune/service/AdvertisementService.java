package com.blockchain.commune.service;

import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.SystemArgsEnum;
import com.blockchain.commune.enums.Wallet.SubAccountTypeEnum;
import com.blockchain.commune.enums.Wallet.TransDirectorEnum;
import com.blockchain.commune.enums.Wallet.TransTypeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.AdReplayMapper;
import com.blockchain.commune.mapper.AdvertisementMapper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.service.wallet.TransService;
import com.blockchain.commune.utils.ConvertUtil;
import com.blockchain.commune.utils.SensitiveWord;
import org.apache.commons.collections.CollectionUtils;
import com.blockchain.commune.customemapper.Advertisement.AdvertisementCustomMapper;
import com.blockchain.commune.model.Advertisement;
import com.blockchain.commune.model.AdvertisementCriteria;
import com.blockchain.commune.model.News;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wrb on 2018/9/22
 */
@Service
public class AdvertisementService {

    @Autowired
    AdvertisementMapper advertisementMapper;

    @Autowired
    UserService userService;

    @Autowired
    SystemService systemService;

    @Autowired
    TransService transService;

    @Autowired
    AdvertisementCustomMapper advertisementCustomMapper;

    @Autowired
    AdReplayMapper adReplayMapper;

    @Value("${web.upload-path}")
    private String uploadPath;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(AdvertisementService.class);

    public int insertAdvertisement(Advertisement advertisement) {
        return this.advertisementMapper.insertSelective(advertisement);
    }

    public int updateAdvertisementByKey(Advertisement advertisement) {
        return this.advertisementMapper.updateByPrimaryKeySelective(advertisement);
    }

    public Advertisement selectAdvertisementeByKey(String adId) {
        return this.advertisementMapper.selectByPrimaryKey(adId);
    }

    public int deleteAdvertisementByKey(String adId) {
        return this.advertisementMapper.deleteByPrimaryKey(adId);
    }


    /**
     * 获取用户当天发布的广告总额
     * @param userId
     * @return
     */
    public BigDecimal selectuserTodayAdvMoney(String userId) {
        BigDecimal bigDecimal = this.advertisementCustomMapper.selectuserTodayAdvMoney(userId);
        return bigDecimal;
    }

    /**
     * 获取未下架的当前新闻的所有广告
     * @param newsId
     * @param status
     * @return
     */
    public List<Advertisement> selectAdByNewsIdAndStatus(String newsId, byte status) {
        AdvertisementCriteria criteria = new AdvertisementCriteria();
        criteria.setOrderByClause("price desc,total_price desc");
        criteria.or().andNewsIdEqualTo(newsId).andStatusEqualTo(status);
        List<Advertisement> advertisementList = this.advertisementMapper.selectByExample(criteria);
        return advertisementList;
    }

    /**
     * 评论广告并奖励积分
     * @param userId
     * @param adReplay
     * @param advertisement
     * @return
     * @throws CommonException
     */
    @Transactional(rollbackFor=Exception.class)
    public BigDecimal commentAdvertisement(String userId, AdReplay adReplay, Advertisement advertisement) throws CommonException {
        User user = this.userService.selectUserByKey(userId);
        //如果是二级回复则插入回复并更新广告评论数
        if (adReplay.getReUserId() != null) {
            int ret = this.insertAdReply(adReplay);
            if (0 == ret) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "插入回复失败");
            }
            Advertisement advertisement1 = this.advertisementMapper.selectByPrimaryKey(advertisement.getAdId());
            advertisement1.setCommentNum(advertisement.getCommentNum() + 1);
            int ret1 = this.updateAdvertisementByKey(advertisement);
            if (0 == ret1) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "新增回复数失败");
            }
            return BigDecimal.ZERO;
        }
        //判断用户是否已回复过广告
        //用户已评论过，直接返回
        AdReplayCriteria criteria = new AdReplayCriteria();
        criteria.or().andUserIdEqualTo(userId).andAdIdEqualTo(advertisement.getAdId()).andReUserIdIsNull();
        List<AdReplay> adReplayList = this.adReplayMapper.selectByExample(criteria);
        if (!CollectionUtils.isEmpty(adReplayList)&& adReplay.getReUserId()==null) {
            int ret = this.insertAdReply(adReplay);
            if (0 == ret) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "插入回复失败");
            }
            Advertisement advertisement1 = this.advertisementMapper.selectByPrimaryKey(advertisement.getAdId());
            advertisement1.setCommentNum(advertisement.getCommentNum() + 1);
            int ret1 = this.updateAdvertisementByKey(advertisement);
            if (0 == ret1) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "新增回复数失败");
            }
            return BigDecimal.ZERO;
        }
        //用户未回复过
        //扣除广告得剩余量，增加评论数
        BigDecimal lastPrice = advertisement.getLastPrice().subtract(advertisement.getPrice());
        BigDecimal price = advertisement.getPrice().divide(new BigDecimal(2));
        advertisement.setLastPrice(lastPrice);
        advertisement.setCommentNum(advertisement.getCommentNum() + 1);
        int ret2 = this.updateAdvertisementByKey(advertisement);
        if (ret2 == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "更新广告剩余量失败");
        }
        //剩余量不足以扣除下次单价金额
        if (lastPrice.compareTo(BigDecimal.ZERO) <= 0 || lastPrice.compareTo(advertisement.getPrice()) < 0) {
            // 返还积分给用户
            this.refundAdvertisement(advertisement.getUserId(), advertisement.getAdId(), "广告余额不足或已用完自动下架");
        }
        //插入评论
        int ret = this.insertAdReply(adReplay);
        if (0 == ret) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "插入回复论失败");
        }
        //公社得积分
        List<SystemArgs> systemArgsList = this.systemService.selectSystemArgsById(SystemArgsEnum.RRGS_USERID.toString());
        if (CollectionUtils.isEmpty(systemArgsList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "公社账户Id未设置");
        }
        String rrgsUserId = systemArgsList.get(0).getSysValue();
        User rrgsUser = this.userService.selectUserByKey(rrgsUserId);
        if (null == rrgsUser) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "公社账户Id为设置");
        }
        String transTitle = "回复广告获得积分";
        String remark = "回复广告获得" + price + "积分";
        this.transService.transFlow(rrgsUserId, SubAccountTypeEnum.DEFAULT, price, transTitle, remark, TransDirectorEnum.IN, TransTypeEnum.AD_COMMENT, false);
        //用户的积分
        this.transService.transFlow(userId, SubAccountTypeEnum.DEFAULT, price, transTitle, remark, TransDirectorEnum.IN, TransTypeEnum.AD_COMMENT, false);

        return price;
    }

    /**
     * 根据新闻ID查询前三上架的广告，并根据总额排序
     * @param newsId
     * @return
     * @throws CommonException
     */
    public List<Advertisement> selectAdvertisementByNews(String newsId) throws CommonException {
        if (StringUtils.isEmpty(newsId)) {
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"新闻ID为空");
        }
        AdvertisementCriteria advertisementCriteria = new AdvertisementCriteria();
        AdvertisementCriteria.Criteria criteria = advertisementCriteria.createCriteria();
        criteria.andNewsIdEqualTo(newsId);
        criteria.andStatusEqualTo(new Byte("1"));
        advertisementCriteria.setOrderByClause(" price desc,total_price desc limit 0,3 ");
        List<Advertisement> advertisements = advertisementMapper.selectByExample(advertisementCriteria);
        return advertisements;
    }

    /**
     * 匹配一条价格最低的广告，将其替换下架
     * @param price
     * @return
     * @throws CommonException
     */
    public Advertisement selectNewsADV(BigDecimal price) throws CommonException {
        //匹配一条价格最低的广告，将其替换下架
        return advertisementCustomMapper.selectNewsHaveAdv(price);
    }

    /**
     * 匹配一条可插入广告且优先级高的新闻
     * @return
     * @throws CommonException
     */
    public News selectNews() throws CommonException {
        //匹配一条可插入广告且优先级高的新闻
        return advertisementCustomMapper.selectNewsNotAdv();
    }

    public int insertAdReply(AdReplay adReplay) {
        if (StringUtils.isBlank(adReplay.getReUserId())) {
            adReplay.setReUserId(null);
            return this.adReplayMapper.insertSelective(adReplay);
        }else {
            return this.adReplayMapper.insertSelective(adReplay);
        }
    }

    public AdReplay selectAdReplayByKey(String replayId) {
        return this.adReplayMapper.selectByPrimaryKey(replayId);
    }

    /**
     * 广告主查询自己发布的广告
     * @return
     * @throws CommonException
     */
    public List<Advertisement> selectAdvertisement(String userId,String total_desc,
                           String onePrice_desc,String status,Integer page,Integer pageSize) throws CommonException {
        User user = userService.selectUserByKey(userId);
        if(user==null){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"用户不存在");
        }
        AdvertisementCriteria advertisementCriteria = new AdvertisementCriteria();
        AdvertisementCriteria.Criteria criteria = advertisementCriteria.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if("1".equals(status)){
            criteria.andStatusEqualTo(new Byte(status));
        }else if("0".equals(status)){
            criteria.andStatusEqualTo(new Byte(status));
        }
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        StringBuffer str = new StringBuffer();
        str.append("create_time desc");
        if("ASC".equals(onePrice_desc)){
            str.append(",price ASC");
        }else{
            str.append(",price DESC");
        }
        if("ASC".equals(total_desc)){
            str.append(",total_price ASC");
        }else{
            str.append(",total_price DESC");
        }
        str.append(" limit "+page * pageSize+","+ pageSize);
        advertisementCriteria.setOrderByClause(str.toString());
        List<Advertisement> advertisements = advertisementMapper.selectByExample(advertisementCriteria);
        return advertisements;
    }


    /**
     * 广告下架退款
     * @return
     * @throws CommonException
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean refundAdvertisement(String userId,String adId,String remark) throws CommonException {
        User user = userService.selectUserByKey(userId);
        if(user==null){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"用户不存在");
        }
        Advertisement advertisement = selectAdvertisementeByKey(adId);
        if (advertisement == null) {
            throw new CommonException(ErrorCodeEnum.EXCEPTION, "此广告不存在");
        }
        //查看是否有剩余金额
        BigDecimal lastPrice = advertisement.getLastPrice();
        advertisement.setStatus(new Byte("0"));
        advertisement.setUpdateTime(new Date());
        advertisement.setRemark(remark);
        advertisement.setLastPrice(BigDecimal.ZERO);
        int i = updateAdvertisementByKey(advertisement);
        if (i > 0 ){
            //下架成功,有则反还
            if(lastPrice.compareTo(BigDecimal.ZERO)>0){
                transService.transFlow(userId,SubAccountTypeEnum.DEFAULT,
                        lastPrice,"退还剩余广告费","退还剩余广告费",
                        TransDirectorEnum.IN,TransTypeEnum.REFUND_ADVERTISEMENT,false);
            }
            return true;
        }else{
            throw new CommonException(ErrorCodeEnum.DBERROR,"下架失败");
        }

    }

    /**
     * 查询回复列表
     * @param adId
     * @return
     */
    public HashMap<String,Object> queryReplayList(String adId,Integer page,Integer pageSize){
        AdReplayCriteria commentReplayCriteria = new AdReplayCriteria();
        commentReplayCriteria.createCriteria().andAdIdEqualTo(adId);

        if (page == null){
            page = 0;
        }

        if (pageSize == null){
            pageSize = 10  ;
        }

        String format = String.format(" create_time desc limit %d,%d", page * pageSize, pageSize);
        commentReplayCriteria.setOrderByClause(format);

        List<AdReplay> commentReplays = adReplayMapper.selectByExample(commentReplayCriteria);
        long count = adReplayMapper.countByExample(commentReplayCriteria);
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        if (CollectionUtils.isEmpty(commentReplays)) {
            commentReplays = new ArrayList<AdReplay>();
        }
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("replayList",commentReplays);
        hashMap.put("page",pageObject);
        return hashMap;
    }

    public boolean checkSensitiveWord(String context) {
        SensitiveWord sw = new SensitiveWord(this.uploadPath + "CensorWords.txt");
        sw.InitializationWork();
        String str = context;
        str = str.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& ;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        String reg = ".*[QqvV群][1-9][0-9]{4,}.*";
        if (str.matches(reg)) {
            return false;
        }
        boolean flag = true;
        flag = sw.containSensitiveWord(str);
        return flag;
    }

}
