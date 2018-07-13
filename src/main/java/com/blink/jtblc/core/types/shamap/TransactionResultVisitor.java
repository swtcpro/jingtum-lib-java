package com.blink.jtblc.core.types.shamap;
import com.blink.jtblc.core.types.known.tx.result.TransactionResult;

public interface TransactionResultVisitor {
    public void onTransaction(TransactionResult tx);
}
