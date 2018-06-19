package com.blink.jtblc.client.bean;

/**
 * 市场挂单列表
 */
public class BookOffer {
	private String account;// 账号地址
	private String bookDirectory;//
	private String bookNode;//
	private Integer flags;// 挂单买卖标记
	private String ledgerEntryType;// 账本数据结构类型
	private String ownerNode;//
	private String previousTxnId;// 上一笔交易hash
	private Integer previousTxnLgrSeq;// 上一笔交易所在账本号
	private Integer sequence;// 单子序列号
	private boolean validated;//
	private String takerGets;// 对方得到的。(买卖双方，当货币是swt时，数据类型 为对象;否则为string)
	private TakerPay takerPays;// 对方支付的
	private String index;// 该数据所在索引hash
	private String ownerFunds;// 用户swt资产
	private String quality;// 价格或价格的倒数
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getBookNode() {
		return bookNode;
	}
	
	public void setBookNode(String bookNode) {
		this.bookNode = bookNode;
	}
	
	public Integer getFlags() {
		return flags;
	}
	
	public void setFlags(Integer flags) {
		this.flags = flags;
	}
	
	public String getOwnerNode() {
		return ownerNode;
	}
	
	public void setOwnerNode(String ownerNode) {
		this.ownerNode = ownerNode;
	}
	
	public String getPreviousTxnId() {
		return previousTxnId;
	}
	
	public void setPreviousTxnId(String previousTxnId) {
		this.previousTxnId = previousTxnId;
	}
	
	public Integer getPreviousTxnLgrSeq() {
		return previousTxnLgrSeq;
	}
	
	public void setPreviousTxnLgrSeq(Integer previousTxnLgrSeq) {
		this.previousTxnLgrSeq = previousTxnLgrSeq;
	}
	
	public Integer getSequence() {
		return sequence;
	}
	
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	public boolean isValidated() {
		return validated;
	}
	
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	
	public String getBookDirectory() {
		return bookDirectory;
	}
	
	public void setBookDirectory(String bookDirectory) {
		this.bookDirectory = bookDirectory;
	}
	
	public String getLedgerEntryType() {
		return ledgerEntryType;
	}
	
	public void setLedgerEntryType(String ledgerEntryType) {
		this.ledgerEntryType = ledgerEntryType;
	}
	
	public String getTakerGets() {
		return takerGets;
	}
	
	public void setTakerGets(String takerGets) {
		this.takerGets = takerGets;
	}
	
	public TakerPay getTakerPays() {
		return takerPays;
	}
	
	public void setTakerPays(TakerPay takerPays) {
		this.takerPays = takerPays;
	}
	
	public String getIndex() {
		return index;
	}
	
	public void setIndex(String index) {
		this.index = index;
	}
	
	public String getOwnerFunds() {
		return ownerFunds;
	}
	
	public void setOwnerFunds(String ownerFunds) {
		this.ownerFunds = ownerFunds;
	}
	
	public String getQuality() {
		return quality;
	}
	
	public void setQuality(String quality) {
		this.quality = quality;
	}
}
