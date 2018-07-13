package com.blink.jtblc.core.types.known.tx.txns;

import com.blink.jtblc.core.serialized.enums.TransactionType;
import com.blink.jtblc.core.types.known.tx.Transaction;

public class TicketCreate extends Transaction {
    public TicketCreate() {
        super(TransactionType.TicketCreate);
    }
}
