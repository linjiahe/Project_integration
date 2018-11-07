package com.blockchain.commune.job;


import com.blockchain.commune.config.RabbitConfig;
import com.blockchain.commune.exception.CommonException;
import com.blockchain.commune.model.PurseTranslog;
import com.blockchain.commune.service.wallet.PurseService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PurseCheckTxHashStatus {

    @Autowired
    PurseService purseService;

    private Logger logger = LoggerFactory.getLogger(PurseCheckTxHashStatus.class);

    /**
     * 定时写入队列需要校验状态的交易记录
     * @throws Exception
     */
    //@Scheduled(fixedRate = 3 * 60 * 1000)
    public void CheckTransStatusJob() throws Exception {
        try {
            //查询共有多少需要校验状态的交易记录
            List<PurseTranslog> list = purseService.queryTransNeedCheck();
            if(list.size()>0){
                JSONArray jsonArray = JSONArray.fromObject(list);
                purseService.sendWalletQueues(RabbitConfig.checkTransStatusQueue,jsonArray.toString());
            }else {
                logger.info("无需要检测状态的订单");
            }
        }catch (CommonException e){
            logger.error("CheckTransStatusJob:"+e.getMessage());
        }

    }

}
