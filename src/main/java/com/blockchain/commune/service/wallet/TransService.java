package com.blockchain.commune.service.wallet;


import com.blockchain.commune.config.Constant;
import com.blockchain.commune.customemapper.attendance.AttendanceCustomMapper;
import com.blockchain.commune.customemapper.userAccessRecord.UserAccessCustomMapper;
import com.blockchain.commune.customemapper.wallet.CustomTransFlowMapper;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.SystemArgsEnum;
import com.blockchain.commune.enums.Wallet.*;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.*;
import com.blockchain.commune.model.*;
import com.blockchain.commune.service.UserRecommendedCodeService;
import com.blockchain.commune.utils.DateUtil;
import com.blockchain.commune.utils.IdUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.blockchain.commune.enums.Wallet.TransDirectorEnum.IN;

@Component
public class TransService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ReadRecordMapper readRecordMapper;

    @Autowired
    NewsMapper newsMapper;
    
    @Autowired
    WalletAccountMapper walletAccountMapper;

    @Autowired
    WalletSubAccountMapper walletSubAccountMapper;

    @Autowired
    WalletTranslogMapper walletTranslogMapper;

    @Autowired
    WalletDailySummaryMapper walletDailySummaryMapper;

    @Autowired
    AttendanceMapper attendanceMapper;

    @Autowired
    AttendanceCustomMapper attendanceCustomMapper;

    @Autowired
    WalletTranslogTypeMapper walletTranslogTypeMapper;

    @Autowired
    SystemArgsMapper systemArgsMapper;

    @Autowired
    UserRecommendedCodeService userRecommendedCodeService;

    @Autowired
    CustomTransFlowMapper customTransFlowMapper;

    @Autowired
    UserAccessCustomMapper userAccessCustomMapper;

    private Logger logger = LoggerFactory.getLogger(TransService.class);
    
    /***
     * 用于增加阅读文章的积分，注意避免同样文章只能有一次，不能二次认领
     *
     * @param userId 用户id
     * @param postId 文章id
     * @throws CommonException 通用异常
     */
    @Transactional(rollbackFor=Exception.class)
    public void addPostScore(String userId,String postId) throws CommonException{

        //1、阅读记录表新增记录，判断新增记录的文章用户是否已读，不存在则新增记录，否则不进行操做
        //2、修改子账户表的冻结余额
        //3、新增日结记录、交易记录

        //阅读记录表新增记录
        //获取随机积分数
        String transConfig = getTransConfig(TransTypeEnum.READ);
        String[] str = transConfig.split(",");

        Random random = new Random();
        int score;
        if (Integer.parseInt(str[0]) == 0){
            score = Integer.parseInt(str[1]);
        }else {
            score = random.nextInt(Integer.parseInt(str[0])+1)+Integer.parseInt(str[0]);
        }
        BigDecimal score2 = new BigDecimal(score);

        //根据userId查询用户名
        User user = userMapper.selectByPrimaryKey(userId);
        String username = user.getLoginName();

        //根据postId查询文章标题
        News news = newsMapper.selectByPrimaryKey(postId);
        String title = news.getTitle();

        ReadRecordCriteria re = new ReadRecordCriteria();
        ReadRecordCriteria.Criteria reCriteria = re.createCriteria().andPostIdEqualTo(postId).andUserIdEqualTo(userId);

        List<ReadRecord> readRecords = readRecordMapper.selectByExample(re);
        if (CollectionUtils.isEmpty(readRecords)){

            ReadRecord readRecord = new ReadRecord();

            String id = IdUtil.getId();//生成id
            readRecord.setId(id);
            readRecord.setUserId(userId);
            readRecord.setUserName(username);
            readRecord.setPostId(postId);
            readRecord.setTitle(title);
            readRecord.setScore(score2);

            int insert = readRecordMapper.insertSelective(readRecord);
            if (insert == 0){
                throw new CommonException(ErrorCodeEnum.DBERROR,"新增阅读记录失败");
            }
            //更新交易流程
            transFlow(userId,SubAccountTypeEnum.DEFAULT,score2,"阅读得积分","阅读文章，获得积分",
                    IN,TransTypeEnum.READ,false);

        }else {
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"重复阅读");
        }
    }

    /**
     * 查询阅读获得的积分
     * @param userId
     * @param newsId
     * @return
     */
    public BigDecimal getScore(String userId,String newsId){
        ReadRecordCriteria rrc = new ReadRecordCriteria();
        ReadRecordCriteria.Criteria criteria = rrc.createCriteria().andUserIdEqualTo(userId).andPostIdEqualTo(newsId);

        List<ReadRecord> readRecords = readRecordMapper.selectByExample(rrc);
        return readRecords.get(0).getScore();
    }

    /**
     * 判断重复阅读
     * @param userId
     * @param postId
     * @return
     * @throws CommonException
     */
    public boolean getRead(String userId,String postId)throws CommonException{

        ReadRecordCriteria re = new ReadRecordCriteria();
        ReadRecordCriteria.Criteria reCriteria = re.createCriteria().andPostIdEqualTo(postId).andUserIdEqualTo(userId);

        List<ReadRecord> readRecords = readRecordMapper.selectByExample(re);
        if (!CollectionUtils.isEmpty(readRecords)){
            return true;
        }else {
            return false;
        }
    }

    public String getTransConfig(TransTypeEnum transTypeEnum) throws CommonException {
        WalletTranslogTypeCriteria criteria = new WalletTranslogTypeCriteria();
        criteria.or().andTransTypeEqualTo(transTypeEnum.toString());
        List<WalletTranslogType> walletTranslogTypeList = walletTranslogTypeMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(walletTranslogTypeList)) {
            throw new CommonException(ErrorCodeEnum.PARAMETERROR,"交易类型不存在");
        }
        return walletTranslogTypeList.get(0).getTransConfig();
    }

    /***
     * 用户注册积分，推荐人也会获得积分
     * @param userId
     * @param topRecommendedCode 推荐用户的推荐码
     * @throws CommonException
     */
    @Transactional(rollbackFor=Exception.class)
    public void addRegisterScore(String userId, String userName, String topRecommendedCode) throws CommonException {

        //新增钱包账号
        WalletAccount walletAccount = new WalletAccount();
        String accountId = IdUtil.getAccountId();

        walletAccount.setAccountId(accountId);
        walletAccount.setUserId(userId);
        walletAccount.setUserName(userName);
        //注册，状态默认为有效
        walletAccount.setAccountStatus(AccountStatusEnum.EFFECTIVE.toString());
        int ret = this.walletAccountMapper.insertSelective(walletAccount);
        if (ret == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "新增钱包账号失败");
        }

        //新增两个子账号，一个默认子账号，一个赠送子账号
        //1.默认子账号
        WalletSubAccount walletSubAccount1 = new WalletSubAccount();
        String subAccountId1 = IdUtil.getSubAccountId();

        walletSubAccount1.setSubAccountId(subAccountId1);
        walletSubAccount1.setAccountId(accountId);
        walletSubAccount1.setSubAccountType(SubAccountTypeEnum.DEFAULT.toString());
        int ret1 = this.walletSubAccountMapper.insertSelective(walletSubAccount1);
        if (ret1 == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "新增默认子账号失败");
        }

        //2.赠送子账号
        WalletSubAccount walletSubAccount2 = new WalletSubAccount();
        String subAccountId2 = IdUtil.getSubAccountId();

        walletSubAccount2.setSubAccountId(subAccountId2);
        walletSubAccount2.setAccountId(accountId);
        walletSubAccount2.setSubAccountType(SubAccountTypeEnum.PRESENTATION.toString());
        int ret2 = this.walletSubAccountMapper.insertSelective(walletSubAccount2);
        if (ret2 == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "新增赠送子账号失败");
        }
    }

    /**
     * 实名认证后送积分
     * @param userId
     * @throws CommonException
     */
    @Transactional(rollbackFor=Exception.class)
    public void addUserAuthScore(String userId) throws CommonException {
        //获取注册所得积分
        String transConfig = this.getTransConfig(TransTypeEnum.REGISTERED);
        BigDecimal score = new BigDecimal(transConfig);
        User user = userMapper.selectByPrimaryKey(userId);
        String topRecommendedCode = user.getRecommendId();
        //注册送积分
        //判断是否累计登录7天
        int check = this.userAccessCustomMapper.countByLoginType(user.getUserId());
        if (check >= 7) {
            transFlow(userId, SubAccountTypeEnum.DEFAULT, score, "注册送积分", "注册成功，送" + score + "积分", IN, TransTypeEnum.REGISTERED, false);
            //推荐人获得积分
            if (StringUtils.isNotBlank(topRecommendedCode)) {
           /* if (topRecommendedCode.equals("666666")) {
                return;
            }*/
                //一级推荐得积分
                UserRecommendedCode userRecommendedCode1 = this.userRecommendedCodeService.selectUserRecommendedCodeByKey(topRecommendedCode);
                String recTransConfig1 = this.getTransConfig(TransTypeEnum.RECOMMEND1);
                BigDecimal recommendScore1 = new BigDecimal(recTransConfig1);
                transFlow(userRecommendedCode1.getUserId(), SubAccountTypeEnum.DEFAULT, recommendScore1, "推荐得积分", "推荐一位用户注册成功，送" + recommendScore1 + "积分", IN, TransTypeEnum.RECOMMEND1, false);
                User user1 = this.userMapper.selectByPrimaryKey(userRecommendedCode1.getUserId());
                //二级推荐得积分
                UserRecommendedCode userRecommendedCode2 = this.userRecommendedCodeService.selectUserRecommendedCodeByKey(user1.getRecommendId());
                if (null != userRecommendedCode2) {
                    String recTransConfig2 = this.getTransConfig(TransTypeEnum.RECOMMEND2);
                    BigDecimal recommendScore2 = new BigDecimal(recTransConfig2);
                    transFlow(userRecommendedCode2.getUserId(), SubAccountTypeEnum.DEFAULT, recommendScore2, "推荐得积分", "下级用户推荐一位用户注册成功，送" + recommendScore2 + "积分", IN, TransTypeEnum.RECOMMEND2, false);
                }
            }
        }else {
            //累计登录小于7天
            transFlow(userId, SubAccountTypeEnum.DEFAULT, score, "注册送积分", "注册成功，送" + score + "积分", IN, TransTypeEnum.REGISTERED, true);

            //推荐人获得积分
            if (StringUtils.isNotBlank(topRecommendedCode)) {
           /* if (topRecommendedCode.equals("666666")) {
                return;
            }*/
                //一级推荐得积分
                UserRecommendedCode userRecommendedCode1 = this.userRecommendedCodeService.selectUserRecommendedCodeByKey(topRecommendedCode);
                String recTransConfig1 = this.getTransConfig(TransTypeEnum.RECOMMEND1);
                BigDecimal recommendScore1 = new BigDecimal(recTransConfig1);
                transFlow(userRecommendedCode1.getUserId(), SubAccountTypeEnum.DEFAULT, recommendScore1, "推荐得积分", "推荐一位用户注册成功，送" + recommendScore1 + "积分", IN, TransTypeEnum.RECOMMEND1, true);
                User user1 = this.userMapper.selectByPrimaryKey(userRecommendedCode1.getUserId());
                //二级推荐得积分
                UserRecommendedCode userRecommendedCode2 = this.userRecommendedCodeService.selectUserRecommendedCodeByKey(user1.getRecommendId());
                if (null != userRecommendedCode2) {
                    String recTransConfig2 = this.getTransConfig(TransTypeEnum.RECOMMEND2);
                    BigDecimal recommendScore2 = new BigDecimal(recTransConfig2);
                    transFlow(userRecommendedCode2.getUserId(), SubAccountTypeEnum.DEFAULT, recommendScore2, "推荐得积分", "下级用户推荐一位用户注册成功，送" + recommendScore2 + "积分", IN, TransTypeEnum.RECOMMEND2, true);
                }
            }
        }
    }

    public int[] getAttendanceScoreArr() throws CommonException {
        String transConfig = this.getTransConfig(TransTypeEnum.ATTENDANCE);
        String[] transConfigArr = transConfig.split(",");
        int[] attendanceScore = new int[transConfigArr.length];
        for (int i = 0; i < transConfigArr.length; i++) {
            attendanceScore[i] = Integer.parseInt(transConfigArr[i]);
        }
        return attendanceScore;
    }
    /***
     * 获取每日签到积分
     * @param user
     * @throws CommonException
     */
    @Transactional(rollbackFor=Exception.class)
    public int addCheckInScore(User user) throws CommonException {
        AttendanceCriteria criteria = new AttendanceCriteria();
        criteria.setOrderByClause("create_time desc");
        criteria.or().andUserIdEqualTo(user.getUserId());
        List<Attendance> attendanceList = attendanceMapper.selectByExample(criteria);
        String attendanceDate = DateUtil.dateFormat(new Date());
        BigDecimal attendanceScore = null;
        int attendanceDay = 1;
        //获得签到规则
        int[] attendanceScoreArr = this.getAttendanceScoreArr();
        //判断有没有签到记录
        if (CollectionUtils.isEmpty(attendanceList)) {
            //1.没有签到记录，新建一条签到记录
            Attendance attendance = new Attendance();
            attendance.setUserId(user.getUserId());
            attendance.setAttendanceDate(attendanceDate);
            int ret = this.attendanceMapper.insertSelective(attendance);
            if (ret == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "新增签到记录失败");
            }
            attendanceScore = new BigDecimal(attendanceScoreArr[0]);
        } else {
            //2.有签到记录
            //2.1 今天有无签到
            Attendance attendance = attendanceList.get(0);
            if (attendanceDate.equals(attendance.getAttendanceDate())) {
                throw new CommonException(ErrorCodeEnum.EXCEPTION, "请勿重复签到");
            }
            //2.2 判断昨天有没有签到
            String lastDate = DateUtil.dateFormat(DateUtil.getLastDay(new Date()));
            if (lastDate.equals(attendance.getAttendanceDate())) {
                //2.2.1 昨天已签到
                attendanceDay = attendance.getAttendanceDay();
                int ret = this.attendanceCustomMapper.updateAttendance(user.getUserId(),attendanceDate);
                if (ret == 0) {
                    throw new CommonException(ErrorCodeEnum.DBERROR, "更新签到记录失败");
                }
                if (attendanceDay < 7) {
                    attendanceScore = new BigDecimal(attendanceScoreArr[attendanceDay]);
                } else {
                    attendanceScore = new BigDecimal(attendanceScoreArr[6]);
                }

                attendanceDay++;

            } else {
                //2.2.2 昨天未签到
                attendance.setAttendanceDate(attendanceDate);
                attendance.setAttendanceDay(attendanceDay);
                int ret = this.attendanceCustomMapper.updateAttendanceReset(user.getUserId(),attendanceDate);
                if (ret == 0) {
                    throw new CommonException(ErrorCodeEnum.DBERROR, "更新签到记录失败");
                }
                attendanceScore = new BigDecimal(attendanceScoreArr[0]);
            }
        }
        String remark = "签到" + attendanceDay + "天，送" + attendanceScore + "积分";
        transFlow(user.getUserId(), SubAccountTypeEnum.DEFAULT, attendanceScore, "签到送积分", remark, IN, TransTypeEnum.ATTENDANCE, false);
        return attendanceDay;
    }

    @Transactional(rollbackFor=Exception.class)
    public void unfreezeRegister(User user) throws CommonException {
        //解冻注册积分
        int check = this.userAccessCustomMapper.countByLoginType(user.getUserId());
        //判断是否有解冻记录
        boolean status = this.checkUnfreeze(user.getUserId(), SubAccountTypeEnum.DEFAULT, TransTypeEnum.REGISTERED);
        if (check >= 7 && status) {
            this.unfreezeWallet(user.getUserId(), SubAccountTypeEnum.DEFAULT, "解冻注册积分", "注册积分冻结", TransTypeEnum.REGISTERED);
            UserRecommendedCode userRecommendedCode1 = this.userRecommendedCodeService.selectUserRecommendedCodeByKey(user.getRecommendId());
            this.unfreezeWallet(userRecommendedCode1.getUserId(), SubAccountTypeEnum.DEFAULT, "解冻一级推荐积分", "一级推荐积分解冻", TransTypeEnum.RECOMMEND1);
            User user1 = this.userMapper.selectByPrimaryKey(userRecommendedCode1.getUserId());
            //二级推荐得积分
            UserRecommendedCode userRecommendedCode2 = this.userRecommendedCodeService.selectUserRecommendedCodeByKey(user1.getRecommendId());
            if (null != userRecommendedCode2) {
                this.unfreezeWallet(userRecommendedCode2.getUserId(), SubAccountTypeEnum.DEFAULT, "解冻二级推荐积分", "二级推荐积分解冻", TransTypeEnum.RECOMMEND2);
            }
        }

    }

    /**
     * 判断是否有解冻交易记录
     * @param userId
     * @param subAccountTypeEnum
     * @param transTypeEnum
     * @return
     * @throws CommonException
     */
    public boolean checkUnfreeze(String userId,SubAccountTypeEnum subAccountTypeEnum,TransTypeEnum transTypeEnum) throws CommonException{
        //查询 有效 钱包账号
        WalletAccountCriteria cr = new WalletAccountCriteria();
        cr.or().andUserIdEqualTo(userId).andAccountStatusEqualTo(AccountStatusEnum.EFFECTIVE.toString());
        List<WalletAccount> accountList = this.walletAccountMapper.selectByExample(cr);
        if (CollectionUtils.isEmpty(accountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR,"钱包账号不存在,或已被锁定");
        }
        String accountId = accountList.get(0).getAccountId();
        // 根据subAccountTypeEnum和AccountId查询 子账号
        WalletSubAccountCriteria criteria = new WalletSubAccountCriteria();
        criteria.or().andAccountIdEqualTo(accountId).andSubAccountTypeEqualTo(subAccountTypeEnum.toString());
        List<WalletSubAccount> subAccountList = this.walletSubAccountMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(subAccountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "子账号不存在");
        }
        WalletSubAccount subAccount = subAccountList.get(0);
        //查询是否有类型对应冻结记录
        WalletTranslogCriteria criteria1 = new WalletTranslogCriteria();
        criteria1.or().andSubAccountIdEqualTo(subAccount.getSubAccountId()).andAccountIdEqualTo(accountId)
                .andTransTypeEqualTo(transTypeEnum.toString()).andIsBlockTransEqualTo((byte) 1).andBlockEndTimeIsNull();
        List<WalletTranslog> translogList = this.walletTranslogMapper.selectByExample(criteria1);
        return !CollectionUtils.isEmpty(translogList);
    }

    /**
     * 解冻操作
     * @param userId
     * @param subAccountTypeEnum
     * @param transTitle
     * @param remark
     * @param transTypeEnum 需要解冻的交易记录交易类型
     * @throws CommonException
     */
    @Transactional(rollbackFor=Exception.class)
    public void unfreezeWallet(String userId,SubAccountTypeEnum subAccountTypeEnum,String transTitle,String remark,TransTypeEnum transTypeEnum) throws CommonException {
        //查询 有效 钱包账号
        WalletAccountCriteria cr = new WalletAccountCriteria();
        cr.or().andUserIdEqualTo(userId).andAccountStatusEqualTo(AccountStatusEnum.EFFECTIVE.toString());
        List<WalletAccount> accountList = this.walletAccountMapper.selectByExample(cr);
        if (CollectionUtils.isEmpty(accountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR,"钱包账号不存在,或已被锁定");
        }
        String accountId = accountList.get(0).getAccountId();
        // 根据subAccountTypeEnum和AccountId查询 子账号
        WalletSubAccountCriteria criteria = new WalletSubAccountCriteria();
        criteria.or().andAccountIdEqualTo(accountId).andSubAccountTypeEqualTo(subAccountTypeEnum.toString());
        List<WalletSubAccount> subAccountList = this.walletSubAccountMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(subAccountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "子账号不存在");
        }
        WalletSubAccount subAccount = subAccountList.get(0);
        //查询是否有类型对应冻结记录
        WalletTranslogCriteria criteria1 = new WalletTranslogCriteria();
        criteria1.or().andSubAccountIdEqualTo(subAccount.getSubAccountId()).andAccountIdEqualTo(accountId)
                .andTransTypeEqualTo(transTypeEnum.toString()).andIsBlockTransEqualTo((byte) 1).andBlockEndTimeIsNull();
        List<WalletTranslog> translogList = this.walletTranslogMapper.selectByExample(criteria1);

        if (!CollectionUtils.isEmpty(translogList)) {
            //获取冻结交易记录
            WalletTranslog translog = translogList.get(0);
            //解冻子账号中的交易记录中的冻结金额
            this.customTransFlowMapper.updateUnfreezeWallet(translog.getTransAmount(), translog.getSubAccountId());

            translog.setUpdateTime(new Date());
            translog.setBlockEndTime(new Date());
            //更新记录为已解冻
            int ret1 = this.walletTranslogMapper.updateByPrimaryKey(translog);
            if (ret1 == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "更改解冻记录失败");
            }
            WalletTranslog walletTranslog1 = new WalletTranslog();
            String transId1 = IdUtil.getTransId();
            walletTranslog1.setTransId(transId1);
            walletTranslog1.setTransType(TransTypeEnum.UNFREEZE.toString());
            walletTranslog1.setAccountId(accountId);
            walletTranslog1.setSubAccountId(subAccount.getSubAccountId());
            walletTranslog1.setSubAccountType(subAccountTypeEnum.toString());
            walletTranslog1.setTransTitle(transTitle);
            walletTranslog1.setTransAmount(translog.getTransAmount());
            walletTranslog1.setTransDirector(TransDirectorEnum.IN.toString());
            walletTranslog1.setSourceBalance(subAccount.getTotalAivilable());
            walletTranslog1.setTageetBalance(subAccount.getTotalAivilable());
            walletTranslog1.setRemark(remark+translog.getTransAmount());
            int ret5 = this.walletTranslogMapper.insertSelective(walletTranslog1);
            if (ret5 == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "新增交易记录失败");
            }
        }else {
            throw new CommonException(ErrorCodeEnum.EXCEPTION, "没有需要解冻的交易记录");
        }
    }

    /**
     * 获取用户推荐的人数及推荐获得的积分
     * @param userId
     * @return
     * @throws CommonException
     */
    public HashMap<String, Object> getRecommendedInfo(String userId) throws CommonException {
        WalletAccountCriteria cr = new WalletAccountCriteria();
        cr.or().andUserIdEqualTo(userId).andAccountStatusEqualTo(AccountStatusEnum.EFFECTIVE.toString());
        List<WalletAccount> accountList = this.walletAccountMapper.selectByExample(cr);
        if (CollectionUtils.isEmpty(accountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "钱包账号不存在,或已被锁定");
        }
        HashMap<String, Object> recommendedMap = new HashMap<String, Object>();
        int number = 0;
        BigDecimal amount = new BigDecimal(0);
        String accountId = accountList.get(0).getAccountId();
        WalletTranslogCriteria criteria = new WalletTranslogCriteria();
        criteria.or().andAccountIdEqualTo(accountId).andTransTypeEqualTo(TransTypeEnum.RECOMMEND1.toString());
        List<WalletTranslog> translogList = this.walletTranslogMapper.selectByExample(criteria);
        if (!CollectionUtils.isEmpty(translogList)) {
            number += translogList.size();
            for (WalletTranslog translog : translogList) {
                amount = amount.add(translog.getTransAmount());
            }
        }
        WalletTranslogCriteria criteria1 = new WalletTranslogCriteria();
        criteria1.or().andAccountIdEqualTo(accountId).andTransTypeEqualTo(TransTypeEnum.RECOMMEND.toString());
        List<WalletTranslog> translogs = this.walletTranslogMapper.selectByExample(criteria1);
        if (!CollectionUtils.isEmpty(translogs)) {
            number += translogs.size();
            for (WalletTranslog translog : translogs) {
                amount = amount.add(translog.getTransAmount());
            }
        }
        BigDecimal otherAmount = new BigDecimal(0);
        WalletTranslogCriteria cr1 = new WalletTranslogCriteria();
        cr1.or().andAccountIdEqualTo(accountId).andTransTypeEqualTo(TransTypeEnum.RECOMMEND2.toString());
        List<WalletTranslog> translogList1 = this.walletTranslogMapper.selectByExample(cr1);
        if (!CollectionUtils.isEmpty(translogList1)) {
            for (WalletTranslog translog : translogList1) {
                otherAmount = otherAmount.add(translog.getTransAmount());
            }
        }
        recommendedMap.put("number", number);
        recommendedMap.put("amount", amount);
        recommendedMap.put("otherAmount", otherAmount);
        return recommendedMap;
    }


    /**
     * 分享快讯得积分
     * @param userId    用户ID
     * @param score     每次分享获得多少积分
     * @param num       每天最多分享多少次
     * @throws CommonException
     */
    public void shareNews(String userId, BigDecimal score, BigDecimal num) throws CommonException {
        if (score.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "交易积分为0或负数");
        }
        //查询 有效 钱包账号
        WalletAccountCriteria cr = new WalletAccountCriteria();
        cr.or().andUserIdEqualTo(userId).andAccountStatusEqualTo(AccountStatusEnum.EFFECTIVE.toString());
        List<WalletAccount> accountList = this.walletAccountMapper.selectByExample(cr);
        if (CollectionUtils.isEmpty(accountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "钱包账号不存在,或已被锁定");
        }
        String accountId = accountList.get(0).getAccountId();
        // 根据subAccountTypeEnum和AccountId查询 子账号
        WalletSubAccountCriteria criteria = new WalletSubAccountCriteria();
        criteria.or().andAccountIdEqualTo(accountId).andSubAccountTypeEqualTo(SubAccountTypeEnum.DEFAULT.toString());
        List<WalletSubAccount> subAccountList = this.walletSubAccountMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(subAccountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "子账号不存在");
        }
        WalletSubAccount subAccount = subAccountList.get(0);
        //查询当日是否已经分享5次，未分享5次在额加积分，分享5次了则不修改
        int i = customTransFlowMapper.updateFalseAddWalletWhereShareNum(score, subAccount.getSubAccountId(), TransTypeEnum.SHARE_NEWS.toString(),num);
        if (i > 0) {
            WalletTranslog walletTranslog1 = new WalletTranslog();
            String transId1 = IdUtil.getTransId();

            walletTranslog1.setTransId(transId1);
            walletTranslog1.setTransType(TransTypeEnum.SHARE_NEWS.toString());
            walletTranslog1.setAccountId(accountId);
            walletTranslog1.setSubAccountId(subAccount.getSubAccountId());
            walletTranslog1.setSubAccountType(SubAccountTypeEnum.DEFAULT.toString());
            walletTranslog1.setTransTitle("分享快讯");
            walletTranslog1.setTransAmount(score);
            walletTranslog1.setTransDirector(TransDirectorEnum.IN.toString());
            walletTranslog1.setSourceBalance(subAccount.getTotalAivilable());
            walletTranslog1.setTageetBalance(subAccount.getTotalAivilable().add(score));
            walletTranslog1.setRemark("分享快讯获得积分"+score.toString());
            int ret5 = this.walletTranslogMapper.insertSelective(walletTranslog1);
            if (ret5 == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "新增交易记录失败");
            }
            //新增或更新交易日结
            String dailyName = DateUtil.dateFormat(new Date());
            WalletDailySummaryCriteria criteria1 = new WalletDailySummaryCriteria();
            criteria1.or().andAccountIdEqualTo(accountId).andDailyNameEqualTo(dailyName);
            List<WalletDailySummary> dailySummaryList = this.walletDailySummaryMapper.selectByExample(criteria1);
            if (CollectionUtils.isEmpty(dailySummaryList)) {
                //list为空则新增交易日结
                //获取所有子账号计算总额
                WalletSubAccountCriteria criteria2 = new WalletSubAccountCriteria();
                criteria2.or().andAccountIdEqualTo(accountId);
                List<WalletSubAccount> subAccountAllList = this.walletSubAccountMapper.selectByExample(criteria);
                BigDecimal dailySourceBalance = new BigDecimal(0);
                for (WalletSubAccount subAccounts : subAccountAllList) {
                    dailySourceBalance = dailySourceBalance.add(subAccounts.getTotalAivilable());
                }
                WalletDailySummary walletDailySummary1 = new WalletDailySummary();
                String dailySummaryId = IdUtil.getDailySummaryId();
                walletDailySummary1.setDailySummaryId(dailySummaryId);
                walletDailySummary1.setAccountId(accountId);
                walletDailySummary1.setDailyName(dailyName);
                walletDailySummary1.setTransAmount(score);
                walletDailySummary1.setTransInAmount(score);
                walletDailySummary1.setSourceBalance(dailySourceBalance.subtract(score));
                walletDailySummary1.setTageetBalance(score);
                int ret6 = this.walletDailySummaryMapper.insertSelective(walletDailySummary1);
                if (ret6 == 0) {
                    throw new CommonException(ErrorCodeEnum.DBERROR, "新增日结记录失败");
                }
            } else {
                //list不为空则进行更新操作
                WalletDailySummary walletDailySummary1 = dailySummaryList.get(0);
                walletDailySummary1.setTransAmount(walletDailySummary1.getTransAmount().add(score));
                walletDailySummary1.setTransInAmount(walletDailySummary1.getTransInAmount().add(score));
                walletDailySummary1.setTageetBalance(score);
                walletDailySummary1.setUpdateTime(new Date());
                int ret6 = this.walletDailySummaryMapper.updateByPrimaryKeySelective(walletDailySummary1);
                if (ret6 == 0) {
                    throw new CommonException(ErrorCodeEnum.DBERROR, "更新日结记录失败");
                }
            }
        }else{
            throw new CommonException(ErrorCodeEnum.DBERROR, "已超每日最大收益或录入失败");
        }
    }

    public int selectTodayShareNewCount(String userId){
        int i = customTransFlowMapper.selectShareNewsCount(userId);
        return i;
    }

    /**
     * 积分兑换BCT
     * @param userId
     * @param TBNum
     * @throws CommonException
     */
    public void TBToBCT(String userId, BigDecimal TBNum) throws CommonException {
        String transTitle = "积分兑换BCT";
        String remark = "用" + TBNum + "积分兑换";
        this.transFlow(userId, SubAccountTypeEnum.DEFAULT, TBNum, transTitle, remark, TransDirectorEnum.INNER, TransTypeEnum.TB_TO_BCT, false);
    }


    /**
     * 积分交易流程
     * @param userId 用户id
     * @param subAccountTypeEnum 子账号类型
     * @param score 交易积分
     * @param transTitle 交易标题
     * @param remark 交易备注
     * @param transDirectorEnum 交易流向
     * @param transTypeEnum 交易类型
     * @param frozenStatus 冻结标记
     * @throws CommonException
     */
    @Transactional(rollbackFor=Exception.class)
    public void transFlow(String userId, SubAccountTypeEnum subAccountTypeEnum, BigDecimal score, String transTitle,
                              String remark, TransDirectorEnum transDirectorEnum,TransTypeEnum transTypeEnum, boolean frozenStatus) throws CommonException {
        if (score.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "交易积分为0或负数");
        }
        //查询 有效 钱包账号
        WalletAccountCriteria cr = new WalletAccountCriteria();
        cr.or().andUserIdEqualTo(userId).andAccountStatusEqualTo(AccountStatusEnum.EFFECTIVE.toString());
        List<WalletAccount> accountList = this.walletAccountMapper.selectByExample(cr);
        if (CollectionUtils.isEmpty(accountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "钱包账号不存在,或已被锁定");
        }
        String accountId = accountList.get(0).getAccountId();
        // 根据subAccountTypeEnum和AccountId查询 子账号
        WalletSubAccountCriteria criteria = new WalletSubAccountCriteria();
        criteria.or().andAccountIdEqualTo(accountId).andSubAccountTypeEqualTo(subAccountTypeEnum.toString());
        List<WalletSubAccount> subAccountList = this.walletSubAccountMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(subAccountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "子账号不存在");
        }
        WalletSubAccount walletSubAccount = subAccountList.get(0);
        String subAccountId = walletSubAccount.getSubAccountId();
        BigDecimal sourceBalance = walletSubAccount.getTotalAivilable();
        if (!transDirectorEnum.toString().equals("IN")) {
            if (score.compareTo(BigDecimal.ZERO) > 0) {
                if (walletSubAccount.getAvailableAivilable().compareTo(score) <0) {
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"您的积分不足");
                }
            }
        }
        //更新子账户
        int result = 0;
        if (!frozenStatus) {
            switch (transDirectorEnum){
                case IN:
                    result = this.customTransFlowMapper.updateFalseAddWallet(score, subAccountId);
                    break;
                case OUT:
                    result = this.customTransFlowMapper.updateFalseSubWallet(score, subAccountId);
                    break;
                case INNER:
                    result = this.customTransFlowMapper.updateFalseSubWallet(score, subAccountId);
                    break;
                default:
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
            }

        } else {
            switch (transDirectorEnum){
                case IN:
                    //增加总的，增加冻结的
                    result = this.customTransFlowMapper.updateTrueAddWallet(score, subAccountId);
                    break;
                case OUT:
                    //扣除可用的，增加冻结的
                    result = this.customTransFlowMapper.updateTrueSubWallet(score, subAccountId);
                    break;
                default:
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
            }


        }
        if (result == 0) {
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"交易失败");
        }
        //新增交易记录
        WalletTranslog walletTranslog1 = new WalletTranslog();
        String transId1 = IdUtil.getTransId();

        walletTranslog1.setTransId(transId1);
        walletTranslog1.setTransType(transTypeEnum.toString());
        walletTranslog1.setAccountId(accountId);
        walletTranslog1.setSubAccountId(subAccountId);
        walletTranslog1.setSubAccountType(subAccountTypeEnum.toString());
        walletTranslog1.setTransTitle(transTitle);
        walletTranslog1.setTransAmount(score);
        walletTranslog1.setTransDirector(transDirectorEnum.toString());
        walletTranslog1.setSourceBalance(sourceBalance);
        if (!frozenStatus) {
            switch (transDirectorEnum){
                case IN:
                    walletTranslog1.setTageetBalance(sourceBalance.add(score));
                    break;
                case OUT:
                    walletTranslog1.setTageetBalance(sourceBalance.subtract(score));
                    break;
                case INNER:
                    walletTranslog1.setTageetBalance(sourceBalance.subtract(score));
                    break;
                default:
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
            }

        }else {
            switch (transDirectorEnum){
                case IN:
                    walletTranslog1.setTageetBalance(sourceBalance.add(score));
                    walletTranslog1.setIsBlockTrans((byte) 1);
                    break;
                case OUT:
                    walletTranslog1.setTageetBalance(sourceBalance);
                    walletTranslog1.setIsBlockTrans((byte) 1);
                    break;
                default:
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
            }
        }
        walletTranslog1.setRemark(remark);
        int ret5 = this.walletTranslogMapper.insertSelective(walletTranslog1);
        if (ret5 == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "新增交易记录失败");
        }
        //新增或更新交易日结
        String dailyName = DateUtil.dateFormat(new Date());
        WalletDailySummaryCriteria criteria1 = new WalletDailySummaryCriteria();
        criteria1.or().andAccountIdEqualTo(accountId).andDailyNameEqualTo(dailyName);
        List<WalletDailySummary> dailySummaryList = this.walletDailySummaryMapper.selectByExample(criteria1);
        if (CollectionUtils.isEmpty(dailySummaryList)) {
            //list为空则新增交易日结
                //获取所有子账号计算总额
            WalletSubAccountCriteria criteria2 = new WalletSubAccountCriteria();
            criteria2.or().andAccountIdEqualTo(accountId);
            List<WalletSubAccount> subAccountAllList = this.walletSubAccountMapper.selectByExample(criteria);
            BigDecimal dailySourceBalance = new BigDecimal(0);
            for (WalletSubAccount subAccount : subAccountAllList) {
                dailySourceBalance = dailySourceBalance.add(subAccount.getTotalAivilable());
            }
            WalletDailySummary walletDailySummary1 = new WalletDailySummary();
            String dailySummaryId = IdUtil.getDailySummaryId();
            walletDailySummary1.setDailySummaryId(dailySummaryId);
            walletDailySummary1.setAccountId(accountId);
                walletDailySummary1.setDailyName(dailyName);
                walletDailySummary1.setTransAmount(score);
                switch (transDirectorEnum){
                    case IN:
                        walletDailySummary1.setTransInAmount(score);
                        walletDailySummary1.setSourceBalance(dailySourceBalance.subtract(score));
                        break;
                    case OUT:
                        walletDailySummary1.setTransOutAmount(score);
                        walletDailySummary1.setSourceBalance(dailySourceBalance.add(score));
                        break;
                    case INNER:
                        walletDailySummary1.setTransOutAmount(score);
                        walletDailySummary1.setSourceBalance(dailySourceBalance.add(score));
                        break;
                    default:
                        throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
                }
                walletDailySummary1.setTageetBalance(score);
                int ret6 = this.walletDailySummaryMapper.insertSelective(walletDailySummary1);
                if (ret6 == 0) {
                    throw new CommonException(ErrorCodeEnum.DBERROR, "新增日结记录失败");
                }
            } else {
                //list不为空则进行更新操作
                WalletDailySummary walletDailySummary1 = dailySummaryList.get(0);
                walletDailySummary1.setTransAmount(walletDailySummary1.getTransAmount().add(score));
                switch (transDirectorEnum){
                    case IN:
                        walletDailySummary1.setTransInAmount(walletDailySummary1.getTransInAmount().add(score));
                        break;
                    case OUT:
                        walletDailySummary1.setTransOutAmount(walletDailySummary1.getTransOutAmount().add(score));
                        break;
                    case INNER:
                        walletDailySummary1.setTransOutAmount(walletDailySummary1.getTransOutAmount().add(score));
                        break;
                    default:
                        throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
                }
                walletDailySummary1.setTageetBalance(score);
                walletDailySummary1.setUpdateTime(new Date());
                int ret6 = this.walletDailySummaryMapper.updateByPrimaryKeySelective(walletDailySummary1);
                if (ret6 == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "更新日结记录失败");
            }
        }
    }

}

