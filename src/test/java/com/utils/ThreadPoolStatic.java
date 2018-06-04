package com.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolStatic {
    public static Integer POOL_SIZE = 13;
    public static ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
    
    public static synchronized void addThread(Runnable thread) {
        pool.execute(thread);
    }
    
    public static synchronized void shutdown() {
        pool.shutdown();
    }
    
}