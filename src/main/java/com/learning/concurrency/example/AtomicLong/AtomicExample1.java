package com.learning.concurrency.example.AtomicLong;

import com.learning.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: LR
 * @Descriprition: 线程安全
 * @Date: Created in 21:57 2019/11/27
 * @Modified By:
 **/
@Slf4j
@ThreadSafe
public class AtomicExample1 {

    // 请求总数
    public static int clientTotal = 5000;
    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static AtomicLong count = new AtomicLong(0); // count: 工作内存

    // 增加线程方法
    private static void add(){
        count.incrementAndGet(); // 保证线程安全 --- 看源码实现    compareAndSwapInt: 拿当前的值跟底层的值对照  值相同才进行接下来的操作
//        count.getAndIncrement();
    }
    public static void main(String[] args) throws Exception{
        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(clientTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try{
                    semaphore.acquire(); // 每次的允许值
                    add();
                    semaphore.release(); // 释放
                }catch (Exception e){
                    log.error("exception:", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        log.info("count:{}", count);
        executorService.shutdown();
    }
}
