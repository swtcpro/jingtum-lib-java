package com.blink.jtblc.core.types.known.tx.txns;

import com.blink.jtblc.client.Remote;
import com.blink.jtblc.core.coretypes.Amount;
import com.blink.jtblc.core.coretypes.uint.UInt32;
import com.blink.jtblc.core.fields.Field;
import com.blink.jtblc.core.serialized.enums.TransactionType;
import com.blink.jtblc.core.types.known.tx.Transaction;

public class RelationSet extends Transaction {
	
    public RelationSet() {
        super(TransactionType.Payment);
    }
    
    public RelationSet(Remote remote) {
		super(TransactionType.Payment);
		this.remote = remote;
	}
    
    private Remote remote = null;

    public UInt32 qualityIn() {return get(UInt32.QualityIn);}
    public UInt32 qualityOut() {return get(UInt32.QualityOut);}
    public Amount limitAmount() {return get(Amount.LimitAmount);}
    public void qualityIn(UInt32 val) {put(Field.QualityIn, val);}
    public void qualityOut(UInt32 val) {put(Field.QualityOut, val);}
    public void limitAmount(Amount val) {put(Field.LimitAmount, val);}
}