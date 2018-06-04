package com.blink.jtblc.exceptions;

@SuppressWarnings("serial")
public class TransactionException extends RuntimeException{

	//友好提示的code码
	private int friendlyCode;

	//友好提示
	private String friendlyMsg;


    public TransactionException(int friendlyCode, String result){
        this.friendlyCode = friendlyCode;
        this.friendlyMsg = result;

    }
    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
		return friendlyCode;
	}

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(int code) {
		this.friendlyCode = code;
	}

	public String getMessage() {
		return friendlyMsg;
	}

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
		this.friendlyMsg = message;
	}

}
