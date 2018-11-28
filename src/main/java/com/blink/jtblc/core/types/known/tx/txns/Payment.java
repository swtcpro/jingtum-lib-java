package com.blink.jtblc.core.types.known.tx.txns;

import java.util.HashMap;
import java.util.Map;

import com.blink.jtblc.client.Remote;
import com.blink.jtblc.client.bean.AccountInfo;
import com.blink.jtblc.client.bean.PaymentInfo;
import com.blink.jtblc.core.coretypes.AccountID;
import com.blink.jtblc.core.coretypes.Amount;
import com.blink.jtblc.core.coretypes.PathSet;
import com.blink.jtblc.core.coretypes.hash.Hash256;
import com.blink.jtblc.core.coretypes.uint.UInt32;
import com.blink.jtblc.core.fields.Field;
import com.blink.jtblc.core.serialized.enums.TransactionType;
import com.blink.jtblc.core.types.known.tx.Transaction;
import com.blink.jtblc.core.types.known.tx.signed.SignedTransaction;
import com.blink.jtblc.utils.JsonUtils;

public class Payment extends Transaction {
	public Payment() {
		super(TransactionType.Payment);
	}
	
	public Payment(Remote remote) {
		super(TransactionType.Payment);
		this.remote = remote;
	}
	
	private Remote remote = null;
	
	public UInt32 destinationTag() {
		return get(UInt32.DestinationTag);
	}
	
	public Hash256 invoiceID() {
		return get(Hash256.InvoiceID);
	}
	
	public Amount amount() {
		return get(Amount.Amount);
	}
	
	public Amount sendMax() {
		return get(Amount.SendMax);
	}
	
	public AccountID destination() {
		return get(AccountID.Destination);
	}
	
	public PathSet paths() {
		return get(PathSet.Paths);
	}
	
	public void destinationTag(UInt32 val) {
		put(Field.DestinationTag, val);
	}
	
	public void invoiceID(Hash256 val) {
		put(Field.InvoiceID, val);
	}
	
	public void amount(Amount val) {
		put(Field.Amount, val);
	}
	
	public void sendMax(Amount val) {
		put(Field.SendMax, val);
	}
	
	public void destination(AccountID val) {
		put(Field.Destination, val);
	}
	
	public void paths(PathSet val) {
		put(Field.Paths, val);
	}
	
	public PaymentInfo submit(String secret) {
		Object obj=this.account();
		AccountInfo ainfo = remote.requestAccountInfo(obj.toString(), null, "trust");
		this.sequence(new UInt32(ainfo.getAccountData().getSequence()));
		
		SignedTransaction tx = sign(secret);
		//System.out.println(tx.tx_blob);
		Map<String, Object> params=new HashMap<>();
		params.put("tx_blob", tx.tx_blob);
		String reString=this.remote.sendMessage("submit", params);
		//System.out.println("-----------reString-----------");
		//System.out.println(reString);
		PaymentInfo bean = JsonUtils.toEntity(reString, PaymentInfo.class);
		return bean;
	}
}
