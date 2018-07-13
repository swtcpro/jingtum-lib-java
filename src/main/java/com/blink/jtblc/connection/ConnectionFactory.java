package com.blink.jtblc.connection;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionFactory {
	final static Logger logger = LoggerFactory.getLogger(Connection.class);

	public static Connection getCollection(String url) {
		Connection connection = null;
		WebSocket webSocket = null;
		try {
			if (connection == null) {
				webSocket = new WebSocket(URI.create(url));
				webSocket.connect();
				int time = 0;
				connection = new Connection(webSocket);
				while (!"open".equals(webSocket.getStatus())) {
					Thread.sleep(100);
					time += 100;
					if (time > 10 * 1000) {
						throw new RuntimeException("服务器连接超时");

					}
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return connection;
	}
}
