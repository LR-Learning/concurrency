package com.learning.concurrency.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: LR
 * @Descriprition: 同步锁
 * @Date: Created in 11:11 2019/11/29
 * @Modified By:
 **/
@Slf4j
public class SynchronizedExample1 {
    // 修饰代码块
    public void test1(int j){
        synchronized (this){
            for (int i = 0; i < 10; i++) {
                log.info("test1 - {} - {}", j, i);
            }
        }
    }

    // 修饰一个方法
    public synchronized void test2() {
            for (int i = 0; i < 10; i++) {
                log.info("test2 -{}", i);
            }
    }

    public static void main(String[] args) {
        SynchronizedExample1 example1 = new SynchronizedExample1();
        SynchronizedExample1 example2 = new SynchronizedExample1();
        // 声明一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() ->{
            example1.test1(1);
        });
        executorService.execute(() ->{
            example2.test1(1);
        });

    }
}
