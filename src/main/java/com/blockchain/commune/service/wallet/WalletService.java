package com.blockchain.commune.service.wallet;


import com.blockchain.commune.customemapper.wallet.TranslogMapper;
import com.blockchain.commune.enums.ErrorCodeEnum;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.mapper.*;
import com.blockchain.commune.model.*;
import com.blockchain.commune.utils.ConvertUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class WalletService {

    @Autowired
    WalletAccountMapper walletAccountMapper;

    @Autowired
    WalletSubAccountTypeMapper walletSubAccountTypeMapper;

    @Autowired
    WalletSubAccountMapper walletSubAccountMapper;

    @Autowired
    WalletTranslogMapper walletTranslogMapper;

    @Autowired
    WalletTranslogTypeMapper walletTranslogTypeMapper;

    @Autowired
    WalletDailySummaryMapper walletDailySummaryMapper;

    @Autowired
    TranslogMapper translogMapper;

    /**
     * 查询钱包积分余额  |  子账户查询方法
     * @param userId    用户ID
     * @return  钱包积分总数实体类
     */
    public List<WalletSubAccount> queryBalance(String userId) throws Exception {
        //调用查询钱包积分方法获取该用户钱包积分表主键
        WalletAccount wallet = queryWalletAccount(userId);
        //判断该用户钱包积分钱包是否存在
        if (wallet==null){
            return null;
        }
        WalletSubAccountCriteria wsc = new WalletSubAccountCriteria();
        WalletSubAccountCriteria.Criteria criteria = wsc.createCriteria();
        //根据钱包主键查询
        criteria.andAccountIdEqualTo(wallet.getAccountId());

        //根据积分表主键查询积分余额
        List<WalletSubAccount> list = walletSubAccountMapper.selectByExample(wsc);
        if(CollectionUtils.isEmpty(list)){
            throw new CommonException(ErrorCodeEnum.EXCEPTION,"无钱包账户");
        }
        return list;
    }

    /**
     * 根据用户ID查询钱包积分表
     * @param userId    用户ID
     * @return  用户积分钱包实体类
     */
    public WalletAccount queryWalletAccount(String userId) throws Exception{
        WalletAccountCriteria wa = new WalletAccountCriteria();
        WalletAccountCriteria.Criteria wacCriteria = wa.createCriteria();
        //根据用户ID查询
        wacCriteria.andUserIdEqualTo(userId);

        //根据用户ID获取钱包积分主表的主键
        List<WalletAccount> listWalletAccount = this.walletAccountMapper.selectByExample(wa);
        if(CollectionUtils.isEmpty(listWalletAccount)){
            return null;
        }
        WalletAccount wallet = listWalletAccount.get(0);
        return wallet;
    }

    /**
     * 根据用户ID查询当天交易日结表
     * @param userId    用户ID
     * @return  用户积分交易日结表实体类
     */
    public WalletDailySummary queryWalletDailySummary(String userId) throws Exception{
        //调用查询钱包积分方法获取该用户钱包积分表主键
        WalletAccount wallet = queryWalletAccount(userId);
        //判断该用户钱包积分钱包是否存在
        if (wallet==null){
            return null;
        }
        WalletDailySummaryCriteria wdsc = new WalletDailySummaryCriteria();
        WalletDailySummaryCriteria.Criteria criteria = wdsc.createCriteria();
        //根据钱包主键查询
        criteria.andAccountIdEqualTo(wallet.getAccountId());

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
        criteria.andCreateTimeBetween(dateZero,dateEnd);
        //查询用户日结表
        List<WalletDailySummary> list = this.walletDailySummaryMapper.selectByExample(wdsc);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        WalletDailySummary walletDailySummary = list.get(0);
        return walletDailySummary;
    }

    /**
     * 查询交易明细
     * @param userId        用户ID
     * @param transType     查询交易类型，为空表示查询所有类型
     * @param beginDate     查询开始时间
     * @param endDate       查询结束时间
     * @return              交易明细
     */
    public HashMap<String, Object> queryWalletTransLogs(
            String userId,String[] transType,Date beginDate,Date endDate,Integer page,Integer pageSize
    ) throws Exception{
        WalletTranslogCriteria walletTranslogCriteria = new WalletTranslogCriteria();
        WalletTranslogCriteria.Criteria wtc = walletTranslogCriteria.createCriteria();
        //调用查询子账户方法，获取子账户信息
        List<WalletSubAccount> subAccount = queryBalance(userId);
        //判断该用户子账户信息是否存在
        if (subAccount==null){
            return null;
        }
        if (page == null) {
            page = 0;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        //获取钱包账户主键ID作为条件查询所属钱包下的交易记录
        wtc.andAccountIdEqualTo(subAccount.get(0).getAccountId());
        //添加所查时间范围条件
        wtc.andCreateTimeBetween(beginDate,endDate);

        //判断类似是否有值，有值则添加类型条件，只查询该类型的交易记录
        if (transType!=null){
            for (int i = 0; i < transType.length; i++) {
                walletTranslogCriteria.or().andTransTypeEqualTo(transType[i]);
            }
        }

        //得到总条数
        long count = this.walletTranslogMapper.countByExample(walletTranslogCriteria);
        //根据订单创建时间排序并且分页
        String orderString = String.format(" create_time desc limit %d,%d ", page * pageSize, pageSize);
        walletTranslogCriteria.setOrderByClause(orderString);
        //返回交易记录list
        List<WalletTranslog> list = this.walletTranslogMapper.selectByExample(walletTranslogCriteria);

        HashMap<String, Object> pageObject = ConvertUtil.pageObject(page, pageSize, count);
        HashMap<String, Object> transMap = new HashMap<String, Object>();
        transMap.put("transLogList", list);
        transMap.put("page", pageObject);
        return transMap;
    }

    /**
     * 查询指定交易类型总额
     * @param userId        用户ID
     * @param transType     查询交易类型，为空表示查询所有类型
     * @param beginDate     查询开始时间
     * @param endDate       查询结束时间
     * @return              交易明细
     */
    public BigDecimal translogTypeCount(
            String userId,String transType,Date beginDate,Date endDate
    ) throws Exception{
        //调用查询子账户方法，获取子账户信息
        List<WalletSubAccount> subAccount = queryBalance(userId);
        //判断该用户子账户信息是否存在
        if (subAccount==null){
            return null;
        }
        //获取钱包账户主键ID作为条件查询所属钱包下的交易记录
        String accountId = subAccount.get(0).getAccountId();

        //返回交易记录list
        BigDecimal count = this.translogMapper.translogTypeCount(accountId,transType,beginDate,endDate);
        if(count==null){
            count = BigDecimal.ZERO;
        }
        return count;
    }

}
