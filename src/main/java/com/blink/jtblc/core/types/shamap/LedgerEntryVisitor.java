package com.blink.jtblc.core.types.shamap;

import com.blink.jtblc.core.types.known.sle.LedgerEntry;

public interface LedgerEntryVisitor {
    public void onEntry(LedgerEntry entry);
}
