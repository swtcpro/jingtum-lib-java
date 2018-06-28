package com.blink.jtblc.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.blink.jtblc.client.bean.Amount;
import com.blink.jtblc.client.bean.TransactionInfo;
import com.blink.jtblc.connection.Connection;
import com.blink.jtblc.exceptions.RemoteException;
import com.blink.jtblc.exceptions.TransactionException;
import com.blink.jtblc.utils.JsonUtils;

//交易信息
public class Transaction {
	
	//发起账号
	private String account;
	
	//目标账号
	private String to;
	
	//支付金额对象
	private Amount amount;
	
	//关系金额对象
	private Amount limit;
	
	//挂单方支付金额对象
	private Amount taker_gets;
	
	//挂单方获得金额对象
	private Amount taker_pays;
	
	//私钥
	private String secret;
	
	//备注信息
	private String memo;
	
	//交易签名
	private String txnSignature;
	
	//关系种类 
	private String relation_type;
	
	//属性种类
	private String property_type;
	
	//挂单类型
	private String flags;
	
	//取消的单子号
	private Integer sequence;
	
	//command
	private String command;
	
	//tx_json
	private Map tx_json = new HashMap();
	
	public String getAccount() {
		//tx_json.get("Account")
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	
	public Amount getLimit() {
		return limit;
	}

	public void setLimit(Amount limit) {
		this.limit = limit;
	}

	public Amount getTaker_gets() {
		return taker_gets;
	}

	public void setTaker_gets(Amount taker_gets) {
		this.taker_gets = taker_gets;
	}

	public Amount getTaker_pays() {
		return taker_pays;
	}

	public void setTaker_pays(Amount taker_pays) {
		this.taker_pays = taker_pays;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTxnSignature() {
		return txnSignature;
	}

	public void setTxnSignature(String txnSignature) {
		this.txnSignature = txnSignature;
	}
	
	public String getRelation_type() {
		return relation_type;
	}

	public void setRelation_type(String relation_type) {
		this.relation_type = relation_type;
	}

	public String getProperty_type() {
		return property_type;
	}

	public void setProperty_type(String property_type) {
		this.property_type = property_type;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
		this.tx_json.put("Flags",flags);
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Map getTx_json() {
		return tx_json;
	}

	public void setTx_json(Map tx_json) {
		this.tx_json = tx_json;
	}

	/**
	 * 提交交易信息
	 * @return
	 */
	public String submit(Connection conn, Boolean local_sign, Map params){
		String tx_json_transactionType = "";
		String tx_json_blob = "WW";
		if(local_sign) {
			//签名之后传给底层
			tx_json_blob = sign(this.secret);
	        params.put("tx_blob", tx_json_blob);
		}else if(tx_json_transactionType.equals("Signer")) {
			//直接将blob传给底层
	        params.put("tx_blob", tx_json_blob);
		}else {
			//不签名交易传给底层
			params.put("secret", this.getSecret());
	        //params.put("tx_json", tx_json);
		}
		params.put("memo", this.memo);
		params.put("command", this.command);
		System.out.println("参数：" + JsonUtils.toJsonString(params));
		String msg = conn.submit(params);
		return msg;
    }

	/**
	 * 添加备注信息
	 * @param memo
	 */
	public void addMemo(String memo) {
		this.memo = memo;
	}
	
	/**
	 * 签名
	 * @param sercet
	 * @return
	 */
	public String sign(String secret) {
		//1.获取账号信息,钱包信息--待完善
        //String rs = remote.requestAccountTums(account);
		return "对交易签名";
	}
	
	   /**
     * 设置费用
     * @param fee
     */
    public void setFee(String fee) {
        try{
            int feeJson = Integer.parseInt(fee);
            if(feeJson<10){
                this.tx_json.put("Fee","fee is too low");
            }
            this.tx_json.put("Fee",fee);
        }catch (Exception e){
            this.tx_json.put("Fee","invalid fee");
        }
    }

    /**
     * 获取交易类型
     * @return
     */
    public String getTransactionType(){
        return this.tx_json.get("TransactionType").toString();
    }


    /*public void setPath(String key){
        if (key.length() != 40) {
            throw new TransactionException(1002,"invalid path key");
        }
        JSONObject item =  (JSONObject) this.remote.getPaths().get(key);
        if (Utils.isNull(item)) {
            throw new TransactionException(1003,"non exists path key");
        }
        if(!item.get("path").toString().equals("[]")){
            this.tx_json.put("Paths",JSON.parse(String.valueOf(item.get("path"))));
            String amount = MaxAmount(item.get("choice"));
            this.tx_json.put("SendMax",amount);
        }
    }*/

    public String MaxAmount(Object amount){
        try {
            float aa = (float) (Float.parseFloat(amount.toString()) * 1.0001);
            return String.valueOf(aa);
        }catch (Exception e){
            throw new TransactionException(1004,"invalid amount to max");
        }
    }
    
    private Boolean localSign = false;
	private Connection conn = null;

	public Boolean getLocalSign() {
		return localSign;
	}

	public void setLocalSign(Boolean localSign) {
		this.localSign = localSign;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * 提交交易信息
	 * @return
	 */
	public TransactionInfo submit(){
		Map params = new HashMap();
		String tx_json_transactionType = "";
		String tx_json_blob = "WW";
		Map tx_json = this.getTx_json();
		if(localSign) {
			//签名之后传给底层
			tx_json_blob = sign(this.secret);
	        params.put("tx_blob", tx_json_blob);
		}else if(tx_json_transactionType.equals("Signer")) {
			//直接将blob传给底层
	        params.put("tx_blob", tx_json_blob);
		}else {
			//不签名交易传给底层
			params.put("secret", this.getSecret());
	        //params.put("tx_json", tx_json);
		}
		
		if (StringUtils.isNotEmpty(memo)) {
			if (memo.length() > 2048) {
				throw new RemoteException("memo is too long");
			} else {
				// tx_json.put("MemoData", __stringToHex(utf8.encode(memo));
			}
		}
		params.put("memo", this.memo);
		params.put("command", this.command);
		params.put("tx_json", tx_json);
		String msg = conn.submit(params);
		TransactionInfo bean = JsonUtils.toEntity(msg, TransactionInfo.class);
		return bean;
    }
}
