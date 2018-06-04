package com.blink.jtblc.connection;

import java.net.URI;

public class ConnectionFactory {

	private static Connection connection;

	private static WebSocket webSocket;
	/**
	 * 4.1 创建Connection对象
	 * @return Connection
	 */
	public static Connection getCollection(String url){
		try {
			if(connection==null){
				webSocket = new WebSocket(URI.create(url));
				webSocket.connect();
				connection = new Connection(webSocket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}


	/**
	 * 关闭websocket连接
	 */
	public static void close(){
		try {
			webSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
