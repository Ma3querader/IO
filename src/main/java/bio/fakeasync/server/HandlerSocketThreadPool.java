package bio.fakeasync.server;

/**
 * @Author: panyusheng
 * @Date: 2021/3/30
 * @Version 1.0
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池处理类
 */
public class HandlerSocketThreadPool {
    // 线程池
    private ExecutorService executor;

    public HandlerSocketThreadPool(int maxPoolSize, int queueSize) {
        this.executor = new ThreadPoolExecutor(3, maxPoolSize, 120,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize));
    }

    /**
     * 通过execute方法提交的Runnable任务会进入线程池的工作队列，且为阻塞队列
     */
    public void execute(Runnable task) {
        this.executor.execute(task);
    }

}
