package com.blink.jtblc.connection;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocket extends WebSocketClient {
	private Map<String, String> results = new HashMap<String, String>();
	
	public WebSocket(URI serverURI) {
		super(serverURI);
	}
	
	public String getMessage(String sessionId) {
		return results.get(sessionId);
	}
	
	public String send(Map<String, Object> params) throws Exception {
		Double end = Math.pow(10, 3);
		String requestId = String.format(System.currentTimeMillis() + "%0" + 3 + "d", RandomUtils.nextInt(0, end.intValue() - 1));
		params.put("id", requestId);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(params);
		this.send(json);
		return requestId;
	}
	
	@Override
	public void onOpen(ServerHandshake handshakedata) {
		System.out.println("已连接");
	}
	
	@Override
	public void onMessage(String message) {
		try {
			System.out.println("收到消息" + message);
			ObjectMapper mapper = new ObjectMapper();
			Map map = mapper.readValue(message, Map.class);
			results.put(map.get("id").toString(), message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("已离线");
	}
	
	@Override
	public void onError(Exception ex) {
		System.out.println("发生错误");
		ex.printStackTrace();
	}
}
