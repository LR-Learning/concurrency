package com.learning.concurrency.example.count;

import lombok.extern.slf4j.Slf4j;

import java.awt.image.VolatileImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author: LR
 * @Descriprition: volatile 不具有原子性  适合作为状态标记量
 * @Date: Created in 21:57 2019/11/27
 * @Modified By:
 **/
@Slf4j
public class CountExample3 {

    // 请求总数
    public static int clientTotal = 5000;
    // 同时并发执行的线程数
    public static int threadTotal = 200;

    public static volatile int count = 0;

    // 增加线程方法
    private static void add(){
        count++;
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
