package com.blink.jtblc.client.bean;

public class FinalFields {
    private String account;
    private AmountInfo balance;
    private Integer flags;
    private Integer ownerCount;
    private Integer sequence;
    
    private AmountInfo highLimit;
    private String highNode;
    private AmountInfo lowLimit;
    private String lowNode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public AmountInfo getBalance() {
		return balance;
	}

	public void setBalance(AmountInfo balance) {
		this.balance = balance;
	}

	public AmountInfo getHighLimit() {
		return highLimit;
	}

	public void setHighLimit(AmountInfo highLimit) {
		this.highLimit = highLimit;
	}

	public String getHighNode() {
		return highNode;
	}

	public void setHighNode(String highNode) {
		this.highNode = highNode;
	}

	public AmountInfo getLowLimit() {
		return lowLimit;
	}

	public void setLowLimit(AmountInfo lowLimit) {
		this.lowLimit = lowLimit;
	}

	public String getLowNode() {
		return lowNode;
	}

	public void setLowNode(String lowNode) {
		this.lowNode = lowNode;
	}

	public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Integer getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(Integer ownerCount) {
        this.ownerCount = ownerCount;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
