package com.blink.jtblc.core.types.known.tx.txns;

import com.blink.jtblc.core.coretypes.uint.UInt32;
import com.blink.jtblc.core.fields.Field;
import com.blink.jtblc.core.serialized.enums.TransactionType;
import com.blink.jtblc.core.types.known.tx.Transaction;

public class OfferCancel extends Transaction {
    public OfferCancel() {
        super(TransactionType.OfferCancel);
    }
    
    public UInt32 offerSequence() {return get(UInt32.OfferSequence);}
    public void offerSequence(UInt32 val) {put(Field.OfferSequence, val);}
}
