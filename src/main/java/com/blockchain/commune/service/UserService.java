package com.blockchain.commune.service;




import com.alibaba.fastjson.JSON;
import com.blockchain.commune.config.Constant;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.helper.ResponseHelper;
import com.blockchain.commune.mapper.UserAuthMapper;
import com.blockchain.commune.mapper.UserMapper;
import com.blockchain.commune.mapper.UserRecommendedCodeMapper;
import com.blockchain.commune.model.*;
import com.blockchain.commune.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRecommendedCodeMapper userRecommendedCodeMapper;

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    SmsBaoService smsBaoService;

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    public int insertUser(User user) {
        return this.userMapper.insertSelective(user);
    }

    public int updateUserByKey(User user) {
        return this.userMapper.updateByPrimaryKeySelective(user);
    }

    public int updateUserByPK(User user) {
        return this.userMapper.updateByPrimaryKey(user);
    }

    public int deleteUserByKey(String userId) {
        return this.userMapper.deleteByPrimaryKey(userId);
    }

    public User selectUserByKey(String userId) {
        return this.userMapper.selectByPrimaryKey(userId);
    }

    public List<User> selectUserByOpenId(String openId) {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or().andOpenIdEqualTo(openId);
        return this.userMapper.selectByExample(userCriteria);
    }

    public List<User> selectUserByLoginName(String loginName) {
        UserCriteria userCriteria = new UserCriteria();
        UserCriteria.Criteria criteria = userCriteria.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        userCriteria.or().andEmailEqualTo(loginName);
        return this.userMapper.selectByExample(userCriteria);
    }


    public long countUser() {
        UserCriteria userCriteria = new UserCriteria();
        return this.userMapper.countByExample(userCriteria);
    }

    public List<User> selectUserList() {
        UserCriteria userCriteria = new UserCriteria();
        return this.userMapper.selectByExample(userCriteria);
    }

    public List<User> selectUserListUserCode(String userCode) {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or().andUserCodeEqualTo(userCode);
        return this.userMapper.selectByExample(userCriteria);
    }


    public HashMap<String, Object> selectUser(String loginName, Integer page, Integer pageSize) {
        UserCriteria userCriteria = new UserCriteria();
        UserCriteria.Criteria criteria = userCriteria.createCriteria();

        if (!StringUtils.isEmpty(loginName)) {
            criteria.andLoginNameLike("%" + loginName + "%");
        }

        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        long count = userMapper.countByExample(userCriteria);

        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);

        userCriteria.setOrderByClause(orderString);

        List<User> userList = this.userMapper.selectByExample(userCriteria);

        if (CollectionUtils.isEmpty(userList)) {
            userList = new ArrayList<User>();
        }

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);

        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("userList", userList);
        newMap.put("page", pageObject);


        return newMap;
    }

    /**
     * 身份证实名认证二要素
     * @param userId
     * @param name  姓名
     * @param idCard 身份证号
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    public Map<String,Object> sendUserMessage(String userId,String name,String idCard)throws CommonException{
        //判断身份证是否已被认证 true-可认证 false--不可认证
        boolean check = checkUser(idCard);
        if (check){
            String host = "https://idcert.market.alicloudapi.com";
            String path = "/idcard";
            String method = "GET";
            String appcode = "318729678a2b4db0984c6bc6fbfca4d2";//api的appcode
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);

            Map<String, String> querys = new HashMap<String, String>();
            querys.put("idCard", idCard);
            querys.put("name", name);

            try {
                HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
                //System.out.println(response.toString());//如不输出json, 请打开这行代码，打印调试头部状态码。
                int code = response.getStatusLine().getStatusCode();
                if (200 != code) {
                    logger.error("实名认证错误，状态码：" + code);
                }
                //状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
                //获取response的body
                //System.out.println(EntityUtils.toString(response.getEntity()));
                String json = EntityUtils.toString(response.getEntity());
                Map<String,Object> map = JSON.parseObject(json);
                String status = map.get("status").toString();

                if (status.equals("01")) {
                    //新增实名认证信息
                    UserAuth userAuth = new UserAuth();
                    userAuth.setUserId(userId);
                    userAuth.setName(map.get("name").toString());
                    userAuth.setIdno(map.get("idCard").toString());
                    userAuth.setSex(map.get("sex").toString());
                    userAuth.setArea(map.get("area").toString());
                    userAuth.setProvince(map.get("province").toString());
                    userAuth.setCity(map.get("city").toString());
                    userAuth.setPrefecture(map.get("prefecture").toString());
                    userAuth.setBirthday(map.get("birthday").toString());
                    userAuth.setAddrcode(map.get("addrCode").toString());
                    userAuth.setLastcode(map.get("lastCode").toString());

                    int result = userAuthMapper.insertSelective(userAuth);
                    if (result > 0) {
                        logger.info("新增实名认证信息成功");
                        //修改用户表的验证状态
                        User user = userMapper.selectByPrimaryKey(userId);
                        user.setValidate(new Byte("1"));
                        int result2 = userMapper.updateByPrimaryKeySelective(user);
                        if (result2 > 0) {
                            logger.info("修改用户实名认证状态成功");
                        } else {
                            logger.error("修改用户实名认证状态失败");
                        }
                    } else {
                        logger.error("新增实名认证信息失败");
                    }
                    return map;
                } else {
                    throw new CommonException(ErrorCodeEnum.EXCEPTION, "实名认证失败，请检查您的姓名及身份证号码是否正确");
                }
            } catch (CommonException e) {
                logger.error("sendUserMessage" + e.getMessage());
                throw new CommonException(ErrorCodeEnum.EXCEPTION, "实名认证失败，请检查您的姓名及身份证号码是否正确");
            } catch (Exception e) {
                logger.error("sendUserMessage"+e.getMessage());
                throw new CommonException(ErrorCodeEnum.EXCEPTION, "认证失败，请联系管理员!!");
            }
        }
        throw new CommonException(ErrorCodeEnum.EXCEPTION,"该身份证已被认证，请重新输入");
    }

    /**
     * 查询用户是否已被认证
     * @param userId
     * @return
     */
    public int getUserValidate(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        Byte validate = user.getValidate();
        switch (validate) {
            case 1:
                return 1;//初级认证
            case 2:
                return 2;//高级认证待审核
            case 3:
                return 3;//高级认证成功
            case 4:
                return 4;//高级认证失败
            default:
                return 0;//未认证
        }
    }

    /**
     * 判断身份证是否已被认证
     * @param idNo
     * @return
     */
    public boolean checkUser(String idNo){
        UserAuthCriteria userAuthCriteria = new UserAuthCriteria();
        userAuthCriteria.createCriteria().andIdnoEqualTo(idNo);

        List<UserAuth> userAuths = userAuthMapper.selectByExample(userAuthCriteria);
        if (CollectionUtils.isEmpty(userAuths)){
            return true;//可进行实名认证
        }
        return false;//不可进行
    }

    /**
     * 高级认证更新身份证照
     * @param userId
     * @param front 身份证正面照url
     * @param back  身份证反面照url
     * @param userPic  半身照
     * @return
     */
    public void updateUserAuth(String userId,String front,String back,String userPic)throws CommonException{
        UserAuth userAuth = userAuthMapper.selectByPrimaryKey(userId);
        userAuth.setFront(front);
        userAuth.setBack(back);
        userAuth.setUserPic(userPic);
        userAuth.setUpdateTime(new Date());
        int result = userAuthMapper.updateByPrimaryKeySelective(userAuth);
        if (result > 0){
            //实名认证信息更新成功，更改用户实名认证状态
            User user = userMapper.selectByPrimaryKey(userId);
            user.setValidate(new Byte("2"));
            int result2 = userMapper.updateByPrimaryKeySelective(user);
            if (result2 == 0){
                throw new CommonException(ErrorCodeEnum.DBERROR,"用户高级认证状态更新失败");
            }
        }
    }

    public boolean getTime(String userId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lastCreateTime = "2018-10-11 20:00:00"; //未实名认证用户最后注册时间
        Date lastTime = sdf.parse(lastCreateTime);
        User user = userMapper.selectByPrimaryKey(userId);
        Date createTime = user.getCreateTime();
        if (createTime.compareTo(lastTime)> 0){
            return true;
        }
        return false;
    }

    public long getRegisterCount(Date date)
    {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or().andCreateTimeGreaterThanOrEqualTo(date);
        return this.userMapper.countByExample(userCriteria);
    }

    /**
     * 获得VIP等级
     * @param bigDecimal
     * @param from
     * @param to
     * @return
     */
    public  int getVipLevel(BigDecimal bigDecimal, int from, int to) {
        if (from + 1 == to) {
            return to;
        }
        if (bigDecimal.compareTo(new BigDecimal(Constant.vipLevel[0]))<0) {
            return 0;
        }
        if (bigDecimal.compareTo(new BigDecimal(Constant.vipLevel[9]))>=0) {
            return 10;
        }
        int middle = (from + to) / 2;
        if (bigDecimal.compareTo(new BigDecimal(Constant.vipLevel[middle])) < 0) {
            return getVipLevel(bigDecimal,from,middle);
        }
        if (bigDecimal.compareTo(new BigDecimal(Constant.vipLevel[middle])) >= 0) {
            return getVipLevel(bigDecimal,middle,to);
        }
        if (bigDecimal.compareTo(new BigDecimal(Constant.vipLevel[to])) == 0) {
            return middle+1;
        }
        return -1;
    }

    /**
     * 谷歌认证方法
     * @param googleAuthKey
     * @param checkCode
     * @return
     */
    public static boolean checkGoogleAuth(String googleAuthKey,Long checkCode){
        GoogleAuthenticatorUtil google = new GoogleAuthenticatorUtil();
        //谷歌校验验证码
        boolean b = google.check_code(googleAuthKey,checkCode,System.currentTimeMillis());
        return b;
    }

    /**
     * 根据 推荐码 或者 登录手机号码 查询列表
     * @param recommendedCode
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public HashMap<String, Object> selectRecommendedPeopleList(String recommendedCode,String userId,Integer page, Integer pageSize ) throws CommonException {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        UserCriteria criteria = new UserCriteria();
        UserCriteria.Criteria cr = criteria.or();
        if (!StringUtils.isEmpty(recommendedCode)) {
            cr.andRecommendIdEqualTo(recommendedCode);
        }
        if (!StringUtils.isEmpty(userId)) {
            User user = this.selectUserByKey(userId);
            if (user==null) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "登录名不正确");
            }
            UserRecommendedCodeCriteria codeCriteria = new UserRecommendedCodeCriteria();
            codeCriteria.or().andUserIdEqualTo(user.getUserId());
            List<UserRecommendedCode> codeList = this.userRecommendedCodeMapper.selectByExample(codeCriteria);
            if (CollectionUtils.isEmpty(codeList)) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "用户推荐码不存在");
            }
            cr.andRecommendIdEqualTo(codeList.get(0).getRecommendedCode());
        }
        long count = this.userMapper.countByExample(criteria);
        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);
        criteria.setOrderByClause(orderString);
        List<User> lst = this.userMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(lst)) {
            lst = new ArrayList<User>();
        } else {
            for (User user : lst) {
                user.setPassword("");
            }
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("userList", lst);
        newMap.put("page", pageObject);
        return newMap;
    }

    /**
     * 验证邮箱验证码是否正确
     * @param mailCode
     * @param token
     * @return
     */
    public boolean checkMailCode(String email,String mailCode, String token) {
        try {
            if (!JWTUtils.checkToken(token, email+mailCode)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("checkMailCode：", e);
            return false;
        }
    }


    /**
     * 根据验证方式进行验证
     * @return
     */
    public void checkAuthMethod(User user,String loginName,String smsCode,
                         String email,String mailCode,String emailToken,
                         String googleAuthCode,Integer xint,String BlockToken) throws CommonException {
        if(xint!=null&&BlockToken!=null) {
            if (!checkBlockToken(xint, BlockToken, true)) {
                throw new CommonException(ErrorCodeEnum.EXCEPTION, "滑块验证错误");
            }
        }
        String way = user.getAuthMethod();
        switch (way){
            case "P":
                if(StringUtils.isEmpty(loginName)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "手机号码为空");
                }
                if(StringUtils.isEmpty(smsCode)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "验证码为空");
                }
                boolean check = smsBaoService.checkSms(loginName, smsCode);
                if (!check) {
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "验证码错误");
                }
                break;
            case "M":
                if(StringUtils.isEmpty(email)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "邮箱账户为空");
                }
                if(StringUtils.isEmpty(mailCode)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "验证码为空");
                }
                if(StringUtils.isEmpty(emailToken)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "邮箱token值为空");
                }
                if(!this.checkMailCode(email,mailCode,emailToken)){
                    throw new CommonException(ErrorCodeEnum.EXCEPTION, "邮箱验证码错误");
                }
                break;
            case "PAG":
                if(StringUtils.isEmpty(loginName)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "手机号码为空");
                }
                if(StringUtils.isEmpty(googleAuthCode)){
                    throw new CommonException(ErrorCodeEnum.EXCEPTION, "谷歌验证码为空");
                }
                if(StringUtils.isEmpty(user.getGoogleAuthkey())){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "用户未进行谷歌认证");
                }
                if (!this.checkGoogleAuth(user.getGoogleAuthkey(),Long.parseLong(googleAuthCode))){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "谷歌验证码错误");
                }
                if(StringUtils.isEmpty(smsCode)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "验证码为空");
                }
                check = smsBaoService.checkSms(loginName, smsCode);
                if (!check) {
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "验证码错误");
                }
                break;
            case "MAG":
                if(StringUtils.isEmpty(googleAuthCode)){
                    throw new CommonException(ErrorCodeEnum.EXCEPTION, "谷歌验证码为空");
                }
                if(StringUtils.isEmpty(email)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "邮箱账户为空");
                }
                if(StringUtils.isEmpty(mailCode)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "验证码为空");
                }
                if(StringUtils.isEmpty(user.getGoogleAuthkey())){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "用户未进行谷歌认证");
                }
                if (!this.checkGoogleAuth(user.getGoogleAuthkey(),Long.parseLong(googleAuthCode))){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "谷歌验证码错误");
                }
                if(StringUtils.isEmpty(emailToken)){
                    throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "邮箱token值为空");
                }
                if(!this.checkMailCode(email,mailCode,emailToken)){
                    throw new CommonException(ErrorCodeEnum.EXCEPTION, "邮箱验证码错误");
                }
                break;
            default:
                throw new CommonException(ErrorCodeEnum.SMSCODEERROR, "未知验证方式");
        }
    }


    /**
     * 验证滑块验证码
     * @param xint          x坐标值
     * @param BlockToken    token值
     * @param isDel         是否删除redis中的token
     * @return
     */
    public boolean checkBlockToken(Integer xint,String BlockToken, boolean isDel) throws CommonException {
            if(xint==null&&xint==0){
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"坐标值不能为空");
            }
            if (RedisUtil.exists(BlockToken)) {
                String StringX = RedisUtil.get(BlockToken);
                if(isDel){
                    RedisUtil.remove(BlockToken);
                }
                Integer x = Integer.parseInt(StringX);
                Integer mm = 10;//上下浮动的像素值
                if (!JWTUtils.checkToken(BlockToken, StringX)) {
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"token验证失败");
                }
                if(xint>=(x-mm)&&xint<=(x+mm)){
                    return true;
                }else{
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"验证错误");
                }
            }else{
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"token不存在或已过期");
            }
    }


}
