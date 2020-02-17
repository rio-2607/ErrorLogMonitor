package com.beautyboss.slogen.errorlog.monitor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Author : Slogen
 * Date   : 2020-02-05 14:54
 */
public final class ThreadPoolFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolFactory.class);

    // 发送kafka消息线程池配置
    private static int SEND_MESSAGE_CORESIZE;

    private static int SEND_MESSAGE_MAXSIZE;

    private static int SEND_MESSAGE_KEEPALIVE;

    private static int SEND_MESSAGE_QUEUESIZE;

    static {
        SEND_MESSAGE_CORESIZE = 1;
        SEND_MESSAGE_MAXSIZE = 1;
        SEND_MESSAGE_KEEPALIVE = 120;
        SEND_MESSAGE_QUEUESIZE = 120;
    }

    public static ExecutorService createExecutorService(int coreNum, int maxNum) {
        return new ThreadPoolExecutor(
                coreNum,
                maxNum,
                SEND_MESSAGE_KEEPALIVE, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(SEND_MESSAGE_QUEUESIZE),
                new ThreadFactory("monitor", "SendMonitorMessage"),
                new ThreadPoolExecutor.DiscardOldestPolicy());

    }


    private ThreadPoolFactory(){

    }

    // reject strategy
    static class MonitorRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (!executor.isShutdown()) {
                try {
                    executor.getQueue().put(r);
                } catch (InterruptedException e) {
                    LOGGER.error("When task queue is full, some bad things happened! Message is {}", e);
                }
            }
        }
    }
}
