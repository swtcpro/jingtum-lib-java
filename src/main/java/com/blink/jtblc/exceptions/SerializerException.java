package com.blink.jtblc.exceptions;

public class SerializerException extends RuntimeException {
	private String message;
	
	@Override
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public SerializerException(String message) {
		super(message);
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Exception occurred in Remote.java. ExceptionInfo:" + message;
	}
}
