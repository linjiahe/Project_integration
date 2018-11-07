package com.blockchain.commune.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    //钱包业务队列名
    public final static String queues = "test_wallet_op";

    //监听钱包业务队列名
    public final static String listenQueues  = "test_wallet_op_notify";

    //监听入账业务队列名
    public final static String rechargeQueues  = "test_wallet_deposit_notify";

    public final static String rechargeQueues_dead = "wallet_deposit_notify_dead";

    //钱包业务处理异常队列名
    public final static String queues_dead = queues+"_dead";

    //校验订单状态队列名
    public final static String checkTransStatusQueue = "wallet_check_status";

    //校验订单状态处理异常队列名
    public final static String checkTransStatusDeadQueue = checkTransStatusQueue+"_dead";

    //存放处理异常的数据队列
    @Bean
    public Queue wallet_op_dead() {
        return new Queue(queues_dead);
    }
    //校验订单状态队列
    @Bean
    public Queue checkTransStatusQueue() {
        return new Queue(checkTransStatusQueue);
    }
    //校验订单状态处理队列
    @Bean
    public Queue checkTransStatusDeadQueue() {
        return new Queue(checkTransStatusDeadQueue);
    } //校验订单状态处理队列

    @Bean
    public Queue RechargeDeadQueue() {
        return new Queue(rechargeQueues_dead);
    }

    @Bean
    public Queue listenQueues() {
        return new Queue(listenQueues);
    }

    @Bean
    public Queue queues() {
        return new Queue(queues);
    }
}
