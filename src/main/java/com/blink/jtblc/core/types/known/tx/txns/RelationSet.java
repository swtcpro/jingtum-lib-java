package com.blink.jtblc.core.types.known.tx.txns;

import com.blink.jtblc.core.coretypes.Amount;
import com.blink.jtblc.core.fields.Field;
import com.blink.jtblc.core.serialized.enums.TransactionType;
import com.blink.jtblc.core.types.known.tx.Transaction;

public class RelationSet extends Transaction {
	
    public RelationSet() {
        super(TransactionType.RelationSet);
    }
    

    public Amount limitAmount() {return get(Amount.LimitAmount);}
    public void limitAmount(Amount val) {put(Field.LimitAmount, val);}
    
}