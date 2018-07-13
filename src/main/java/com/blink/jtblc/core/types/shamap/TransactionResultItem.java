package com.blink.jtblc.core.types.shamap;

import com.blink.jtblc.core.coretypes.hash.prefixes.HashPrefix;
import com.blink.jtblc.core.coretypes.hash.prefixes.Prefix;
import com.blink.jtblc.core.serialized.BinarySerializer;
import com.blink.jtblc.core.serialized.BytesSink;
import com.blink.jtblc.core.types.known.tx.result.TransactionResult;

public class TransactionResultItem extends ShaMapItem<TransactionResult> {
    public TransactionResult result;

    public TransactionResultItem(TransactionResult result) {
        this.result = result;
    }

    @Override
    void toBytesSink(BytesSink sink) {
        BinarySerializer write = new BinarySerializer(sink);
        write.addLengthEncoded(result.txn);
        write.addLengthEncoded(result.meta);
    }

    @Override
    public ShaMapItem<TransactionResult> copy() {
        // that's ok right ;) these bad boys are immutable anyway
        return this;
    }

    @Override
    public TransactionResult value() {
        return result;
    }

    @Override
    public Prefix hashPrefix() {
        return HashPrefix.txNode;
    }
}
