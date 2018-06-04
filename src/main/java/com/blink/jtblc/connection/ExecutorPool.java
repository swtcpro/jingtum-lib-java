package com.blink.jtblc.connection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorPool {
	private static ExecutorService executorService;
	
	public static ExecutorService getExecutorPool() {
		if (executorService == null) {
			executorService = Executors.newCachedThreadPool();
		}
		return executorService;
	}
}
