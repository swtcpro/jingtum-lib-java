package com.blink.jtblc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
	private static Logger logger = LoggerFactory.getLogger(LogbackTest.class);
	
	public static void main(String[] args) {
		logger.info("Welcome to Silicon Valley");
		logger.debug("Less is more!");
		logger.error("NO PARKING");
	}
}