package com.example.mall.note.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * <dl>
 * <dt><p>@description zk config <p>@date 2021/12/27 11:35</dt>
 * <dd><p>place some brief intro here</dd>
 * </dl>
 */
@Configuration
public class ZkConfig {
    @Value("${zookeeper.address:localhost:2181}")
    private String connectString;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public ZooKeeper zooKeeper() {
        ZooKeeper zooKeeper = null;
        try {
            logger.info("【 ZooKeeper 】 初始化 ....");
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(connectString, 3000, new MyWatcherDefault() {
                @Override
                public void process(WatchedEvent event) {

                    //  三种监听类型： 创建，删除，更新
                    logger.info("【Watcher】监听事件={}", event.getState());
                    logger.info("【Watcher】监听路径为={}", event.getPath());
                    logger.info("【Watcher】监听的类型为={}", event.getType());

                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        logger.info("【ZooKeeper】 连接成功 收到了服务端的响应事件 zk`s Watcher 被触发了");
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            logger.info("【ZooKeeper】 当前连接状态 = {}", zooKeeper.getState());
        } catch (Exception e) {
            logger.error("【ZooKeeper】 初始化 {} 连接异常 = {}", connectString, e);
            e.printStackTrace();
        }
        return zooKeeper;
    }
}

