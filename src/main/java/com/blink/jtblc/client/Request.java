package com.blink.jtblc.client;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Request {
	protected static final Logger logger = LoggerFactory.getLogger(Request.class);
	ObjectMapper mapper = new ObjectMapper();
	private Remote remote = null;
	private String command ="";

	public Request(Remote remote,String command) {
		this.remote = remote;
		this.command = command;
	}
	public Request() {
		
	}
	/**
	 * 1.1	提交请求
	 * @param data
	 * @return 包含两个参数：错误信息和结果信息
	 */
	public String submit(Map<String, Object> data) {
		return remote.sendMessage(this.command, data);
	}
	
	/*
	 * @param ledger 账本高度或者账号hash
	 */
	public Map selectLedger(Object ledger) {
		Map message = new HashMap();

		if(ledger instanceof String) {
			message.put("ledger_index", ledger.toString());
		}else if(ledger instanceof Number) {
			message.put("ledger_index", ((Number) ledger).longValue());
		}else if(Pattern.matches("^[A-F0-9]+$", ledger.toString())) {
			message.put("ledger_index", ledger.toString());
		}else {
			message.put("ledger_index","validated");
		}
		return message;
	}
	
	
}
