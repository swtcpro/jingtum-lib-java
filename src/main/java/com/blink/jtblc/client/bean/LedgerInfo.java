package com.blink.jtblc.client.bean;

import java.util.List;

/**
 * 账本详情
 * 调用方法：
 */
public class LedgerInfo {
	private Integer feeBase;// 基础费用(手续费计算公式因子)
	private Integer feeRef;// 引用费用(手续费计算公式因子)
	private String hostid;// 主机名
	private String ledgerHash;// 账本hash
	private Integer ledgerIndex;// 区块高度
	private Integer ledgerTime;// 账本关闭时间
	private String pubkeyNode;// 节点公钥
	private Integer reserveBase;// 账号保留值
	private Integer reserveInc;// 用户每次挂单或信任冻结数量
	private String serverStatus;// 服务器状态
	private String validatedLedgers;// 账本区间
	private List<Line> lines;
	
	public List<Line> getLines() {
		return lines;
	}
	
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
	
	public Integer getFeeBase() {
		return feeBase;
	}

	public void setFeeBase(Integer feeBase) {
		this.feeBase = feeBase;
	}
	
	public Integer getFeeRef() {
		return feeRef;
	}
	
	public void setFeeRef(Integer feeRef) {
		this.feeRef = feeRef;
	}
	
	public String getHostid() {
		return hostid;
	}
	
	public void setHostid(String hostid) {
		this.hostid = hostid;
	}
	
	public String getLedgerHash() {
		return ledgerHash;
	}
	
	public void setLedgerHash(String ledgerHash) {
		this.ledgerHash = ledgerHash;
	}
	
	public Integer getLedgerIndex() {
		return ledgerIndex;
	}
	
	public void setLedgerIndex(Integer ledgerIndex) {
		this.ledgerIndex = ledgerIndex;
	}
	
	public Integer getLedgerTime() {
		return ledgerTime;
	}
	
	public void setLedgerTime(Integer ledgerTime) {
		this.ledgerTime = ledgerTime;
	}
	
	public String getPubkeyNode() {
		return pubkeyNode;
	}
	
	public void setPubkeyNode(String pubkeyNode) {
		this.pubkeyNode = pubkeyNode;
	}
	
	public Integer getReserveBase() {
		return reserveBase;
	}
	
	public void setReserveBase(Integer reserveBase) {
		this.reserveBase = reserveBase;
	}
	
	public Integer getReserveInc() {
		return reserveInc;
	}
	
	public void setReserveInc(Integer reserveInc) {
		this.reserveInc = reserveInc;
	}
	
	public String getServerStatus() {
		return serverStatus;
	}
	
	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus;
	}
	
	public String getValidatedLedgers() {
		return validatedLedgers;
	}
	
	public void setValidatedLedgers(String validatedLedgers) {
		this.validatedLedgers = validatedLedgers;
	}
}
