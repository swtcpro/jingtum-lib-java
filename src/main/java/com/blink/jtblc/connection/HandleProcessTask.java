package com.blink.jtblc.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
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
		String result ="";
		if(StringUtils.isNotBlank(uuid)) {
			result = webSocket.getMessageAndClean(uuid);
		}
		Map map = JsonUtils.toObject(result, Map.class);
		if (map.get("status").equals("error")) {
			throw new Exception("接口调用异常,异常信息：" + result);
		}
		this.handleResponse(map);
		return result;
	}



	public void handleResponse(Map result) throws Exception {
		if(((Map)result.get("result")).get("server_status")!=null) {
			Map map =(Map)result.get("result");
			Map _status =new HashMap();
			_status.put("load_base", map.get("load_base"));
			_status.put("load_factor", map.get("load_factor"));
			if(map.get("pubkey_node")!=null) {
				_status.put("pubkey_node", map.get("pubkey_node"));
			}
			_status.put("server_status", map.get("server_status"));

			String[] onlineStates = new String[] {"syncing", "tracking", "proposing", "validating", "full", "connected"};

			for(int i =0;i<onlineStates.length;i++) {
				if(map.get("server_status").equals(onlineStates[i])) {
					//todo
				}
			}
		}

	}



	public String sendMessage(String command, Map<String, Object> data) throws Exception{
		Map<String, Object> params = new HashMap();
		params.put("command", command);
		params.putAll(data);
		logger.debug("WebSocket参数： " + JsonUtils.toJsonString(params));
		return webSocket.send(params);
	}
}
