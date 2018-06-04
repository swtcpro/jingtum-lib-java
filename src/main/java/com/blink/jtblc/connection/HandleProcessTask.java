package com.blink.jtblc.connection;

import com.blink.jtblc.utils.JsonUtils;

import java.util.Map;
import java.util.concurrent.Callable;

public class HandleProcessTask implements Callable<String> {
	private Map<String, Object> params;
	private WebSocket webSocket;
	
	public HandleProcessTask(Map<String, Object> params, WebSocket webSocket) {
		this.params = params;
		this.webSocket = webSocket;
	}
	
	@Override
	public String call() throws Exception {
		String uuid = webSocket.send(params);
		Thread.sleep(1000);
		String result = "";
		while (result == "" || result == null) {
			result = webSocket.getMessage(uuid);
			Map map = JsonUtils.toObject(result,Map.class);
			if(map.get("status").equals("error")){
				throw new Exception("接口调用异常,异常信息："+result);
			}
		}
		return result;
	}
}
