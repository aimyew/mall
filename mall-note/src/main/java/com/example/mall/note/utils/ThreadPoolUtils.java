package com.example.mall.note.utils;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <dl>
 * <dt><p>ThreadPool Utils</dt>
 * <dd><p>place some brief intro here</dd>
 * </dl>
 *
 * <pre> {@code
 *     ThreadPoolExecutor executor = ThreadPoolUtils.createThreadPool(3, 3);
 *     Future<String> submit1 = executor.submit(() -> "res Aa");
 *     Future<String> submit2 = executor.submit(() -> "res Bb");
 *     try {
 *         System.out.println("submit response1 : " + submit1.get());
 *         System.out.println("submit response2 : " + submit2.get());
 *     } catch (Exception e) {
 *         e.printStackTrace();
 *     }
 *     if (!executor.isShutdown()) {
 *         executor.shutdown();
 *     }
 * }</pre>
 *
 * @author a
 */
public class ThreadPoolUtils {
    private static final Integer CORE_POOL_SIZE = 3;
    private static final Integer MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private static final Integer KEEP_ALIVE_TIME = 2;
    private static final Integer QUEUE_SIZE = 6;

    public static ThreadPoolExecutor createThreadPool(Integer corePoolSize, Integer maxPoolSize) {
        Integer corePoolSize_val = Optional.ofNullable(corePoolSize).orElse(CORE_POOL_SIZE);
        Integer maxPoolSize_val = Optional.ofNullable(maxPoolSize).orElse(MAXIMUM_POOL_SIZE);
        return new ThreadPoolExecutor(corePoolSize_val, maxPoolSize_val, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE));
    }

    public static ThreadPoolExecutor createThreadPool(Integer corePoolSize, Integer maxPoolSize, Integer keepAliveTime, Integer queueSize) {
        Integer corePoolSize_val = Optional.ofNullable(corePoolSize).orElse(CORE_POOL_SIZE);
        Integer maxPoolSize_val = Optional.ofNullable(maxPoolSize).orElse(MAXIMUM_POOL_SIZE);
        Integer keepAliveTime_val = Optional.ofNullable(keepAliveTime).filter(e -> e > 0 && e < 3).orElse(KEEP_ALIVE_TIME);
        Integer queueSize_val = Optional.ofNullable(queueSize).filter(e -> e > 0 && e < 7).orElse(QUEUE_SIZE);
        return new ThreadPoolExecutor(corePoolSize_val, maxPoolSize_val, keepAliveTime_val, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize_val));
    }
}
