package com.blockchain.commune.config;

import com.blockchain.commune.model.TeamComputerCache;
import com.blockchain.commune.service.CustomTeamService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
@Component("AutoTeamComputer")
public class AutoTeamComputer {
    @Autowired
    CustomTeamService customTeamService;

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(AutoTeamComputer.class);
    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        Thread thread = new Thread(new Work());
        thread.setName("AutoCoinCome");
        thread.start();
        // initChache
        initCache();
    }

    private void initCache(){
        List<TeamComputerCache> list =  customTeamService.queryCacheUserId();
        if(CollectionUtils.isEmpty(list))
        {
            return;
        }
        try {
            for (TeamComputerCache computerCache : list) {
                queue.put(computerCache.getUserId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addUserId(String userId){
        if(TextUtils.isEmpty(userId))
        {
            return;
        }
        try {
            customTeamService.addUserIdToCache(userId);
            queue.put(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    int count;
    /**
     * Work
     */
    class Work implements Runnable {
        public void run() {
            while (true) {
                String userId = "";
                try {
                    // 内存队列消费
                    userId = queue.take();
                    // 做团队计算
                    doComputer(userId);
                    // 计算完后 移除数据库备份，异常流程则不移除，先不做处理，留做观察
                    customTeamService.deleteUserIdToCache(userId);
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    private void doComputer(String userId) throws Exception {
         if(TextUtils.isEmpty(userId))
         {
             throw new Exception("userId is null");
         }
         customTeamService.updateTeamAmount(userId);
    }
}
