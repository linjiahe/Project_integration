package com.blockchain.commune.service.wallet;


import com.alibaba.fastjson.JSONObject;
import com.blockchain.commune.config.RabbitConfig;
import com.blockchain.commune.customemapper.wallet.CustomPurseMapper;
import com.blockchain.commune.customemapper.wallet.CustomTransFlowMapper;
import com.blockchain.commune.customemapper.wallet.TranslogMapper;
import com.blockchain.commune.custommodel.CheckedOrder;
import com.blockchain.commune.custommodel.PurseTranslogAdmin;
import com.blockchain.commune.custommodel.UserCoinDetail;
import com.blockchain.commune.custommodel.WithdrawOrder;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.enums.Purse.PAccountStatusEnum;
import com.blockchain.commune.enums.Purse.*;
import com.blockchain.commune.enums.Purse.PSubAccountTypeEnum;
import com.blockchain.commune.enums.Purse.PurseApiMethodEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.http.HttpUtils;
import com.blockchain.commune.mapper.*;
import com.blockchain.commune.model.*;
import com.blockchain.commune.service.UserService;
import com.blockchain.commune.utils.ConvertUtil;
import com.blockchain.commune.utils.IdUtil;
import com.rabbitmq.client.Channel;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;


@Component
public class PurseService implements RabbitTemplate.ReturnCallback{

    @Autowired
    PurseAccountMapper purseAccountMapper;

    @Autowired
    PurseSubAccountMapper purseSubAccountMapper;

    @Autowired
    PurseTranslogMapper purseTranslogMapper;

    @Autowired
    PurseDailySummaryMapper purseDailySummaryMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CustomPurseMapper customPurseMapper;

    @Autowired
    CustomTransFlowMapper customTransFlowMapper;

    @Autowired
    UserMapper userMapper;

    //以太坊API密钥
    private final static String apikey = "ZHE5MTP8N3SX4JHNWEFCT6T65N1G1T647H";

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(PurseService.class);

    /**
     * 向队列写入信息
     * @param queues    队列名
     * @param json      消息
     * @throws Exception
     */
    public void sendWalletQueues(String queues,String json) throws Exception{
        logger.info("sendWalletQueues：准备发送请求");
        logger.info("sendWalletQueues："+json);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            logger.info("ack:"+ack);
            if (!ack) {
                System.out.println("sendWalletQueues消息发送失败" + cause + correlationData.toString());
            } else {
                System.out.println("sendWalletQueues 消息发送成功 ");
            }
        });
        //this.rabbitTemplate.convertAndSend("hello", json);
        this.rabbitTemplate.convertAndSend(queues,json);
        logger.info("sendWalletQueues：发送完成");
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.listenQueues)
    public void WalletQueuesNotify(byte[] data, Channel channel, Message message) throws Exception {
        try {
            JSONObject json = (JSONObject) JSONObject.parse(data);
            logger.info("WalletQueuesNotify:" + json);
            switch (PurseApiMethodEnum.valueOf(json.getString("method"))) {
                    //新增钱包账户
                case newAccount:
                    newAccount(json);
                    break;
                    //查询余额
                case getBalance:
                    getBalance(json);
                    break;
                    //交易
//                case translate:
//                    translate(json);
//                    break;
                    //未知
                default:
                    logger.error("WalletQueuesNotify:未知方法");
                    break;
            }
            //抛弃此数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
           //异常存入队列保存数据
            sendWalletQueues(RabbitConfig.queues_dead,JSONObject.parse(data).toString());
            //抛弃此数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 币种钱包交易处理方法
     */
    public void translate(JSONObject json) throws Exception {
        //获取该订单
        PurseTranslog purseTranslog = this.purseTranslogMapper.selectByPrimaryKey(json.getString("msgid"));
        if(purseTranslog==null){
            logger.error("translate:失败，无此订单");
            logger.error("translate:错误数据为："+json);
            throw new Exception();
        }
        //从订单中获取子账户ID
        String subaccountId = purseTranslog.getSubAccountId();
        //子账户信息
        PurseSubAccount purseSubAccount = this.purseSubAccountMapper.selectByPrimaryKey(subaccountId);
        //从订单中获取交易金额
        BigDecimal score = purseTranslog.getTransAmount();
        if(json.get("code").equals(200)){
            //消除交易日志发起状态为等待状态
            purseTranslog.setOrderStatus(PurseStatusEnum.pending.toString());
            purseTranslog.setTransDetailId(json.getString("data"));
            int updatePT = this.purseTranslogMapper.updateByPrimaryKey(purseTranslog);
            if(updatePT>0){
                //修改交易后金额变化
                purseSubAccount.setTotalAivilable(purseSubAccount.getTotalAivilable().subtract(score));
                purseSubAccount.setBlockAivilable(purseSubAccount.getBlockAivilable().subtract(score));
                int i = purseSubAccountMapper.updateByPrimaryKey(purseSubAccount);
                if(i>0){
                    logger.info("translate:交易成功");
                }else{
                    logger.error("translate:修改子账户余额失败");
                    logger.error("translate修改子账户余额失败数据："+json);
                }
            }else{
                logger.error("translate:修改交易日志表失败");
                logger.error("translate交易日志表失败数据："+json);
            }

        }else{
            purseTranslog.setOrderStatus(PurseStatusEnum.fail.toString());
            int updatePT = this.purseTranslogMapper.updateByPrimaryKey(purseTranslog);
            if(updatePT>0){
                logger.info("translate:交易返回失败，将交易状态设置为失败");
            }
            logger.error("translate:转账接口返回调用失败");
            logger.error("translate转账调用失败数据："+json);
            purseSubAccount.setTotalAivilable(purseSubAccount.getTotalAivilable().add(score));
            purseSubAccount.setAvailableAivilable(purseSubAccount.getAvailableAivilable().add(score));
            int i = purseSubAccountMapper.updateByPrimaryKey(purseSubAccount);
            if(i>0){
                logger.info("translate:接口返回调用失败,撤销冻结金额成功");
            }else{
                logger.error("translate:接口返回调用失败,修改撤销冻结金额失败");
                logger.error("translate修改撤销冻结金额数据："+json);
            }
        }
    }

    /**
     * 币种钱包余额查询处理方法
     */
    private void getBalance(JSONObject json)throws CommonException{
        //获得返回余额
        String data = json.getString("data");
        System.out.println("返回的钱包余额 :" + data);

        String params = json.getString("params");
        JSONObject jsonObject = (JSONObject)JSONObject.parse(params);
        String subAccountId = jsonObject.getString("account");

        PurseSubAccount purseSubAccount = purseSubAccountMapper.selectByPrimaryKey(subAccountId);
        purseSubAccount.setTotalAivilable(new BigDecimal(data));

        int result = purseSubAccountMapper.updateByPrimaryKeySelective(purseSubAccount);
        if (result == 0){
            throw new CommonException(ErrorCodeEnum.DBERROR,"操作失败！");
        }
    }

    /**
     * 查询钱包余额
     * @param userId
     * @throws Exception
     */
    public void sendGetBalanceQuene(String userId) throws Exception {

        PurseAccount purse = queryPurseAccount(userId);

        PurseSubAccountCriteria psac = new PurseSubAccountCriteria();
        PurseSubAccountCriteria.Criteria criteria = psac.createCriteria().andAccountIdEqualTo(purse.getAccountId());

        List<PurseSubAccount> purseSubAccounts = purseSubAccountMapper.selectByExample(psac);
        String subAccountId = purseSubAccounts.get(0).getSubAccountId();//获得子账户id

        PurseSubAccountCriteria psac2 = new PurseSubAccountCriteria();
        PurseSubAccountCriteria.Criteria criteria1 = psac2.createCriteria().andAccountIdEqualTo(purse.getAccountId()).andSubAccountIdEqualTo(subAccountId);

        List<PurseSubAccount> purseSubAccounts2 = purseSubAccountMapper.selectByExample(psac2);
        String subAccountType = purseSubAccounts2.get(0).getSubAccountType();//获得币种

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("account",subAccountId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("method",PurseApiMethodEnum.getBalance.toString());
        jsonObject.put("coin",subAccountType);
        jsonObject.put("msgid","a");
        jsonObject.put("params",hashMap);
        this.sendWalletQueues(RabbitConfig.queues,jsonObject.toJSONString());
    }

    /**
     * 新增币种钱包处理方法
     */
    private void newAccount(JSONObject json) throws CommonException {
        if (!"200".equals(json.getString("code"))) {
            throw new CommonException(ErrorCodeEnum.PARAMETERROR, "创建钱包地址失败，返回错误信息为：" + json.getString("msg"));
        }
        String purseAddress = json.getString("data");
        JSONObject accountId = (JSONObject)JSONObject.parse(json.getString("params"));
        String subAccountId = accountId.getString("account_id");
        PurseSubAccount subAccount = this.purseSubAccountMapper.selectByPrimaryKey(subAccountId);
        if (null == subAccount) {
            logger.error("队列中获得的子账号id：", subAccountId);
            throw new CommonException(ErrorCodeEnum.DBERROR, "用户币种钱包不存在");
        }
        if (purseAddress.equals(subAccount.getPurseAddress())) {
            return;
        }
        if (null != subAccount.getPurseAddress()) {
            logger.info("子账号ID为：" + subAccountId + "的旧钱包地址为：" + subAccount.getPurseAddress() + "，新钱包地址为：" + purseAddress);
        }
        PurseSubAccount purseSubAccount = new PurseSubAccount();
        purseSubAccount.setSubAccountId(subAccountId);
        purseSubAccount.setPurseAddress(purseAddress);
        logger.info("新增钱包地址：" + purseAddress + "子账号id为：" + subAccountId);
        int ret = this.purseSubAccountMapper.updateByPrimaryKeySelective(purseSubAccount);
        if (ret == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "数据库异常，钱包地址插入失败");
        }
    }

    public void sendAllCoinAccountQuene(String userId, String userName) throws Exception {
        sendNewAccountQuene(userId,userName,PSubAccountTypeEnum.BCT.toString());
        sendNewAccountQuene(userId,userName,PSubAccountTypeEnum.SC.toString());
//        sendNewAccountQuene(userId,userName,PSubAccountTypeEnum.BTM.toString());
    }

    public void sendNewAccountQuene(String userId, String userName,String coin) throws Exception {
        //新增钱包账号
        PurseAccount purseAccount = new PurseAccount();
        String accountId = IdUtil.getAccountId();

        purseAccount.setAccountId(accountId);
        purseAccount.setUserId(userId);
        purseAccount.setUserName(userName);
        //新增币种钱包注册，状态默认为有效
        purseAccount.setAccountStatus(PAccountStatusEnum.EFFECTIVE.toString());
        int ret = this.purseAccountMapper.insertSelective(purseAccount);
        if (ret == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "新增钱包账号失败");
        }

        //新增BCT子账号
        PurseSubAccount purseSubAccount = new PurseSubAccount();
        String subAccountId = IdUtil.getSubAccountId();
        purseSubAccount.setSubAccountId(subAccountId);
        purseSubAccount.setAccountId(accountId);
        purseSubAccount.setSubAccountType(coin);
        int ret1 = this.purseSubAccountMapper.insertSelective(purseSubAccount);
        if (ret1 == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "新增"+coin+"钱包子账号失败");
        }

        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("account_id", subAccountId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("method", PurseApiMethodEnum.newAccount.toString());
        jsonObject.put("coin",purseSubAccount.getSubAccountType());
        jsonObject.put("msgid", purseSubAccount.getSubAccountType()+ subAccountId);
        jsonObject.put("params", hashMap);
        this.sendWalletQueues(RabbitConfig.queues, jsonObject.toJSONString());

    }

    /**
     * 用户登录即调用，发送获取钱包地址消息
     * @param userId
     * @throws Exception
     */
    public void updatePurseAddress(String userId) throws Exception{
        List<PurseSubAccount> subAccountList = this.queryBalance(userId, PSubAccountTypeEnum.BCT.toString());
        if (CollectionUtils.isEmpty(subAccountList)) {
            logger.error("UserId:", userId);
            throw new CommonException(ErrorCodeEnum.DBERROR, "用户没有子账号");
        }
        PurseSubAccount subAccount = subAccountList.get(0);
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("account_id", subAccount.getSubAccountId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("method", PurseApiMethodEnum.newAccount.toString());
        jsonObject.put("coin",subAccount.getSubAccountType());
        jsonObject.put("msgid", subAccount.getSubAccountType()+subAccount.getSubAccountId());
        jsonObject.put("params", hashMap);
        this.sendWalletQueues(RabbitConfig.queues, jsonObject.toJSONString());
    }


    /**
     * 币种交易流程
     * @param userId 用户id
     * @param purseAddress  转入钱包地址
     * @param pSubAccountTypeEnum 子账号类型
     * @param score 交易金额
     * @param transTitle 交易标题
     * @param fee   矿工费
     * @param upAndDown 是否上链交易
     * @param remark 交易备注
     * @param pTransDirectorEnum 交易流向
     * @param pTransTypeEnum 交易类型
     * @throws CommonException  自定义抛出异常
     */
    public void transCoin(String userId,String purseAddress, PSubAccountTypeEnum pSubAccountTypeEnum, BigDecimal score, String transTitle,
                          BigDecimal fee,String upAndDown,String remark, PTransDirectorEnum pTransDirectorEnum, PTransTypeEnum pTransTypeEnum) throws CommonException {
        if (StringUtils.isEmpty(purseAddress)){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"转入钱包地址不能为空");
        }
        if(score==null){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"转入金额不能为空");
        }
        if(score.compareTo(BigDecimal.ZERO)<=0){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"转入金额不是有效数字");
        }
        if(StringUtils.isEmpty(upAndDown)){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"请选择是否上链交易");
        }
        byte upAndDownByte;
        if(upAndDown.equals("0")){
            upAndDownByte = new Byte("0");
        }else if(upAndDown.equals("1")){
            upAndDownByte = new Byte("1");
        }else{
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"参数非法");
        }
        if(StringUtils.isEmpty(remark)&&upAndDownByte==0){
            remark = "内部转账，金额"+score;
        }else if(StringUtils.isEmpty(remark)&&upAndDownByte==1){
            remark = "外部转账，金额"+score;
        }
        //判断是不是内部转账
        User user = queryUserInPlatform(purseAddress);
        if(user==null){
            if(upAndDownByte==0){
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"非平台钱包地址不允许内部转账");
            }
        }
        if(fee==null){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"矿工费不能为空");
        }
        if(upAndDownByte==1){
            if(fee.compareTo(BigDecimal.ZERO)<=0){
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"矿工费不是有效数字");
            }
        }


        //查询 有效 钱包账号
        PurseAccount Purse = null;
        try {
            Purse = queryPurseAccount(userId);
        }catch (Exception e){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"获取钱包信息失败");
        }

        if (Purse==null){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"钱包账户不存在");
        }
        if (!Purse.getAccountStatus().equals(PAccountStatusEnum.EFFECTIVE.toString())){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"钱包账户状态不正常");
        }
        String accountId = Purse.getAccountId();
        // 根据subAccountTypeEnum和AccountId查询 子账号
        PurseSubAccountCriteria criteria = new PurseSubAccountCriteria();
        criteria.or().andAccountIdEqualTo(accountId).andSubAccountTypeEqualTo(pSubAccountTypeEnum.toString());
        List<PurseSubAccount> psubAccountList = this.purseSubAccountMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(psubAccountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "该币种子账号不存在");
        }

        //发起子账户
        PurseSubAccount purseSubAccount = psubAccountList.get(0);
        //查询该账户是否存在该币种钱包地址
        if(purseSubAccount.getPurseAddress()==null||purseSubAccount.getPurseAddress()==""){
            //新增钱包地址
            try {
                updatePurseAddress(userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new CommonException(ErrorCodeEnum.DBERROR, "子账号不存在该币种钱包地址，请重试");
        }
        if(score.compareTo(purseSubAccount.getAvailableAivilable())>0){
            throw new CommonException(ErrorCodeEnum.DBERROR, "子账号可用余额不足");
        }
        String subAccountId = purseSubAccount.getSubAccountId();
        BigDecimal sourceBalance = purseSubAccount.getTotalAivilable();


        //先更新子账号,后添加交易记录
        int i = 0;
        if(PTransDirectorEnum.IN.equals(pTransDirectorEnum)){
            i = customTransFlowMapper.updateFalseAddPurse(score,subAccountId);
        }
        if(PTransDirectorEnum.OUT.equals(pTransDirectorEnum)){
            i = customTransFlowMapper.updateFalseSubPurse(score,subAccountId);
        }

        //新增交易记录
        PurseTranslog purseTranslog = new PurseTranslog();
        String transId1 = IdUtil.getTransId();

        purseTranslog.setTransId(transId1);
        purseTranslog.setTransType(pTransTypeEnum.toString());
        purseTranslog.setAccountId(accountId);
        purseTranslog.setSubAccountId(subAccountId);
        purseTranslog.setSubAccountType(pSubAccountTypeEnum.toString());
        purseTranslog.setTransTitle(transTitle);
        purseTranslog.setTransAmount(score);
        purseTranslog.setTransDirector(pTransDirectorEnum.toString());
        purseTranslog.setSourceBalance(sourceBalance);
        purseTranslog.setOrderStatus(PurseStatusEnum.launch.toString());
        purseTranslog.setTransParty(purseAddress);
        purseTranslog.setUpanddown(upAndDownByte);
        if(user!=null){
            purseTranslog.setPartyUserid(user.getUserId());
        }
        //判断是流入还是流出
        if(PTransDirectorEnum.IN.equals(pTransDirectorEnum)){
            purseTranslog.setTageetBalance(sourceBalance.add(score));
        }
        if(PTransDirectorEnum.OUT.equals(pTransDirectorEnum)){
            purseTranslog.setTageetBalance(sourceBalance.subtract(score));
        }
        purseTranslog.setRemark(remark);

        if(i>0){
            int ret5 = this.purseTranslogMapper.insertSelective(purseTranslog);
            if (ret5 == 0) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "新增交易记录失败");
            }
        }else{
            throw new CommonException(ErrorCodeEnum.DBERROR, "提币失败");
        }


        //请求远程rabbitMQ交易接口
//        JSONObject json = new JSONObject();
//        json.put("method",PurseApiMethodEnum.translate);
//        json.put("coin",pSubAccountTypeEnum.toString());
//        json.put("msgid",transId1);
//        JSONObject params = new JSONObject();
//        params.put("from",subAccountId);
//        params.put("to",purseAddress);
//        params.put("amount",score);
//        params.put("fee",fee);
//        json.put("params",params);
//
//        try {
//            sendWalletQueues(RabbitConfig.queues,json.toJSONString());
//        }catch (Exception e){
//            throw new CommonException(ErrorCodeEnum.NETWORKERROR, "请求交易接口出现异常");
//        }


    }

    public PurseSubAccount selectPurseSubAccountBySubAccountId(String subAccountId) {
        PurseSubAccountCriteria criteria = new PurseSubAccountCriteria();
        criteria.or().andSubAccountIdEqualTo(subAccountId);
        List<PurseSubAccount> subAccountList = this.purseSubAccountMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(subAccountList)) {
            return null;
        }
        return subAccountList.get(0);
    }

    /**
     * 外部入账通知
     * @param data
     * @param channel
     * @param message
     * @throws Exception
     */
    @Transactional(rollbackFor=Exception.class)
    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.rechargeQueues)
    public void recharge(byte[] data, Channel channel, Message message) throws Exception {
        try {
            JSONObject json = (JSONObject) JSONObject.parse(data);
            logger.info("RechargeQueues:" + json);
            //1.获取有用的数据
            String subAccountId = json.getString("account_id");
            String purseAddress = json.getString("payee");
            String transParty = json.getString("payer");
            String transDetailId = json.getString("tx_id");
            BigDecimal transAmount = json.getBigDecimal("amount");
            boolean isCompeted = json.getBoolean("is_completed");
            PurseTranslog purseTranslog = this.selectPurseTranslogByTransDetailId(subAccountId, transParty, transDetailId);
            PurseSubAccount purseSubAccount = this.selectPurseSubAccountBySubAccountId(subAccountId);
            if (null == purseSubAccount) {
                throw new CommonException(ErrorCodeEnum.DBERROR, "转入记录中的子账号id不存在");
            }
            String accountId = purseSubAccount.getAccountId();
            BigDecimal sourceBalance = purseSubAccount.getTotalAivilable();
            //2.判断交易记录是否存在
            if (null == purseTranslog) {
                PurseTranslog purseTranslog1 = new PurseTranslog();
                purseTranslog1.setOrderStatus(PurseStatusEnum.pending.toString());
                if (isCompeted) {
                    purseTranslog1.setOrderStatus(PurseStatusEnum.success.toString());
                    purseTranslog1.setSourceBalance(sourceBalance);
                    purseTranslog1.setTageetBalance(sourceBalance.add(transAmount));
                    int ret3 = this.customTransFlowMapper.updateFalseAddPurse(transAmount, purseSubAccount.getSubAccountId());
                    if (ret3 == 0) {
                        throw new CommonException(ErrorCodeEnum.DBERROR, "转入记录子账号完成更新失败");
                    }
                }
                //2.1交易记录不存在，新增交易记录
                String transId = IdUtil.getTransId();
                purseTranslog1.setTransId(transId);
                purseTranslog1.setSubAccountId(subAccountId);
                purseTranslog1.setAccountId(accountId);
                purseTranslog1.setTransType(PTransTypeEnum.RECHARGE.toString());
                purseTranslog1.setSubAccountType(PSubAccountTypeEnum.BCT.toString());
                purseTranslog1.setTransTitle("外部转入");
                purseTranslog1.setTransAmount(transAmount);
                purseTranslog1.setTransDirector(PTransDirectorEnum.IN.toString());
                purseTranslog1.setTransParty(transParty);
                purseTranslog1.setTransDetailId(transDetailId);
                purseTranslog1.setRemark("外部转入");
                int ret1 = this.purseTranslogMapper.insertSelective(purseTranslog1);
                if (ret1 == 0) {
                    throw new CommonException(ErrorCodeEnum.DBERROR, "转入记录插入失败");
                }
            } else {
                //2.2如果交易记录为正在交易中，且返回isCompeted为true则更新交易记录为完成
                if (!PurseStatusEnum.success.toString().equals(purseTranslog.getOrderStatus()) && isCompeted) {
                    int ret3 = this.customTransFlowMapper.updateFalseAddPurse(transAmount, purseSubAccount.getSubAccountId());
                    if (ret3 == 0) {
                        throw new CommonException(ErrorCodeEnum.DBERROR, "转入记录子账号完成更新失败");
                    }
                    purseTranslog.setOrderStatus(PurseStatusEnum.success.toString());
                    purseTranslog.setSourceBalance(sourceBalance);
                    purseTranslog.setTageetBalance(sourceBalance.add(transAmount));
                    purseTranslog.setUpdateTime(new Date());
                    int ret2 = this.purseTranslogMapper.updateByPrimaryKeySelective(purseTranslog);
                    if (ret2 == 0) {
                        throw new CommonException(ErrorCodeEnum.DBERROR, "转入记录完成更新失败");
                    }

                }
            }
            //交易记录存在，且isCompeted为false，抛弃此数据

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            //存入新队列保存数据
            sendWalletQueues(RabbitConfig.rechargeQueues_dead, JSONObject.parse(data).toString());
            //抛弃此数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }


    public PurseTranslog selectPurseTranslogByTransDetailId(String subAccountId,String transParty,String transDetailId) {
        PurseTranslogCriteria criteria = new PurseTranslogCriteria();
        criteria.or().andSubAccountIdEqualTo(subAccountId).andTransPartyEqualTo(transParty).andTransDetailIdEqualTo(transDetailId);
        List<PurseTranslog> translogList = this.purseTranslogMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(translogList)) {
            return null;
        }
        return translogList.get(0);
    }

    /**
     * 查询币种钱包余额  |  子账户查询方法
     * @param userId    用户ID
     * @return  钱包币种总数实体类
     */
    public List<PurseSubAccount> queryBalance(String userId, String type) throws Exception {
        //调用查询钱包币种方法获取该用户钱包币种表主键
        PurseAccount Purse = queryPurseAccount(userId);
        //判断该用户钱包币种钱包是否存在
        if (Purse == null) {
            return null;
        }
        PurseSubAccountCriteria wsc = new PurseSubAccountCriteria();
        PurseSubAccountCriteria.Criteria criteria = wsc.createCriteria();
        //根据钱包主键查询
        criteria.andAccountIdEqualTo(Purse.getAccountId()).andSubAccountTypeEqualTo(type);

        //根据币种表主键查询币种余额
        List<PurseSubAccount> list = purseSubAccountMapper.selectByExample(wsc);
        return list;
    }

    /**
     * 钱包首页查询所有币种钱包余额
     * @param userId    用户ID
     * @return  钱包币种总数实体类
     */
    public List<PurseSubAccount> queryBalance(String userId, String search ,boolean display) throws Exception {
        //调用查询钱包币种方法获取该用户钱包币种表主键
        PurseAccount Purse = queryPurseAccount(userId);
        //判断该用户钱包币种钱包是否存在
        if (Purse == null) {
            return null;
        }
        PurseSubAccountCriteria wsc = new PurseSubAccountCriteria();
        PurseSubAccountCriteria.Criteria criteria = wsc.createCriteria();
        //根据钱包主键查询
        criteria.andAccountIdEqualTo(Purse.getAccountId());
        if(!StringUtils.isEmpty(search)){
            criteria.andSubAccountTypeLike(search);
        }
        if(!display){
            criteria.andTotalAivilableNotEqualTo(BigDecimal.ZERO);
        }

        //根据币种表主键查询币种余额
        List<PurseSubAccount> list = purseSubAccountMapper.selectByExample(wsc);
        return list;
    }

    /**
     * 根据用户ID查询币种钱包表
     * @param userId    用户ID
     * @return  用户币种钱包实体类
     */
    public PurseAccount queryPurseAccount(String userId) throws Exception {
        PurseAccountCriteria wa = new PurseAccountCriteria();
        PurseAccountCriteria.Criteria wacCriteria = wa.createCriteria();
        //根据用户ID查询
        wacCriteria.andUserIdEqualTo(userId);

        //根据用户ID获取钱包币种主表的主键
        List<PurseAccount> listPurseAccount = this.purseAccountMapper.selectByExample(wa);
        if (CollectionUtils.isEmpty(listPurseAccount)) {
            return null;
        }
        PurseAccount Purse = listPurseAccount.get(0);
        return Purse;
    }

    /**
     * 根据用户ID查询当天交易日结表
     * @param userId    用户ID
     * @return  用户币种交易日结表实体类
     */
    public PurseDailySummary queryPurseDailySummary(String userId) throws Exception {
        //调用查询钱包币种方法获取该用户钱包币种表主键
        PurseAccount Purse = queryPurseAccount(userId);
        //判断该用户钱包币种钱包是否存在
        if (Purse == null) {
            return null;
        }
        PurseDailySummaryCriteria wdsc = new PurseDailySummaryCriteria();
        PurseDailySummaryCriteria.Criteria criteria = wdsc.createCriteria();
        //根据钱包主键查询
        criteria.andAccountIdEqualTo(Purse.getAccountId());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //获取当天凌晨时间
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date dateZero = calendar.getTime();
        //获取当天23:59:59时间
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date dateEnd = calendar.getTime();
        //查询00:00:00--23:59:59时间段内日总结数据
        criteria.andCreateTimeBetween(dateZero, dateEnd);
        //查询用户日结表
        List<PurseDailySummary> list = this.purseDailySummaryMapper.selectByExample(wdsc);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        PurseDailySummary PurseDailySummary = list.get(0);
        return PurseDailySummary;
    }

    /**
     * 查询交易明细
     *
     * @param userId    用户ID
     * @param transType 查询交易类型，为空表示查询所有类型
     * @param beginDate 查询开始时间
     * @param endDate   查询结束时间
     * @return 交易明细
     */
    public HashMap<String, Object> queryPurseTransLogs(
            String userId, String transType, Date beginDate, Date endDate, Integer page, Integer pageSize
    ) throws Exception {
        PurseTranslogCriteria PurseTranslogCriteria = new PurseTranslogCriteria();
        PurseTranslogCriteria.Criteria wtc = PurseTranslogCriteria.createCriteria();
        //调用查询子账户方法，获取子账户信息
        PurseAccount Account = queryPurseAccount(userId);
        //判断该用户子账户信息是否存在
        if (Account == null) {
            return null;
        }
        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }
        //判断类似是否有值，有值则添加类型条件，只查询该类型的交易记录
        if (!StringUtils.isEmpty(transType)) {
            wtc.andTransTypeEqualTo(transType);
        }
        //获取钱包账户主键ID作为条件查询所属钱包下的交易记录
        wtc.andAccountIdEqualTo(Account.getAccountId());
        //添加所查时间范围条件
        wtc.andCreateTimeBetween(beginDate, endDate);
        //得到总条数
        long count = this.purseTranslogMapper.countByExample(PurseTranslogCriteria);
        //根据订单创建时间排序并且分页
        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);
        PurseTranslogCriteria.setOrderByClause(orderString);
        //返回交易记录list
        List<PurseTranslog> list = this.purseTranslogMapper.selectByExample(PurseTranslogCriteria);

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> transMap = new HashMap<String, Object>();
        transMap.put("transLogList", list);
        transMap.put("page", pageObject);
        return transMap;
    }

    public int updatePurseSubAccount(PurseSubAccount purseSubAccount){
        return this.purseSubAccountMapper.updateByPrimaryKeySelective(purseSubAccount);
    }

    public HashMap<String, Object> queryCoinOperateLog(String filter, Integer page, Integer pageSize) {
        PurseTranslogCriteria  purseCriteria = new PurseTranslogCriteria();
        PurseTranslogCriteria.Criteria criteria = purseCriteria.createCriteria();

        if (!StringUtils.isEmpty(filter)) {
            criteria.andAccountIdLike("%" + filter + "%");
        }
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }

        long count = purseTranslogMapper.countByExample(purseCriteria);

        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);

        purseCriteria.setOrderByClause(orderString);

        List<PurseTranslog> purseTranslog = this.purseTranslogMapper.selectByExample(purseCriteria);

        if (CollectionUtils.isEmpty(purseTranslog)) {
            purseTranslog = new ArrayList<PurseTranslog>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("purseTranslog", purseTranslog);
        newMap.put("page", pageObject);
        return newMap;
    }

    public HashMap<String,Object> queryUserCoinDetail(String filter,Integer page,Integer pageSize){
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        long count = customPurseMapper.queryCoinDetailCount(filter);
        List<UserCoinDetail> coinDetails = customPurseMapper.queryUserCoinDetail(filter,page*pageSize,pageSize);

        if (CollectionUtils.isEmpty(coinDetails)) {
            coinDetails = new ArrayList<UserCoinDetail>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("coinDetails", coinDetails);
        newMap.put("page", pageObject);
        return newMap;
    }


    public HashMap<String,Object> queryPurseTransLogByUserId(String filter,Integer page,Integer pageSize){
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        long count = customPurseMapper.queryPurseTransLogByUserIdCount(filter);
        List<PurseTranslogAdmin> purseTranslogAdmin = customPurseMapper.queryPurseTransLogByUserId(filter,page*pageSize,pageSize);

        if (CollectionUtils.isEmpty(purseTranslogAdmin)) {
            purseTranslogAdmin = new ArrayList<PurseTranslogAdmin>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("purseTranslogAdmin", purseTranslogAdmin);
        newMap.put("page", pageObject);
        return newMap;
    }

    public List<PurseSubAccount> selectByAccountIdAndCoinName(String accountId,String coinName)
    {
        PurseSubAccountCriteria purseCriteria = new PurseSubAccountCriteria();
        PurseSubAccountCriteria.Criteria criteria = purseCriteria.createCriteria();
        criteria.andAccountIdEqualTo(accountId).andSubAccountTypeEqualTo(coinName);
        return purseSubAccountMapper.selectByExample(purseCriteria);
    }

    public int insertTransLog(PurseTranslog translog){
        return this.purseTranslogMapper.insertSelective(translog);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("returnedMessage" + message.toString()+"==="+replyCode+"==="+replyText+"==="+exchange+"==="+routingKey);
    }

    /**
     * 查询交易在以太坊是否成功
     * @param txHash    交易ID
     * @return          是否成功
     */
    public boolean checkTxhash(String txHash) throws CommonException{
        Map<String,String> map = new HashMap<>();
        map.put("module","transaction");
        map.put("action","gettxreceiptstatus");
        map.put("txhash",txHash);
        map.put("apikey",apikey);
        String request = HttpUtils.requestPostForm("https://api.etherscan.io/api",map,null);
        JSONObject json  = JSONObject.parseObject(request);
        if (json.isEmpty()){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"返回数据为空");
        }
        if (json.getString("status").equals("1")){
            JSONObject result = json.getJSONObject("result");
            if (result.isEmpty()){
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"查询失败,结果集内无数据");
            }else{
                if(result.getString("status").equals("1")){
                    return true;
                }else {
                    return false;
                }
            }
        }else{
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"查询失败");
        }
    }

    /**
     * 查询所有待核对以太链的交易记录
     * @return
     */
    public List<PurseTranslog> queryTransNeedCheck() throws Exception{
        PurseTranslogCriteria purseTranslogCriteria = new PurseTranslogCriteria();
        PurseTranslogCriteria.Criteria criteria = purseTranslogCriteria.createCriteria();
        criteria.andTransDetailIdIsNotNull();
        criteria.andOrderStatusEqualTo(PurseStatusEnum.pending.toString());
        criteria.andTransDirectorEqualTo(PTransDirectorEnum.OUT.toString());
        List<PurseTranslog> list = purseTranslogMapper.selectByExample(purseTranslogCriteria);
        return list;
    }

    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.checkTransStatusQueue)
    public void wallet_check_status(String data, Channel channel, Message message) throws Exception {
        try {
            JSONArray json = JSONArray.fromObject(data);
            logger.info("wallet_check_status:本次共"+json.size()+"个订单需要监测状态");
            for (int i=0;i<json.size();i++){
                PurseTranslog trans = (PurseTranslog) net.sf.json.JSONObject.toBean(json.getJSONObject(i),PurseTranslog.class);
                if(checkTxhash(trans.getTransDetailId())){
                    logger.info("交易"+trans.getTransId()+"链上检测为成功状态");
                    trans.setOrderStatus(PurseStatusEnum.success.toString());
                    int success = purseTranslogMapper.updateByPrimaryKey(trans);
                    if(success>0){
                        logger.info("交易"+trans.getTransId()+"更新状态为成功");
                    }
                }else{
                    logger.info("交易"+trans.getTransId()+"链上检测为失败状态");
                }
            }

            //抛弃此数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            //异常存入队列保存数据
            sendWalletQueues(RabbitConfig.checkTransStatusDeadQueue, net.sf.json.JSONObject.fromObject(data).toString());
            //抛弃此数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 积分兑换BCT 流程
     * @param userId
     * @param BCTNum
     * @throws CommonException
     */
    public void TBToBCT(String userId, BigDecimal BCTNum) throws CommonException {
        String transTitle = "积分兑换BCT";
        String remark = "用户用积分兑换得到" + BCTNum + "BCT";
        this.transFlow(userId, PSubAccountTypeEnum.BCT, BCTNum, transTitle, remark, PTransDirectorEnum.INNER, PTransTypeEnum.TB_TO_BCT, PurseStatusEnum.success, false);
    }

    /**
     * 币种交易流程
     * @param userId 用户id
     * @param subAccountTypeEnum 子账号类型
     * @param score 交易积分
     * @param transTitle 交易标题
     * @param remark 交易备注
     * @param transDirectorEnum 交易流向
     * @param transTypeEnum 交易类型
     * @param purseStatusEnum 交易状态
     * @param frozenStatus 冻结标记
     * @throws CommonException
     */
    @Transactional(rollbackFor=Exception.class)
    public void transFlow(String userId, PSubAccountTypeEnum subAccountTypeEnum, BigDecimal score, String transTitle,
                           String remark, PTransDirectorEnum transDirectorEnum,PTransTypeEnum transTypeEnum,PurseStatusEnum purseStatusEnum, boolean frozenStatus) throws CommonException {
        //查询 有效 钱包账号
        if (score.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"货币不能为0或负数");
        }
        PurseAccountCriteria cr = new PurseAccountCriteria();
        cr.or().andUserIdEqualTo(userId).andAccountStatusEqualTo(PAccountStatusEnum.EFFECTIVE.toString());
        List<PurseAccount> accountList = this.purseAccountMapper.selectByExample(cr);
        if (CollectionUtils.isEmpty(accountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "钱包账号不存在,或已被锁定");
        }
        String accountId = accountList.get(0).getAccountId();
        // 根据subAccountTypeEnum和AccountId查询 子账号
        PurseSubAccountCriteria criteria = new PurseSubAccountCriteria();
        criteria.or().andAccountIdEqualTo(accountId).andSubAccountTypeEqualTo(subAccountTypeEnum.toString());
        List<PurseSubAccount> subAccountList = this.purseSubAccountMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(subAccountList)) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "子账号不存在");
        }
        PurseSubAccount purseSubAccount = subAccountList.get(0);
        String subAccountId = purseSubAccount.getSubAccountId();
        BigDecimal sourceBalance = purseSubAccount.getTotalAivilable();
        if (transDirectorEnum.toString().equals("OUT")) {
            if (score.compareTo(BigDecimal.ZERO) > 0) {
                if (purseSubAccount.getAvailableAivilable().compareTo(score) <0) {
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"您的货币不足");
                }
            }
        }
        //更新子账户
        int result = 0;
        if (!frozenStatus) {
            switch (transDirectorEnum){
                case IN:
                    result = this.customTransFlowMapper.updateFalseAddPurse(score, subAccountId);
                    break;
                case OUT:
                    result = this.customTransFlowMapper.updateFalseSubPurse(score, subAccountId);
                    break;
                case INNER:
                    result = this.customTransFlowMapper.updateFalseAddPurse(score, subAccountId);
                    break;
                default:
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
            }

        } else {
            switch (transDirectorEnum){
                case IN:
                    //增加总的，增加冻结的
                    result = this.customTransFlowMapper.updateTrueAddPurse(score, subAccountId);
                    break;
                case OUT:
                    //扣除可用的，增加冻结的
                    result = this.customTransFlowMapper.updateTrueSubPurse(score, subAccountId);
                    break;
                default:
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
            }
        }
        if (result == 0) {
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"交易失败");
        }

        //新增交易记录
        PurseTranslog purseTranslog1 = new PurseTranslog();
        String transId1 = IdUtil.getTransId();

        purseTranslog1.setTransId(transId1);
        purseTranslog1.setTransType(transTypeEnum.toString());
        purseTranslog1.setAccountId(accountId);
        purseTranslog1.setSubAccountId(subAccountId);
        purseTranslog1.setSubAccountType(subAccountTypeEnum.toString());
        purseTranslog1.setTransTitle(transTitle);
        purseTranslog1.setTransAmount(score);
        purseTranslog1.setTransDirector(transDirectorEnum.toString());
        purseTranslog1.setSourceBalance(sourceBalance);
        if (!frozenStatus){
            switch (transDirectorEnum){
                case IN:
                    purseTranslog1.setTageetBalance(sourceBalance.add(score));
                    break;
                case OUT:
                    purseTranslog1.setTageetBalance(sourceBalance.subtract(score));
                    break;
                case INNER:
                    purseTranslog1.setTageetBalance(sourceBalance.add(score));
                    break;
                default:
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
            }
        }else {
            switch (transDirectorEnum){
                case IN:
                    purseTranslog1.setTageetBalance(sourceBalance.add(score));
                    break;
                case OUT:
                    purseTranslog1.setTageetBalance(sourceBalance);
                    break;
                default:
                    throw new CommonException(ErrorCodeEnum.EXCEPTION,"不知流向的交易");
            }
        }
        purseTranslog1.setRemark(remark);
        purseTranslog1.setOrderStatus(purseStatusEnum.toString());
        int ret5 = this.purseTranslogMapper.insertSelective(purseTranslog1);
        if (ret5 == 0) {
            throw new CommonException(ErrorCodeEnum.DBERROR, "新增交易记录失败");
        }
    }

    /**
     * 查询已审核的提币订单
     * @param filter 条件
     * @param page 页码
     * @param pageSize 页数
     * @return
     */
    public HashMap<String, Object> queryAuditOrderByConditional(String filter,Integer page,Integer pageSize)
    {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        long count = customPurseMapper.queryAuditOrderCount(filter);
        List<WithdrawOrder> auditOrder;
        auditOrder = customPurseMapper.queryAuditOrder(filter,page*pageSize,pageSize);

        if (CollectionUtils.isEmpty(auditOrder)) {
            auditOrder = new ArrayList<WithdrawOrder>();
        }
        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        newMap.put("auditOrder", auditOrder);
        newMap.put("page", pageObject);
        return newMap;
    }

    /**
     *  根据钱包地址查询用户是否为平台用户
     * @param address 条件
     * @return
     */
    public User queryUserInPlatform(String address) throws CommonException {
        try {
            PurseSubAccountCriteria purseSubAccountCriteria = new PurseSubAccountCriteria();
            PurseSubAccountCriteria.Criteria criteria = purseSubAccountCriteria.createCriteria();
            criteria.andPurseAddressEqualTo(address);
            List<PurseSubAccount> purseSubAccounts = purseSubAccountMapper.selectByExample(purseSubAccountCriteria);
            if(CollectionUtils.isEmpty(purseSubAccounts)){
                return null;
            }else{
                PurseAccount purseAccount = purseAccountMapper.selectByPrimaryKey(purseSubAccounts.get(0).getAccountId());
                if(purseAccount!=null){
                    User user = userMapper.selectByPrimaryKey(purseAccount.getUserId());
                    if(user!=null){
                        return user;
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            }
        }catch (Exception e){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"未知异常");
        }

    }


    /**
     * 外部转账审核拒绝
     * @param transId 交易ID
     * @param remark 备注
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean transExamine(String transId,String remark) throws CommonException{
        if(StringUtils.isEmpty(transId)){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"交易ID为空");
        }
        //查询该ID是否为待审批的转账订单
        PurseTranslogCriteria purseTranslogCriteria = new PurseTranslogCriteria();
        PurseTranslogCriteria.Criteria criteria = purseTranslogCriteria.createCriteria();
        criteria.andTransIdEqualTo(transId);
        criteria.andTransPartyIsNotNull();
        criteria.andOrderStatusEqualTo(PurseStatusEnum.launch.toString());
        criteria.andIsBlockTransEqualTo(new Byte("0"));
        criteria.andTransDirectorEqualTo(PTransDirectorEnum.OUT.toString());
        List<PurseTranslog> list = purseTranslogMapper.selectByExample(purseTranslogCriteria);
        if(CollectionUtils.isEmpty(list)){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"此ID无转账记录");
        }
        //当前转账记录
        PurseTranslog purseTranslog = list.get(0);
        BigDecimal transAmount = purseTranslog.getTransAmount();
        PurseAccount purseAccount = purseAccountMapper.selectByPrimaryKey(purseTranslog.getAccountId());

        purseTranslog.setRemark(purseTranslog.getRemark()+remark);
        purseTranslog.setOrderStatus(PurseStatusEnum.Reject.toString());
        int i1 = purseTranslogMapper.updateByPrimaryKeySelective(purseTranslog);
        if (i1 > 0) {
            this.transFlow(purseAccount.getUserId(), PSubAccountTypeEnum.BCT, transAmount, "驳回提币", "请求被驳回，退还币", PTransDirectorEnum.IN, PTransTypeEnum.DISMISSAL, PurseStatusEnum.success, false);
            return true;
        }else{
            throw new CommonException(ErrorCodeEnum.DBERROR,"更新交易记录失败");
        }
    }

    /**
     * 内部转账审核
     * @param transId 交易ID
     * @param isExamine 是否通过审核
     * @param remark 备注
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean transInsideExamine(String transId,boolean isExamine,String remark) throws CommonException{
        if(StringUtils.isEmpty(transId)){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"交易ID为空");
        }
        //查询该ID是否为待审批的转账订单
        PurseTranslogCriteria purseTranslogCriteria = new PurseTranslogCriteria();
        PurseTranslogCriteria.Criteria criteria = purseTranslogCriteria.createCriteria();
        criteria.andTransIdEqualTo(transId);
        criteria.andTransPartyIsNotNull();
        criteria.andOrderStatusEqualTo(PurseStatusEnum.launch.toString());
        criteria.andIsBlockTransEqualTo(new Byte("0"));
        criteria.andTransDirectorEqualTo(PTransDirectorEnum.OUT.toString());
        List<PurseTranslog> list = purseTranslogMapper.selectByExample(purseTranslogCriteria);
        if(CollectionUtils.isEmpty(list)){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"此ID无转账记录");
        }
        //当前转账记录
        PurseTranslog purseTranslog = list.get(0);

        //判断是不是内部转账
        User user = queryUserInPlatform(purseTranslog.getTransParty());
        if(user==null){
            if(purseTranslog.getUpanddown()==0){
                throw new CommonException(ErrorCodeEnum.EXCEPTION,"非平台钱包地址不允许内部转账");
            }
        }
        BigDecimal transAmount = purseTranslog.getTransAmount();
        PurseAccount purseAccount = purseAccountMapper.selectByPrimaryKey(purseTranslog.getAccountId());

        purseTranslog.setRemark(purseTranslog.getRemark()+remark);
        //是否通过
        if(isExamine){
            purseTranslog.setOrderStatus(PurseStatusEnum.success.toString());
        }else{
            purseTranslog.setOrderStatus(PurseStatusEnum.Reject.toString());
        }
        int i1 = purseTranslogMapper.updateByPrimaryKeySelective(purseTranslog);
        if (i1 > 0) {
            //是否通过
            if(isExamine){
                this.transFlow(user.getUserId(), PSubAccountTypeEnum.BCT, transAmount, "内部入账", "内部入账成功，到账"+transAmount, PTransDirectorEnum.IN, PTransTypeEnum.RECHARGE, PurseStatusEnum.success, false);
            }else{
                this.transFlow(purseAccount.getUserId(), PSubAccountTypeEnum.BCT, transAmount, "驳回提币", "请求被驳回，退还币", PTransDirectorEnum.IN, PTransTypeEnum.DISMISSAL, PurseStatusEnum.success, false);
            }

            return true;
        }else{
            throw new CommonException(ErrorCodeEnum.DBERROR,"更新交易记录失败");
        }
    }

    @Transactional
    public int updateAuditOrder(List<CheckedOrder> checkedOrders)
    {
        try {
            for (CheckedOrder checkedOrder : checkedOrders) {
                customPurseMapper.updateAudiOrder(checkedOrder.getTxid(),checkedOrder.getTransId());
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return 1;
    }


}
