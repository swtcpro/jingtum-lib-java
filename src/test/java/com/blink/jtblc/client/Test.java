package com.blink.jtblc.client;

import com.blink.jtblc.client.bean.Amount;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.connection.ConnectionFactory;

public class Test {
	// 井通节点websocket链接： 
	// ws://ts5.jingtum.com:5020
	// wss://hc.jingtum.com:5020
	// wss://c05.jingtum.com:5020
	private static String server = "ws://ts5.jingtum.com:5020";
	//private static String account = "jLDn91zS5J1sCURGZkBGMj6WNt3dq1dery";
	private static String account = "jJHdzzHwrPggaBKBZZWbC6snJneSvzip5C";
	private static String secret = "ssCXga6r2x398DdLyN3XkLDiT2ZuS";
	private static Integer limit_num = 10;
	
	//4.4 请求底层服务器信息
	public void requestServerInfoTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
        String msg = remote.requestServerInfo();
        System.out.println("requestServerInfoTest:"+msg);
	}
	
	//4.5 获取最新账本信息
	public void requestLedgerClosedTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
        String msg = remote.requestLedgerClosed();
        System.out.println("requestLedgerClosedTest:"+msg);
	}
	
	//4.6 获取某一账本具体信息
	public void requestLedgerTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
		String ledger_index = "213477";
	    String ledger_hash = "";//9A29EE3DF103B9FED0CF20DB334FD93DF9232647AC23F9B9F133622FAAECA5F1
	    boolean transactions = true;
	    String msg = remote.requestLedger(ledger_index,ledger_hash,transactions);
	    System.out.println("requestLedgerTest:"+msg);
	}
	
	//4.7 查询某一交易具体信息
	public void requestTxTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
        String hash = "C57A26E656CF5DF389FCC154BA14F2845420DB88A102F0D86305E4FFE48EDA5D";
	    String msg = remote.requestTx(hash);
	    System.out.println("requestTxTest:"+msg);
	}
	
	//4.8 请求账号信息
	public void requestAccountInfoTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
	    String msg = remote.requestAccountInfo("jL8L5f56zjFfmBBq19nvDTuVRfAQ7n19aJ");
	    System.out.println("requestAccountInfoTest:"+msg);
	}
	
	//4.9 获得账号可接收和发送的货币
	public void requestAccountTumsTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
	    String msg = remote.requestAccountTums(account);
	    System.out.println("requestAccountTumsTest:"+msg);
	}
	
	//4.10 获得账号关系
	public void requestAccountRelationsTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
        String type = "trust";
	    String msg = remote.requestAccountRelations(account,type);
	    System.out.println("requestAccountRelationsTest:"+msg);
	}
	
	//4.11 获得账号挂单
	public void requestAccountOffersTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
	    String msg = remote.requestAccountOffers(account);
	    System.out.println("requestAccountOffersTest:"+msg);
	}
	
	//4.12 获得账号交易列表
	public void requestAccountTxTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
	    String msg = remote.requestAccountTx(account,limit_num);
	    System.out.println("requestAccountTxTest:"+msg);
	}
	
	//4.13 获得市场挂单列表
	public void requestOrderBookTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
        Remote remote = new Remote(conn);
        String getsCurrency = "SWT";
        String getsIssuer = "";
        String paysCurrency = "CNY";
        String paysIssuer = "jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or";
        Amount taker_gets = new Amount();
		taker_gets.setCurrency(getsCurrency);
		taker_gets.setIssuer(getsIssuer);
		Amount taker_pays = new Amount();
		taker_pays.setCurrency(paysCurrency);
		taker_pays.setIssuer(paysIssuer);
        String msg = remote.requestOrderBook(taker_gets,taker_pays,limit_num);
	    System.out.println("requestOrderBookTest:"+msg);
	}
	
	//4.14 支付
	public void buildPaymentTxTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
    	Remote remote = new Remote(conn);
    	//使用签名
    	//Remote remote = new Remote(conn,true);
        
        //address=jLRndNqkyi4DasqG3pjA3pcAcsnkSKoTdU
        //secret=ssCXga6r2x398DdLyN3XkLDiT2ZuS
        
        //msg = remote.requestAccountInfo("jLRndNqkyi4DasqG3pjA3pcAcsnkSKoTdU");//19999940
        //msg = remote.requestAccountInfo("jJHdzzHwrPggaBKBZZWbC6snJneSvzip5C");//48099931
        
        String account = "jLDn91zS5J1sCURGZkBGMj6WNt3dq1dery";
        //SWT支付
        String to = "jJHdzzHwrPggaBKBZZWbC6snJneSvzip5C";
        String value = "0.000005";
        String currency = "SWT";
        String issuer = "";
        //其他货币支付
        /*String value = "1";
        String currency = "CNY";
        String issuer = "jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or";*/
        
        Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setValue(value);
		amount.setIssuer(issuer);
        
        String memo = "给jJHdzzHwrPggaBKBZZWbC6snJneSvzip5C支付0.000005swt.";
        String msg = remote.buildPaymentTx(account,to,amount,memo,secret);
        System.out.println("buildPaymentTx:"+msg);
	}
	
	
	//4.15 设置关系
	public void buildRelationTxTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
    	Remote remote = new Remote(conn);
		
		String type = "trust";
	    String target = "jJHdzzHwrPggaBKBZZWbC6snJneSvzip5C";
	    String value = "0.00005";
	    String currency = "SWT";
	    String issuer = "";
	    Amount limit = new Amount();
		limit.setValue(value);
		limit.setCurrency(currency);
		limit.setIssuer(issuer);
	    String memo = "4.15 设置关系";
	    String msg = remote.buildRelationTx(type,account,target,limit,memo,secret);
	    System.out.println("buildRelationTx:"+msg);
	}
	
	//4.16 设置账号属性
	public void buildAccountSetTxTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
    	Remote remote = new Remote(conn);
		
        String type = "property"; 
        String set_flag = "";
        //可选 
        String memo = "4.16 设置账号属性";
        String msg = remote.buildAccountSetTx(type, account, set_flag,memo,secret);
	    System.out.println("buildAccountSetTx:"+msg);
	}
	
	//4.17 挂单
	public void buildOfferCreateTxTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
    	Remote remote = new Remote(conn);
		
    	String type = "Sell";
    	
        Amount getsAmount = new Amount();
        getsAmount.setValue("1");
        getsAmount.setIssuer("");
        getsAmount.setCurrency("SWT");
        Amount paysAmount = new Amount();
        paysAmount.setValue("0.01");
        paysAmount.setIssuer("jGa9J9TkqtBcUoHe2zqhVFFbgUVED6o9or");
        paysAmount.setCurrency("CNY");
        //可选 
        String memo = "4.17 挂单";
        String msg = remote.buildOfferCreateTx(type, account, getsAmount, paysAmount,memo,secret);
	    System.out.println("buildOfferCreateTx:"+msg);
	}
	
	//4.18 取消挂单
	public void buildOfferCancelTxTest() {
    	Connection conn = ConnectionFactory.getCollection(server);
    	Remote remote = new Remote(conn);
		
        Integer sequence = 19;
        //可选 
        String memo = "4.18 取消挂单";
        String msg = remote.buildOfferCancelTx(account,sequence,memo,secret);
        //可选 
	    System.out.println("buildOfferCancelTx:"+msg);
	}
	
	
    public static void main(String[] args){
    	Test test = new Test();
    	
    	//test.requestServerInfoTest();
    	//test.requestLedgerClosedTest();
    	//test.requestLedgerTest();
    	//test.requestTxTest();
    	
    	test.requestAccountInfoTest();
    	//test.requestAccountTumsTest();
    	//test.requestAccountRelationsTest();
    	//test.requestAccountOffersTest();
    	//test.requestAccountTxTest();
    	
    	//test.requestOrderBookTest();
    	
    	//test.buildPaymentTxTest();
    	//test.buildRelationTxTest();//trust时返回错误，其他正常
    	//test.buildAccountSetTxTest();
    	//test.buildOfferCreateTxTest();
    	//test.buildOfferCancelTxTest();
    }
}