package com.blink.jtblc.exceptions;

public class RemoteException extends RuntimeException{
	
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RemoteException(String message) {
		super(message);
		this.message = message;
	}

	public String toString() {
		return "Exception occurred in Remote.java. ExceptionInfo:" + message;
	}
}
