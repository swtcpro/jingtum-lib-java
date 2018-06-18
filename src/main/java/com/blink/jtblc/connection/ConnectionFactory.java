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
				connection = new Connection(webSocket);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return connection;
	}
}
