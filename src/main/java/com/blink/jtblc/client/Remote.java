package com.blink.jtblc.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blink.jtblc.client.bean.Account;
import com.blink.jtblc.client.bean.AccountInfo;
import com.blink.jtblc.client.bean.AccountOffers;
import com.blink.jtblc.client.bean.AccountPropertyInfo;
import com.blink.jtblc.client.bean.AccountRelations;
import com.blink.jtblc.client.bean.AccountTums;
import com.blink.jtblc.client.bean.AccountTx;
import com.blink.jtblc.client.bean.Amount;
import com.blink.jtblc.client.bean.BookOffers;
import com.blink.jtblc.client.bean.Ledger;
import com.blink.jtblc.client.bean.LedgerClosed;
import com.blink.jtblc.client.bean.LedgerInfo;
import com.blink.jtblc.client.bean.OfferCancelInfo;
import com.blink.jtblc.client.bean.OfferCreateInfo;
import com.blink.jtblc.client.bean.OrderBook;
import com.blink.jtblc.client.bean.PaymentInfo;
import com.blink.jtblc.client.bean.RelationInfo;
import com.blink.jtblc.client.bean.ServerInfo;
import com.blink.jtblc.config.Config;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.exceptions.RemoteException;
import com.blink.jtblc.utils.CheckUtils;
import com.blink.jtblc.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Remote {
	protected static final Logger logger = LoggerFactory.getLogger(Remote.class);
	ObjectMapper mapper = new ObjectMapper();
	private String server = "";
	// 签名默认为false,false需要传入密钥
	private Boolean localSign = false;
	private Connection conn = null;
	
	public Remote(Connection conn) {
		this.conn = conn;
	}
	
	public Remote(Connection conn, Boolean localSign) {
		this.conn = conn;
		this.localSign = localSign;
	}
	
	/**
	 * 工具方法：组装发送前的信息
	 * 
	 * @param command
	 * @param data
	 * @return
	 */
	public String sendMessage(String command, Map<String, Object> data) {
		Map<String, Object> params = new HashMap();
		params.put("command", command);
		params.putAll(data);
		logger.debug("WebSocket参数： " + JsonUtils.toJsonString(params));
		return conn.submit(params);
	}
	
	// 4.1 创建Remote对象
	/**
	 * 4.2 创建连接 获取服务信息及帐本信息
	 *
	 * @return
	 */
	public LedgerInfo requestLedgerInfo() {
		Map<String, Object> params = new HashMap();
		params.put("streams", new String[] { "ledger", "server" });
		String res = sendMessage("subscribe", params);
		return JsonUtils.toEntity(res, LedgerInfo.class);
	}
	
	/**
	 * 4.4 请求底层服务器信息
	 * 
	 * @return
	 */
	public ServerInfo requestServerInfo() {
		ServerInfo ser = new ServerInfo();
		Map params = new HashMap();
		String msg = this.sendMessage("server_info", params);
		return JsonUtils.toEntity(msg, ServerInfo.class);
	}
	
	/**
	 * 4.5 获取最新账本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public LedgerClosed requestLedgerClosed() throws Exception {
		Map params = new HashMap();
		String msg = this.sendMessage("ledger_closed", params);
		return JsonUtils.toEntity(msg, LedgerClosed.class);
	}
	
	/**
	 * 4.6 获取某一账本具体信息
	 * 注：整体参数是Object类型，当参数都不填时，默认返回最新账本信息
	 *
	 * @param ledger_index 井通区块高度
	 * @param ledger_hash 井通区块hash(与上面ledger_index二选其一)
	 * @param transactions 是否返回账本上的交易记录hash，默认false
	 * @return
	 */
	public Ledger requestLedger(String ledgerIndexOrHash, boolean transactions) {
		Map params = new HashMap();
		// 校验,并将参数写入message对象
		Map message = new HashMap();
		if (StringUtils.isEmpty(ledgerIndexOrHash)) {
			throw new RemoteException("ledger_index and ledger_hash is null");
		}
		if (StringUtils.isBlank(ledgerIndexOrHash)) {
			throw new RemoteException("invalid ledger_index or ledger_hash is null");
		}
		if (CheckUtils.isNumeric(ledgerIndexOrHash)) {
			params.put("ledger_index", Integer.parseInt(ledgerIndexOrHash));
		} else {
			params.put("ledger_hash", ledgerIndexOrHash);
		}
		// params.put("message", message);
		params.put("transactions", transactions);
		String msg = this.sendMessage("ledger", params);
		return JsonUtils.toEntity(msg, Ledger.class);
	}
	
	/**
	 * 4.7 查询某一交易具体信息
	 * 
	 * @param hash 交易hash
	 * @return
	 */
	public Account requestTx(String hash) {
		Map params = new HashMap();
		// 校验,并将参数写入message对象
		if (!CheckUtils.isValidHash(hash)) {
			throw new RemoteException("invalid tx hash");
		}
		params.put("transaction", hash);
		String msg = this.sendMessage("tx", params);
		Map map = JsonUtils.toObject(msg, Map.class);
		Account account = new Account();
		if (map.get("status").equals("success")) {
			account = JsonUtils.toEntity(msg, Account.class);
		} else if (map.get("status").equals("error")) {
			msg = "接口调用出错";
			throw new RuntimeException(msg);
		} else {
			throw new RuntimeException("unknown error");
		}
		return account;
	}
	
	/**
	 * 4.8 请求账号信息
	 *
	 * @param account 井通钱包地址
	 * @return
	 */
	public AccountInfo requestAccountInfo(String account) {
		String msg = requestAccount("account_info", account, "");
		AccountInfo accountInfo = JsonUtils.toEntity(msg, AccountInfo.class);
		return accountInfo;
	}
	
	/**
	 * 4.9 获得账号可接收和发送的货币
	 *
	 * @param account 井通钱包地址
	 * @return
	 */
	public AccountTums requestAccountTums(String account) {
		String msg = requestAccount("account_currencies", account, "");
		AccountTums accountTums = JsonUtils.toEntity(msg, AccountTums.class);
		return accountTums;
	}
	
	/**
	 * 4.10 获得账号关系
	 *
	 * @param account 井通钱包地址
	 * @param type 关系类型，固定的三个值：trust、authorize、freeze
	 * @return
	 */
	public AccountRelations requestAccountRelations(String account, String type) {
		String command = "";
		if (!CheckUtils.isValidType("relation", type)) {
			throw new RemoteException("invalid relation type");
		}
		switch (type) {
			case "trust":
				command = "account_lines";
				break;
			case "authorize":
			case "freeze":
				command = "account_relation";
				break;
		}
		String msg = requestAccount(command, account, type);
		AccountRelations accountRelations = JsonUtils.toEntity(msg, AccountRelations.class);
		return accountRelations;
	}
	
	/**
	 * 4.11 获得账号挂单
	 *
	 * @param account 井通钱包地址
	 * @return
	 */
	public AccountOffers requestAccountOffers(String account) {
		String msg = requestAccount("account_offers", account, "");
		AccountOffers accountOffers = JsonUtils.toEntity(msg, AccountOffers.class);
		return accountOffers;
	}
	
	/**
	 * 获取账号信息
	 * inner function
	 * 
	 * @param command
	 * @param account
	 * @param type
	 * @return
	 */
	private String requestAccount(String command, String account, String type) {
		Map params = new HashMap();
		// 校验,并将参数写入message对象
		if (StringUtils.isNotEmpty(type)) {
			Integer relation_type = null;
			switch (type) {
				case "trustline":
					relation_type = 0;
					break;
				case "authorize":
					relation_type = 1;
					break;
				case "freeze":
					relation_type = 3;
					break;
			}
			params.put("relation_type", relation_type);
		}
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid account");
		} else {
			params.put("account", account);
		}
		params.put("ledger_index", "validated"); // 4.9 新增参数ledger_index值
		// params.put("message", message);
		params.put("account", account);
		params.put("command", command);
		String msg = this.submit(params);
		return msg;
	}
	
	/**
	 * 获得账号交易列表
	 *
	 * @param account 井通钱包地址
	 * @param limit 限定返回多少条记录，默认200
	 * @return
	 */
	public AccountTx requestAccountTx(String account, Integer limit) {
		if (limit == null || limit == 0) {
			limit = 200;
		}
		Map params = new HashMap();
		// 校验,并将参数写入message对象
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("account parameter is invalid");
		} else {
			params.put("account", account);
		}
		if (limit != null) {
			params.put("limit", limit);
		}
		params.put("account", account);
		params.put("command", "account_tx");
		// 新增参数start
		params.put("ledger_index_min", 0);
		params.put("ledger_index_max", -1);
		// 新增参数end
		String msg = this.submit(params);
		AccountTx accountTx = new AccountTx();
		Map map = JsonUtils.toObject(msg, Map.class);
		if (map.get("status").equals("success")) {
			Map result = (Map) map.get("result");
			accountTx.setAccount(result.get("account").toString());
			accountTx.setLedgerIndexMax(result.get("ledger_index_max").toString());
			accountTx.setLedgerIndexMin(result.get("ledger_index_min").toString());
			List<Map> list = (List<Map>) result.get("transactions");
			List<com.blink.jtblc.client.bean.Transaction> txs = new ArrayList<>();
			for (Map txMap : list) {
				com.blink.jtblc.client.bean.Transaction tx = new com.blink.jtblc.client.bean.Transaction();
				try {
					tx = processTx(txMap,account);
				} catch (Exception e) {
					throw new RemoteException("exchange error");
				}
				txs.add(tx);
			}
			accountTx.setTransactions(txs);
		}
			
		return accountTx;
	}
	
	public com.blink.jtblc.client.bean.Transaction processTx(Map txMap,String account) throws Exception{
		com.blink.jtblc.client.bean.Transaction transtacion = new com.blink.jtblc.client.bean.Transaction();
		Map tx = new HashMap();
		Map meta = new HashMap();
		if(txMap.get("tx")!=null) {
			tx = (Map)txMap.get("tx");
		}else if(txMap.get("transaction")!=null) {
			tx = (Map)txMap.get("transaction");
		}else {
			tx = txMap;
		}
		meta = (Map)txMap.get("meta");
		String hex = "0x386D4380";
		Integer x = Integer.parseInt(hex.substring(2),16);
		if(tx.get("date")!=null) {
			transtacion.setDate(Integer.valueOf(tx.get("date").toString())+x);
		}else{
			transtacion.setDate(Integer.valueOf(tx.get("Timestamp").toString())+x);
		}
		if(tx.get("hash")!=null) {
			transtacion.setHash(tx.get("hash").toString());
		}
		transtacion.setType(txnType(tx,account));
		transtacion.setFee((Integer.valueOf((tx.get("Fee").toString()))/1000000.0)+"");
		
		if(meta.get("TransactionResult")!=null) {
			transtacion.setResult(meta.get("TransactionResult").toString());
		}else {
			transtacion.setResult("failed");
		}
		transtacion.setMemos(new HashMap());
		
		switch(transtacion.getType()) {
        case "sent":
        	transtacion.setCounterparty(tx.get("Destination").toString());
        	transtacion.setAmount(parseAmount(tx.get("Amount")));
            break;
        case "received":
        	transtacion.setCounterparty(tx.get("Account").toString());
        	transtacion.setAmount(parseAmount(tx.get("Amount")));
            break;
        case "trusted":
        	transtacion.setCounterparty(tx.get("Account").toString());
            transtacion.setAmount(this.reverseAmount((Map)tx.get("LimitAmount"),tx.get("Account").toString()));
            break;
        case "trusting":
        	transtacion.setCounterparty(((Map)tx.get("LimitAmount")).get("issuer").toString());
        	transtacion.setAmount(tx.get("LimitAmount").toString());
            break;
        case "convert":
        	transtacion.setSpent(parseAmount(tx.get("SendMax")));
            transtacion.setAmount(parseAmount(tx.get("Account")));
            break;
        case "offernew":
        	//Transaction.flags.OfferCreate.Sell 是一个常量
        	transtacion.setOffertype((tx.get("Flags")!=null?"sell" : "buy"));
        	transtacion.setGets(parseAmount(tx.get("TakerGets")));
        	transtacion.setPays(parseAmount(tx.get("TakerPays")));
        	transtacion.setSeq(tx.get("Sequence").toString());
            break;
        case "offercancel":
        	transtacion.setOfferseq(tx.get("Sequence").toString());
            break;
        case "relationset":
        	transtacion.setCounterparty(account.equals(tx.get("Target").toString())?tx.get("Account").toString():tx.get("Target").toString());
        	transtacion.setTransactionType(tx.get("RelationType").toString().equals("3")? "freeze":"authorize");
        	transtacion.setIsactive(account.equals(tx.get("Target").toString())? false : true);
            transtacion.setAmount(parseAmount(tx.get("LimitAmount")));
            break;
        case "relationdel":
        	transtacion.setCounterparty(account.equals(tx.get("Target").toString())?tx.get("Account").toString():tx.get("Target").toString());
        	transtacion.setTransactionType(tx.get("RelationType").toString().equals("3")? "unfreeze":"unknown");
        	transtacion.setIsactive(account.equals(tx.get("Target").toString())? false : true);
            transtacion.setAmount(parseAmount(tx.get("LimitAmount")));
            break;
        case "configcontract":
        	transtacion.setParams(formatArgs((List<Map>)tx.get("Args")));
            if(tx.get("Method").equals("0")){
            	transtacion.setMethod("deploy");
            	transtacion.setPayload(tx.get("Payload").toString());
            
            }else  if(tx.get("Method").equals("1")){
                transtacion.setMethod("call");
            	transtacion.setPayload(tx.get("Destination").toString());
            }
            break;
        default :
            // TODO parse other type
            break;
		}  
        if(tx.get("Memos")!=null&&tx.get("Memos").getClass().isArray()&&((List)tx.get("Memos")).size()>0) {
        	List menos=((List)tx.get("Memos"));
        	for(int i=0;i<menos.size();i++) {
        		Map<String,String> meno=(Map)((Map)menos.get(i)).get("Memo");
	        		for (Map.Entry<String,String> entry : meno.entrySet()) { 
        		     String str  = URLEncoder.encode(new String(entry.getValue().getBytes("UTF-8")), "UTF-8"); 
        			  entry.setValue(str);
        		}
        		transtacion.setMemos(meno);
        	}
        }
        transtacion.setEffects(new ArrayList());
        if (meta==null || !meta.get("TransactionResult").toString().equals("tesSUCCESS")) {
            return transtacion;
        }
        List<Map> maps = (List<Map>)meta.get("AffectedNodes");
        for(int i=0;i<maps.size();i++) {
        	Map map = maps.get(i);
        	Map node = processAffectNode(map);
        	Map effect =new HashMap();
        	if (node.get("entryType").equals("Offer")) {
        		 Map fieldSet = (Map)node.get("fields");
        		 boolean sell = ((Map)node.get("fields")).get("Flags")!=null?true:false;
        		 if (((Map)node.get("fields")).get("Account").equals(account)) {
	                if (node.get("diffType").equals("ModifiedNode") || 
	                		(node.get("diffType").equals("DeletedNode")&& (((Map)node.get("fieldsPrev")).get("TakerGets")!=null && 
	                				!isAmountZero(parseAmount(((Map)node.get("fieldsFinal")).get("TakerGets")))))) {
	                		
	                 effect.put("effect", "offer_partially_funded");
               		 Map _map =new HashMap();
               		_map.put("account", tx.get("Account"));
               		_map.put("seq", tx.get("Sequence"));
               		_map.put("hash",tx.get("hash"));
            	       try {
            	    	   effect.put("counterparty", mapper.writeValueAsString(map));
            			} catch (JsonProcessingException e) {
            				e.printStackTrace();
            			}
               		 	if(node.get("diffType").equals("DeletedNode")) {
	                        if(isAmountZero(parseAmount(((Map)node.get("fields")).get("TakerGets")))){
        						effect.put("remaining", false);
        					}else {
        						effect.put("remaining", true);
        					}
               		 	}else {
               		 		effect.put("cancelled", true);
               		 	}
               		 	effect.put("gets", parseAmount(fieldSet.get("TakerGets")));
               		 	effect.put("pays", parseAmount(fieldSet.get("TakerPays")));
               		 	if(StringUtils.isNotBlank(parseAmount((((Map)node.get("fieldsPrev"))).get("TakerGets")))) {
               		 	 effect.put("paid", AmountSubtract(
                      		      parseAmount((((Map)node.get("fieldsPrev"))).get("TakerGets")),
                      		      parseAmount((((Map)node.get("fields"))).get("TakerGets"))));
               		 	}
               		 	
               		 if(StringUtils.isNotBlank(parseAmount((((Map)node.get("fieldsPrev"))).get("TakerGets")))) {
               			effect.put("got", AmountSubtract(
                   		      parseAmount((((Map)node.get("fieldsPrev"))).get("TakerPays")),
                   		      parseAmount((((Map)node.get("fields"))).get("TakerPays"))));
               		 	}
               		   
        			    
        			    
        			    effect.put("type", sell ? "sold" : "bought");
        			 
	                }else {
	                     effect.put("effect", node.get("diffType").equals("CreatedNode") ? "offer_created" : ((Map)node.get("fieldsPrev")).get("TakerPays")!=null ? "offer_funded" : "offer_cancelled");
	                	 
	                	 if (effect.get("effect").equals("offer_funded")) {
	                		 fieldSet = (Map)node.get("fieldsPrev");
	                		 Map _map =new HashMap();
	                		 _map.put("account",tx.get("Account"));
	                		 _map.put("seq",tx.get("Sequence"));
	                		 _map.put("hash",tx.get("hash"));
	                	       try {
	                	    	   effect.put("counterparty", mapper.writeValueAsString(_map));
	                			} catch (JsonProcessingException e) {
	                				e.printStackTrace();
	                			}
	                	       effect.put("paid", AmountSubtract(
	                      		      parseAmount((((Map)node.get("fieldsPrev"))).get("TakerGets")),
	                      		      parseAmount((((Map)node.get("fields"))).get("TakerGets"))));
	                	       
	                	       effect.put("got", AmountSubtract(
	                      		      parseAmount((((Map)node.get("fieldsPrev"))).get("TakerPays")),
	                      		      parseAmount((((Map)node.get("fields"))).get("TakerPays"))));
	                	       
	            			 effect.put("type", sell ? "sold" : "bought");
	                     }
	                     // 3. offer_created
	                     if (effect.get("effect").equals("offer_created")) {
	                    	 effect.put("gets", parseAmount(fieldSet.get("TakerGets")));
	                    	 effect.put("pays", parseAmount(fieldSet.get("TakerPays")));
	                    	 effect.put("type", sell ? "sell" : "buy");
	                     }
	                     // 4. offer_cancelled
	                     if (effect.get("effect").equals("offer_cancelled")) {
	                    	
	                    	 effect.put("hash", ((Map)node.get("fields")).get("PreviousTxnID"));
	                         // collect data for cancel transaction type
	                         if (transtacion.getType().equals("offercancel")) {
		                    	 transtacion.setGets(parseAmount(fieldSet.get("TakerGets")));
		                    	 transtacion.setPays(parseAmount(fieldSet.get("TakerPays")));
	                         }
                        	 effect.put("gets", parseAmount(fieldSet.get("TakerGets")));
	                    	 effect.put("pays", parseAmount(fieldSet.get("TakerPays")));
	                         effect.put("type", sell ? "sell" : "buy");
	                     }
	                	 
	                }
	                effect.put("seq", ((Map)node.get("fields")).get("Sequence"));
        		 }else if (tx.get("Account").equals(account) && node.get("fieldsPrev")!=null) {
        			 effect.put("effect", "offer_bought");
        			 Map _map =new HashMap();
        			 _map.put("account",((Map)node.get("fields")).get("Account"));
        			 _map.put("seq", ((Map)node.get("fields")).get("Sequence"));
            		 if(node.get("PreviousTxnID")!=null) {
            			 _map.put("hash",node.get("PreviousTxnID"));
            		 }else {
            			 _map.put("hash",((Map)node.get("fields")).get("PreviousTxnID"));
            		 }
            	       try {
            	    	   effect.put("counterparty", mapper.writeValueAsString(_map));
            			} catch (JsonProcessingException e) {
            				e.printStackTrace();
            			}
        			 effect.put("type", sell ? "bought" : "sold");
        			 effect.put("paid", AmountSubtract(
                 		      parseAmount((((Map)node.get("fieldsPrev"))).get("TakerPays")),
                 		      parseAmount((((Map)node.get("fields"))).get("TakerPays"))));
        			 effect.put("got", AmountSubtract(
                		     parseAmount((((Map)node.get("fieldsPrev"))).get("TakerGets")),
                		      parseAmount((((Map)node.get("fields"))).get("TakerGets"))));
                 }
                 // add price
        		if ((effect.get("gets")!=null && effect.get("pays")!=null) || (effect.get("got")!=null && effect.get("paid")!=null)) {
        			Boolean created=effect.get("effect").toString().equals("offer_created")&&effect.get("type").toString().equals("buy");
                    Boolean funded=effect.get("effect").toString().equals("offer_funded")&&effect.get("type").toString().equals("bought");
                    Boolean cancelled=effect.get("effect").toString().equals("offer_cancelled")&&effect.get("type").toString().equals("buy");
                    Boolean bought=effect.get("effect").toString().equals("offer_bought")&&effect.get("type").toString().equals("bought");
                    Boolean partially_funded=effect.get("effect").toString().equals("offer_partially_funded")&&effect.get("type").toString().equals("bought");
                    effect.put("price", getPrice(effect, (created || funded || cancelled || bought ||  partially_funded ))) ;
                    
                    
                 }
        	}
        	if(transtacion.getType().equals("offereffect") && node.get("entryType").equals("AccountRoot")){
                if(((Map)node.get("fields")).get("RegularKey")!=null&&((Map)node.get("fields")).get("RegularKey").equals(account)){
                	effect.put("effect", "set_regular_key");
                	effect.put("type", "null");
                	effect.put("account", ((Map)node.get("fields")).get("Account"));
                	effect.put("regularkey", account);
                }
            }
            // add effect
            if (effect!=null&&!effect.isEmpty()) {
                if (node.get("diffType").equals("DeletedNode")&& effect.get("effect").equals("offer_bought")) {
                	effect.put("deleted", true);
                }
                transtacion.getEffects().add(effect);
            }
        }
		

		return transtacion;
	}
	public Boolean isAmountZero(String amount) {
		if (amount=="") return false;
	       Map map = new HashMap();
		try {
			map = mapper.readValue(amount, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}


	    return Integer.valueOf((map.get("value").toString())) < 1e-12;
	}

	public String getPrice(Map effect ,boolean funded) throws Exception{
		try {
			Map g = new HashMap();
			Map p = new HashMap();
			if(effect.get("got")!=null&&effect.get("got")!="") {
				g=mapper.readValue(effect.get("got").toString(),Map.class);
			}else if(effect.get("pays")!=null&&effect.get("pays")!="") {
				g=mapper.readValue(effect.get("pays").toString(),Map.class);
			}else {
				return "";
			}
			
			if(effect.get("paid")!=null&&effect.get("paid")!="") {
				g=mapper.readValue(effect.get("paid").toString(),Map.class);
			}else if(effect.get("gets")!=null&&effect.get("gets")!="") {
				g=mapper.readValue(effect.get("gets").toString(),Map.class);
			}else {
				return "";
			}
		    if(!funded){
		        return AmountRatio(g, p);
		    } else {
		        return AmountRatio(p, g);
		    }
		}catch(Exception e) {
			throw new Exception("get Price error");
		}
		
	}
	public String AmountRatio(Map amount1,Map amount2 ) {
		BigDecimal bi1 = new BigDecimal(amount1.get("value").toString());
    	BigDecimal bi2 = new BigDecimal(amount2.get("value").toString());
    	BigDecimal bi3 = bi1.divide(bi2);
    	return bi3+"";
	}

	public Map AmountSubtract(String amount1,String amount2) {
		if(StringUtils.isNotBlank(amount1)&&StringUtils.isNotBlank(amount2)) {
			try {
				return AmountAdd(mapper.readValue(amount1, Map.class), AmountNegate(mapper.readValue(amount2, Map.class)));
			} catch (Exception e) {
				throw new RemoteException("to map error");
			}
		}
		return null;
	}
	public Map AmountNegate(Map amount) {
		 if (amount==null) return amount;
		 Map map = new HashMap();
		 map.put("value", (new BigInteger(amount.get("value").toString())).negate());
	     map.put("currency",amount.get("currency"));
	     map.put("issuer", amount.get("issuer"));
	     return map;
	}
	public Map AmountAdd(Map amount1,Map amount2) {
		 if (amount1==null) return amount2;
		    if (amount2==null) return amount1;
		    if (amount1!=null && amount2!=null) {
		    	Map map = new HashMap();
				 map.put("value", (new BigInteger(amount1.get("value").toString())).and(new BigInteger(amount2.get("value").toString())));
			     map.put("currency",amount1.get("currency"));
			     map.put("issuer", amount1.get("issuer"));
			     return map;
		    }
		    return null;
	}
	public Map processAffectNode(Map map) {
		Map result = new HashMap();
		String[] arrays =new String[]{"CreatedNode", "ModifiedNode", "DeletedNode"};
		 for(int i=0;i<arrays.length;i++) {
			 if(map.get(arrays[i])!=null) {
				 result.put("diffType", arrays[i]);
			 }
		 }
	    if(result.get("diffType")==null) {
	    	return null;
	    }
	    map = (Map)map.get(result.get("diffType"));
	    result.put("entryType",map.get("LedgerEntryType"));
	    result.put("ledgerIndex",map.get("LedgerIndex"));
	    Map _map =new HashMap();
	    if(map.get("PreviousFields")!=null) {
	    	result.put("fieldsPrev", map.get("PreviousFields"));
	    	_map.putAll((Map)map.get("PreviousFields"));
	    }
	    if(map.get("NewFields")!=null){
	    	 result.put("fieldsNew",map.get("NewFields"));
	    	 _map.putAll((Map)map.get("NewFields"));
	    }
	    if(map.get("FinalFields")!=null){
	    	 result.put("fieldsFinal",map.get("FinalFields"));
	    	 _map.putAll((Map)map.get("FinalFields"));
	    }
	    if(map.get("PreviousTxnID")!=null){
	    	 result.put("PreviousTxnID",map.get("PreviousTxnID"));
	    }
	    result.put("fields", _map);
	    return result;
	}
	public List formatArgs(List<Map> args) {
		 List list = new ArrayList();
		    if(args!=null)
		        for(int i = 0; i < args.size(); i++){
		            list.add(hexToString(((Map)args.get(i).get("Arg")).get("Parameter").toString()));
		     }
		 return list;
	}
	
	public String hexToString(String str) {
		 List<String> list = new ArrayList<String>();
		 int i=0;
		    if (str.length() % 2==0) {
		        list.add(unicode2String(String.valueOf(Integer.parseInt(str.substring(0, 1), 16))));
		        i = 1;
		    }

		    for (; i<str.length(); i+=2) {
		        list.add(unicode2String(String.valueOf(Integer.parseInt(str.substring(i, i+2), 16))));
		    }
		    return String.join("",list);
	}
	public static String unicode2String(String unicode) {
		 
	    StringBuffer string = new StringBuffer();
	 
	    String[] hex = unicode.split("\\\\u");
	 
	    for (int i = 1; i < hex.length; i++) {
	 
	        // 转换出每一个代码点
	        int data = Integer.parseInt(hex[i], 16);
	 
	        // 追加成string
	        string.append((char) data);
	    }
	 
	    return string.toString();
	}
	

	public String reverseAmount(Map limitAmount,String account) {
		Map map = new HashMap();
		map.put("value", limitAmount.get("value"));
    	map.put("currency",limitAmount.get("currency"));
    	map.put("issuer", account);
        try {
			return mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return "";
	}
	public String txnType(Map tx,String account) {
		if((tx.get("Account")!=null&&tx.get("Account").toString().equals(account))||(tx.get("Target")!=null&&tx.get("Target").toString().equals(account))||
				(tx.get("Destination")!=null&&tx.get("Destination").toString().equals(account))||(tx.get("LimitAmount")!=null&&((Map)tx.get("LimitAmount")).get("issuer").toString().equals(account))) {
			switch(tx.get("TransactionType").toString()) {
            case "Payment":
                return tx.get("Account")!=null&&tx.get("Account").toString().equals(account) ?
                		tx.get("Destination")!=null&&tx.get("Destination").toString().equals(account) ? "convert" : "sent" : "received";
            case "OfferCreate":
                return "offernew";
            case "OfferCancel":
                return "offercancel";
            case "TrustSet":
                return tx.get("Account")!=null&&tx.get("Account").toString().equals(account) ? "trusting" : "trusted";
            case "RelationDel":
            case "AccountSet":
            case "SetRegularKey":
            case "RelationSet":
            case "SignSet":
            case "Operation":
            case "ConfigContract":
                // TODO to sub-class tx type
                return tx.get("TransactionType").toString().toLowerCase();
            default :
                // TODO CHECK
                return "unknown";
            }
        }else {
        	return "offereffect";
        }
	}
	
	public String parseAmount(Object tx) {
		try {
				if(tx instanceof String) {
					if(isNum(tx.toString())) {
						BigDecimal bi1 = new BigDecimal(tx.toString());
				    	BigDecimal bi2 = new BigDecimal("1000000");
				    	BigDecimal bi3 = bi1.divide(bi2); 
				    	Map map = new HashMap();
				    	map.put("value", bi3);
				    	map.put("currency", "SWT");
				    	map.put("issuer", "");
						return mapper.writeValueAsString(map);
					}
				}else if(tx instanceof Map && isValidAmount((Map)tx)){
					return mapper.writeValueAsString(tx);
				}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
		
	
	public boolean isNum(String amount) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
		Matcher matcher = pattern.matcher(amount);  
		return matcher.matches();  
	}
	
	public boolean isValidAmount(Map amount) {
		if((amount.get("value")!=null&&(amount.get("value").toString()).equals("0"))||!isNum(amount.get("value").toString())) {
			return false;
		}
		if(amount.get("currency")==null||!isValidCurrency(amount)) {
			return false;
		}
		if (amount.get("currency").toString().equals("SWT")&& amount.get("issuer")!=null) {
		    return false;
		}
		 if (amount.get("currency").toString().equals("SWT")) {
			 //todo !baselib.isValidAddress(amount.issuer)
			 return false;
		 }
		return true;
	}
	
	public boolean isValidCurrency(Map amount) {
		if (amount.get("currency")==null || !(amount.get("currency")  instanceof String)
		            || amount.get("currency").toString().equals("")) {
		    return false;
		}
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9]{3,6}|[A-F0-9]{40})$");  
		Matcher matcher = pattern.matcher(amount.get("currency").toString());  
		return matcher.matches(); 
	}
	
	
	/**
	 * 获得市场挂单列表
	 *
	 * @param getsCurrency 对家想要获得的货币信息：货币种类，三到六个字母或20字节的自定义货币
	 * @param getsIssuer 对家想要获得的货币信息：货币发行方
	 * @param paysCurrency 对家想要支付的货币信息：货币种类，三到六个字母或20字节的自定义货币
	 * @param paysIssuer 对家想要支付的货币信息：货币发行方
	 * @param limit 限定返回多少条记录
	 * @return
	 */
	public BookOffers requestOrderBook(Amount taker_gets, Amount taker_pays, Integer limit) {
		Map params = new HashMap();
		/*
		 * Map gets = new HashMap();
		 * gets.put("currency", getCurrency);
		 * gets.put("issuer", getsIssuer);
		 * Map pays = new HashMap();
		 * pays.put("currency", paysCurrency);
		 * pays.put("issuer", paysIssuer);
		 * params.put("taker_gets", gets);
		 * params.put("taker_pays", pays);
		 * params.put("command", "book_offers");
		 */
		if (!CheckUtils.isValidAmount(taker_gets)) {
			throw new RemoteException("invalid taker gets amount");
		}
		if (!CheckUtils.isValidAmount(taker_pays)) {
			throw new RemoteException("invalid taker pays amount");
		}
		if (limit != null) {
			params.put("limit", limit);
		}
		params.put("taker_gets", taker_gets);
		params.put("taker_pays", taker_pays);
		params.put("command", "book_offers");
		params.put("taker", Config.ACCOUNT_ONE);
		String msg = this.submit(params);
		return JsonUtils.toEntity(msg, BookOffers.class);
	}
	
	/**
	 * 4.13 获得市场挂单列表
	 * 
	 * @param taker_gets 对家想要获得的货币信息
	 * @param taker_pays 对家想要支付的货币信息
	 * @param limit
	 * @return
	 */
	public OrderBook requestOrderBook(Map taker_gets, Map taker_pays, Integer limit) {
		Map params = new HashMap();
		params.put("taker_gets", taker_gets);
		params.put("taker_pays", taker_pays);
		params.put("command", "book_offers");
		params.put("taker", "jjjjjjjjjjjjjjjjjjjjBZbvri");
		params.put("limit", limit);
		String msg = this.submit(params);
		return JsonUtils.toEntity(msg, OrderBook.class);
	}
	
	/**
	 * 获取交易对象
	 *
	 * @param account 发起账号
	 * @param to 目标账号
	 * @param amount 支付金额对象Amount
	 * @param value
	 * @param currency
	 * @param issuer
	 * @param secret 密钥
	 * @param memo 备注
	 * @return
	 */
	public PaymentInfo buildPaymentTx(String account, String to, Amount amount, String memo, String secret) {
		Transaction tx = new Transaction();
		tx.setAccount(account);
		tx.setTo(to);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAddress(to)) {
			throw new RemoteException("invalid destination address");
		}
		tx_json.put("TransactionType", "Payment");
		tx_json.put("Account", account);
		/*
		 * Map amount = new HashMap();
		 * amount.put("value", value);
		 * amount.put("currency", currency);
		 * amount.put("issuer", issuer);
		 */
		/*
		 * Amount amount = new Amount();
		 * amount.setCurrency(currency);
		 * amount.setValue(value);
		 * amount.setIssuer(issuer);
		 */
		tx_json.put("Amount", toAmount(amount));
		tx_json.put("Destination", to);
		if (StringUtils.isNotEmpty(memo)) {
			if (memo.length() > 2048) {
				throw new RemoteException("memo is too long");
			} else {
				// tx_json.put("MemoData", __stringToHex(utf8.encode(memo));
			}
		}
		tx.setSecret(secret);
		tx.setCommand("submit");
		tx.addMemo(memo);
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.localSign, params);
		PaymentInfo bean = JsonUtils.toEntity(msg, PaymentInfo.class);
		return bean;
	}
	
	/**
	 * 设置关系
	 *
	 * @param type 关系种类
	 * @param account 设置关系的源账号
	 * @param target 目标账号
	 * @param limit 关系金额 对象Amount
	 * @param secret
	 * @param memo
	 * @return
	 */
	public RelationInfo buildRelationTx(String type_value, String account, String target, Amount limit, String memo, String secret) {
		Transaction tx = new Transaction();
		// 将参数写入tx对象,方便读取校验
		tx.setAccount(account);
		tx.setTo(target);
		tx.setLimit(limit);
		tx.setRelation_type(type_value);
		tx.setCommand("submit");
		tx.setSecret(secret);
		tx.setMemo(memo);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidType("relation", type_value)) {
			throw new RemoteException("invalid relation type");
		}
		if (StringUtils.isNotEmpty(memo)) {
			if (memo.length() > 2048) {
				throw new RemoteException("memo is too long");
			} else {
				// tx_json.put("MemoData", __stringToHex(utf8.encode(memo));
			}
		}
		tx.setSecret(secret);
		switch (type_value) {
			case "trust":
				return buildTrustSet(tx);
			case "authorize":
			case "freeze":
			case "unfreeze":
				return buildRelationSet(tx);
		}
		throw new RemoteException("build relation set should not go here");
	}
	
	/**
	 * 根据Transaction对象获取trust关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private RelationInfo buildTrustSet(Transaction tx) {
		String src = tx.getAccount();
		Amount limit = tx.getLimit();
		String quality_out = "";
		String quality_in = "";
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAmount(limit)) {
			throw new RemoteException("invalid amount");
		}
		tx_json.put("TransactionType", "TrustSet");
		tx_json.put("Account", src);
		tx_json.put("LimitAmount", toAmount(limit));
		if (StringUtils.isNotEmpty(quality_in)) {
			tx_json.put("QualityIn", quality_in);
		}
		if (StringUtils.isNotEmpty(quality_out)) {
			tx_json.put("QualityOut", quality_out);
		}
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.localSign, params);
		RelationInfo bean = JsonUtils.toEntity(msg, RelationInfo.class);
		return bean;
	}
	
	/**
	 * 根据Transaction对象获取非trust关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private RelationInfo buildRelationSet(Transaction tx) {
		String src = tx.getAccount();
		Amount limit = tx.getLimit();
		String des = tx.getTo();
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAddress(des)) {
			throw new RemoteException("invalid destination address");
		}
		if (!CheckUtils.isValidAmount(limit)) {
			throw new RemoteException("invalid amount");
		}
		// 关系类型：0信任；1授权；3冻结/解冻；
		String type = tx.getRelation_type();
		tx_json.put("TransactionType", type.equals("unfreeze") ? "RelationDel" : "RelationSet");
		tx_json.put("RelationType", type.equals("authorize") ? "1" : "3");
		tx_json.put("Account", src);
		tx_json.put("Target", des);
		tx_json.put("LimitAmount", toAmount(limit));
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.localSign, params);
		RelationInfo bean = JsonUtils.toEntity(msg, RelationInfo.class);
		return bean;
	}
	
	/**
	 * 设置账号属性
	 * 
	 * @param type_value 属性种类
	 * @param account 设置属性的源账号
	 * @param set_flag 属性编号
	 * @param secret
	 * @param memo
	 * @return
	 */
	public AccountPropertyInfo buildAccountSetTx(String type_value, String account, String set_flag, String memo, String secret) {
		Transaction tx = new Transaction();
		tx.setAccount(account);
		tx.setProperty_type(type_value);
		tx.setCommand("submit");
		tx.setSecret(secret);
		tx.setMemo(memo);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidType("accountSet", type_value)) {
			throw new RemoteException("invalid account set type");
		}
		switch (type_value) {
			case "property":
				return buildAccountSet(tx);
			case "delegate":
				return buildDelegateKeySet(tx);
			case "signer":
				return buildSignerSet(tx);
		}
		throw new RemoteException("build account set should not go here");
	}
	
	/**
	 * 根据Transaction对象获取property关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private AccountPropertyInfo buildAccountSet(Transaction tx) {
		Map params = new HashMap();
		String src = tx.getAccount();
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		tx_json.put("Account", src);
		tx_json.put("TransactionType", "AccountSet");
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.localSign, params);
		AccountPropertyInfo bean = JsonUtils.toEntity(msg, AccountPropertyInfo.class);
		return bean;
	}
	
	/**
	 * 根据Transaction对象获取delegate关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private AccountPropertyInfo buildDelegateKeySet(Transaction tx) {
		Map params = new HashMap();
		String src = tx.getAccount();
		String delegate_key = "";
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAddress(delegate_key)) {
			throw new RemoteException("invalid regular key address");
		}
		tx_json.put("TransactionType", "SetRegularKey");
		tx_json.put("Account", src);
		tx_json.put("RegularKey", delegate_key);
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.localSign, params);
		AccountPropertyInfo bean = JsonUtils.toEntity(msg, AccountPropertyInfo.class);
		return bean;
	}
	
	/**
	 * 根据Transaction对象获取signer关系参数
	 * 
	 * @param tx Transaction
	 * @return
	 */
	private AccountPropertyInfo buildSignerSet(Transaction tx) {
		Map params = new HashMap();
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.localSign, params);
		AccountPropertyInfo bean = JsonUtils.toEntity(msg, AccountPropertyInfo.class);
		return bean;
	}
	
	/**
	 * 挂单
	 *
	 * @param type 挂单类型，固定的两个值：Buy、Sell
	 * @param account 挂单方账号
	 * @param getsAmount 对方得到的，即挂单方支付的
	 * @param paysAmount 对方支付的，即挂单方获得的
	 * @param secret
	 * @param memo
	 * @return
	 */
	public OfferCreateInfo buildOfferCreateTx(String type, String account, Amount getsAmount, Amount paysAmount, String memo,
	        String secret) {
		Transaction tx = new Transaction();
		tx.setCommand("submit");
		tx.setSecret(secret);
		tx.setMemo(memo);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidType("offer", type)) {
			throw new RemoteException("invalid offer type");
		}
		if (!CheckUtils.isValidAmountValue(getsAmount.getValue())) {
			throw new RemoteException("invalid to pays amount");
		}
		if (!CheckUtils.isValidAmount(getsAmount)) {
			throw new RemoteException("invalid to pays amount object");
		}
		if (!CheckUtils.isValidAmountValue(paysAmount.getValue())) {
			throw new RemoteException("invalid to gets amount");
		}
		if (!CheckUtils.isValidAmount(paysAmount)) {
			throw new RemoteException("invalid to gets amount object");
		}
		tx_json.put("TransactionType", "OfferCreate");
		tx_json.put("Account", account);
		if (type.equals("Sell")) {
			tx.setFlags(type);
		}
		tx_json.put("TakerPays", toAmount(paysAmount));
		tx_json.put("TakerGets", toAmount(getsAmount));
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.localSign, params);
		OfferCreateInfo bean = JsonUtils.toEntity(msg, OfferCreateInfo.class);
		return bean;
	}
	
	/**
	 * 根据金额对象内容返回信息
	 * 货币单位SWT转基本单位
	 * 
	 * @param amount 金额对象
	 * @return
	 */
	private Object toAmount(Amount amount) {
		String value = amount.getValue();
		BigDecimal temp = new BigDecimal(value);
		BigDecimal max_value = new BigDecimal("100000000000");
		String currency = amount.getCurrency();
		if (StringUtils.isNotEmpty(value) && temp.compareTo(max_value) > 0) {
			throw new RemoteException("invalid amount: amount's maximum value is 100000000000");
		}
		if (currency.equals("SWT")) {
			BigDecimal exchange_rate = new BigDecimal("1000000.00");
			BigDecimal rs = temp.multiply(exchange_rate);
			return String.valueOf(rs.intValue());
		}
		return amount;
	}
	
	/**
	 * 取消挂单
	 *
	 * @param account 挂单方账号
	 * @param sequence 取消的单子号
	 * @param secret
	 * @param memo
	 * @return
	 */
	public OfferCancelInfo buildOfferCancelTx(String account, Integer sequence, String memo, String secret) {
		Transaction tx = new Transaction();
		tx.setCommand("submit");
		tx.setSecret(secret);
		tx.setMemo(memo);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid source address");
		}
		tx_json.put("TransactionType", "OfferCancel");
		tx_json.put("Account", account);
		tx_json.put("OfferSequence", sequence);
		Map params = new HashMap();
		params.put("tx_json", tx_json);
		String msg = tx.submit(this.conn, this.localSign, params);
		OfferCancelInfo bean = JsonUtils.toEntity(msg, OfferCancelInfo.class);
		return bean;
	}
	
	/**
	 * 提交信息
	 * 
	 * @return
	 */
	public String submit(Map params) {
		params.remove("message");
		System.out.println("参数：" + JsonUtils.toJsonString(params));
		String msg = this.conn.submit(params);
		System.out.println(msg);
		return msg;
	}
	
	/************ setter and getter ************/
	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public Boolean getLocal_sign() {
		return localSign;
	}
	
	public void setLocal_sign(Boolean local_sign) {
		this.localSign = local_sign;
	}
	
	
	/**********************返回transaction对象方法*************************/
	
	/**
	 * 获取交易对象
	 *
	 * @param account 发起账号
	 * @param to 目标账号
	 * @param amount 支付金额对象Amount
	 * @param value
	 * @param currency
	 * @param issuer
	 * @return Transaction
	 */
	public Transaction buildPaymentTx(String account, String to, Amount amount) {
		Transaction tx = new Transaction();
		tx.setAccount(account);
		tx.setTo(to);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAddress(to)) {
			throw new RemoteException("invalid destination address");
		}
		tx_json.put("TransactionType", "Payment");
		tx_json.put("Account", account);
		tx_json.put("Amount", toAmount(amount));
		tx_json.put("Destination", to);
		tx.setCommand("submit");
		tx.setTx_json(tx_json);
		tx.setConn(conn);
		tx.setLocalSign(localSign);
		return tx;
	}
	
	/**
	 * 设置关系
	 *
	 * @param type 关系种类
	 * @param account 设置关系的源账号
	 * @param target 目标账号
	 * @param limit 关系金额 对象Amount
	 * @return Transaction
	 */
	public Transaction buildRelationTx(String type_value, String account, String target, Amount limit) {
		Transaction tx = new Transaction();
		// 将参数写入tx对象,方便读取校验
		tx.setAccount(account);
		tx.setTo(target);
		tx.setLimit(limit);
		tx.setRelation_type(type_value);
		tx.setCommand("submit");
		tx.setConn(conn);
		tx.setLocalSign(localSign);
		if (!CheckUtils.isValidType("relation", type_value)) {
			throw new RemoteException("invalid relation type");
		}
		
		switch (type_value) {
			case "trust":
				return buildTrustSet_tx(tx);
			case "authorize":
			case "freeze":
			case "unfreeze":
				return buildRelationSet_tx(tx);
		}
		throw new RemoteException("build relation set should not go here");
	}
	
	/**
	 * 根据Transaction对象获取trust关系参数
	 * 
	 * @param tx Transaction
	 * @return Transaction
	 */
	private Transaction buildTrustSet_tx(Transaction tx) {
		String src = tx.getAccount();
		Amount limit = tx.getLimit();
		String quality_out = "";
		String quality_in = "";
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAmount(limit)) {
			throw new RemoteException("invalid amount");
		}
		tx_json.put("TransactionType", "TrustSet");
		tx_json.put("Account", src);
		tx_json.put("LimitAmount", toAmount(limit));
		if (StringUtils.isNotEmpty(quality_in)) {
			tx_json.put("QualityIn", quality_in);
		}
		if (StringUtils.isNotEmpty(quality_out)) {
			tx_json.put("QualityOut", quality_out);
		}
		tx.setTx_json(tx_json);
		return tx;
	}
	
	/**
	 * 根据Transaction对象获取非trust关系参数
	 * 
	 * @param tx Transaction
	 * @return Transaction
	 */
	private Transaction buildRelationSet_tx(Transaction tx) {
		String src = tx.getAccount();
		Amount limit = tx.getLimit();
		String des = tx.getTo();
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAddress(des)) {
			throw new RemoteException("invalid destination address");
		}
		if (!CheckUtils.isValidAmount(limit)) {
			throw new RemoteException("invalid amount");
		}
		// 关系类型：0信任；1授权；3冻结/解冻；
		String type = tx.getRelation_type();
		tx_json.put("TransactionType", type.equals("unfreeze") ? "RelationDel" : "RelationSet");
		tx_json.put("RelationType", type.equals("authorize") ? "1" : "3");
		tx_json.put("Account", src);
		tx_json.put("Target", des);
		tx_json.put("LimitAmount", toAmount(limit));
		tx.setTx_json(tx_json);
		return tx;
	}
	
	/**
	 * 设置账号属性
	 * 
	 * @param type_value 属性种类
	 * @param account 设置属性的源账号
	 * @return Transaction
	 */
	public Transaction buildAccountSetTx(String type_value, String account) {
		Transaction tx = new Transaction();
		tx.setAccount(account);
		tx.setProperty_type(type_value);
		tx.setCommand("submit");
		tx.setConn(conn);
		tx.setLocalSign(localSign);
		if (!CheckUtils.isValidType("accountSet", type_value)) {
			throw new RemoteException("invalid account set type");
		}
		switch (type_value) {
			case "property":
				return buildAccountSet_tx(tx);
			case "delegate":
				return buildDelegateKeySet_tx(tx);
			case "signer":
				return buildSignerSet_tx(tx);
		}
		throw new RemoteException("build account set should not go here");
	}
	
	/**
	 * 根据Transaction对象获取property关系参数
	 * 
	 * @param tx Transaction
	 * @return Transaction
	 */
	private Transaction buildAccountSet_tx(Transaction tx) {
		Map params = new HashMap();
		String src = tx.getAccount();
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		tx_json.put("Account", src);
		tx_json.put("TransactionType", "AccountSet");
		tx.setTx_json(tx_json);
		return tx;
	}
	
	/**
	 * 根据Transaction对象获取delegate关系参数
	 * 
	 * @param tx Transaction
	 * @return Transaction
	 */
	private Transaction buildDelegateKeySet_tx(Transaction tx) {
		Map params = new HashMap();
		String src = tx.getAccount();
		String delegate_key = "";
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(src)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidAddress(delegate_key)) {
			throw new RemoteException("invalid regular key address");
		}
		tx_json.put("TransactionType", "SetRegularKey");
		tx_json.put("Account", src);
		tx_json.put("RegularKey", delegate_key);
		tx.setTx_json(tx_json);
		return tx;
	}
	
	/**
	 * 根据Transaction对象获取signer关系参数
	 * 
	 * @param tx Transaction
	 * @return Transaction
	 */
	private Transaction buildSignerSet_tx(Transaction tx) {
		Map params = new HashMap();
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		tx.setTx_json(tx_json);
		return tx;
	}
	
	/**
	 * 挂单
	 *
	 * @param type 挂单类型，固定的两个值：Buy、Sell
	 * @param account 挂单方账号
	 * @param getsAmount 对方得到的，即挂单方支付的
	 * @param paysAmount 对方支付的，即挂单方获得的
	 * @return Transaction
	 */
	public Transaction buildOfferCreateTx(String type, String account, Amount getsAmount, Amount paysAmount) {
		Transaction tx = new Transaction();
		tx.setCommand("submit");
		tx.setConn(conn);
		tx.setLocalSign(localSign);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid source address");
		}
		if (!CheckUtils.isValidType("offer", type)) {
			throw new RemoteException("invalid offer type");
		}
		if (!CheckUtils.isValidAmountValue(getsAmount.getValue())) {
			throw new RemoteException("invalid to pays amount");
		}
		if (!CheckUtils.isValidAmount(getsAmount)) {
			throw new RemoteException("invalid to pays amount object");
		}
		if (!CheckUtils.isValidAmountValue(paysAmount.getValue())) {
			throw new RemoteException("invalid to gets amount");
		}
		if (!CheckUtils.isValidAmount(paysAmount)) {
			throw new RemoteException("invalid to gets amount object");
		}
		tx_json.put("TransactionType", "OfferCreate");
		tx_json.put("Account", account);
		if (type.equals("Sell")) {
			tx.setFlags(type);
		}
		tx_json.put("TakerPays", toAmount(paysAmount));
		tx_json.put("TakerGets", toAmount(getsAmount));
		tx.setTx_json(tx_json);
		return tx;
	}
	
	/**
	 * 取消挂单
	 *
	 * @param account 挂单方账号
	 * @param sequence 取消的单子号
	 * @return Transaction
	 */
	public Transaction buildOfferCancelTx(String account, Integer sequence) {
		Transaction tx = new Transaction();
		tx.setCommand("submit");
		tx.setConn(conn);
		tx.setLocalSign(localSign);
		// 校验,并将参数写入tx_json对象
		Map tx_json = new HashMap();
		if (!CheckUtils.isValidAddress(account)) {
			throw new RemoteException("invalid source address");
		}
		tx_json.put("TransactionType", "OfferCancel");
		tx_json.put("Account", account);
		tx_json.put("OfferSequence", sequence);
		tx.setTx_json(tx_json);
		return tx;
	}
}
