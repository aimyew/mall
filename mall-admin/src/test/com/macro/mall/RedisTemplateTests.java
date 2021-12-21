package com.macro.mall;

import com.macro.mall.base.BaseTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description redis test
 * @date 2021/11/8 10:14
 */
@Slf4j
public class RedisTemplateTests extends BaseTests {
    @Autowired
    @Qualifier(value = "pureStringRedis")
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        //multiplyThreadTest();
        Boolean promotionApprovedLock = redisTemplate.opsForValue().setIfAbsent("magicKey", "magicVal", 60, TimeUnit.SECONDS);
        System.out.println(promotionApprovedLock);
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (!redisTemplate.opsForValue().setIfAbsent("promotionApprovedLock", "pId = 99", 10, TimeUnit.SECONDS)) {
//                    log.info(Thread.currentThread().getName() + "service updateSelective get lock error.. pid = {}", "99");
//                } else {
//                    log.info(Thread.currentThread().getName() + "service updateSelective get lock.. pid = {}", "99");
//                }
//            }
//        });
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (!redisTemplate.opsForValue().setIfAbsent("promotionApprovedLock", "pId = 99", 10, TimeUnit.SECONDS)) {
//                    log.info(Thread.currentThread().getName() + "service updateSelective get lock error.. pid = {}", "99");
//                } else {
//                    log.info(Thread.currentThread().getName() + "service updateSelective get lock.. pid = {}", "99");
//                }
//            }
//        });
//        thread1.start();
//        thread2.start();



    }

    private void multiplyThreadTest() {
        long begaintime = System.currentTimeMillis();//开始系统时间
        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //设置集合点为 10
        final int count = 1;
        CountDownLatch countDownLatch = new CountDownLatch(count);//与countDownLatch.await();实现运行完所有线程之后才执行后面的操作
        //final CyclicBarrier barrier = new CyclicBarrier(count);  //与barrier.await() 实现并发;
        //创建100个线程
        for (int i = 0; i < count; i++) {
            countDownLatch.countDown();
            //barrier.await();
            executorService.execute(() -> {
                try {
                    Boolean lock = Optional.ofNullable(redisTemplate.opsForValue().setIfAbsent("promotionApprovedLock", "pId = 99", 10, TimeUnit.SECONDS)).orElse(false);
                    if (!lock) {
                        log.info(Thread.currentThread().getName() + "service updateSelective get lock error.. pid = {}", "99");
                    } else {
                        log.info(Thread.currentThread().getName() + "service updateSelective get lock.. pid = {}", "99");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            countDownLatch.await();  //这一步是为了将全部线程任务执行完以后，开始执行后面的任务（计算时间，数量）
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        long endTime = System.currentTimeMillis(); //结束时间
        System.out.println(count + " 个  接口请求总耗时 ： " + (endTime - begaintime) + "-----平均耗时为" + ((endTime - begaintime) / count));
    }
}
