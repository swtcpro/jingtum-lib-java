package com.blink.jtblc.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blink.jtblc.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Connection {
	final static Logger logger = LoggerFactory.getLogger(Connection.class);
	private static WebSocket webSocket;
	
	public Connection(WebSocket webSocket) {
		this.webSocket = webSocket;
	}
	
	public static String sendMessage(String command, Map<String, Object> data) {
		Map<String, Object> params = new HashMap();
		params.put("command", command);
		params.putAll(data);
		logger.debug("WebSocket参数： " + JsonUtils.toJsonString(params));
		return submit(params);
	}
	
	/**
	 *  发送请求
	 * 
	 * @param params 参数
	 * @return string
	 */
	public static String submit(Map<String, Object> params) {
		String result = "";
		try {
			Future<String> future = ExecutorPool.getExecutorPool().submit(new HandleProcessTask(params, webSocket));
			try {
				while (!future.isDone()) {
					result = future.get();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 *  发送请求
	 * 
	 * @param params 参数
	 * @return map
	 */
	public static Map<String, String> send(Map<String, Object> params) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Future<String> future = ExecutorPool.getExecutorPool().submit(new HandleProcessTask(params, webSocket));
			try {
				while (!future.isDone()) {
					ObjectMapper mapper = new ObjectMapper();
					String result = future.get();
					map = mapper.readValue(result, Map.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 关闭webSocket连接
	 */
	public static void close() {
		if (webSocket != null) {
			webSocket.close();
		}
	}

	/**
	 *
	 * @return
	 */
	public static String getState(){
		return webSocket.getReadyState().name();
	}
}
