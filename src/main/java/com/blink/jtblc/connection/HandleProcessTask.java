package com.blink.jtblc.connection;

import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blink.jtblc.utils.JsonUtils;

public class HandleProcessTask implements Callable<String> {
	final static Logger logger = LoggerFactory.getLogger(HandleProcessTask.class);
	private Map<String, Object> params;
	private WebSocket webSocket;
	
	public HandleProcessTask(Map<String, Object> params, WebSocket webSocket) {
		this.params = params;
		this.webSocket = webSocket;
	}
	
	@Override
	public String call() throws Exception {
		String uuid = webSocket.send(params);
		while (webSocket.getMessage(uuid) == null) {
		}
		String result = webSocket.getMessageAndClean(uuid);
		Map map = JsonUtils.toObject(result, Map.class);
		if (map.get("status").equals("error")) {
			throw new Exception("接口调用异常,异常信息：" + result);
		}
		return result;
	}
}
