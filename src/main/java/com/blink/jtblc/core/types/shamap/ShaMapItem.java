package com.blink.jtblc.core.types.shamap;

import com.blink.jtblc.core.coretypes.hash.prefixes.Prefix;
import com.blink.jtblc.core.serialized.BytesSink;

abstract public class ShaMapItem<T> {
    abstract void toBytesSink(BytesSink sink);
    public abstract ShaMapItem<T> copy();
    public abstract T value();
    public abstract Prefix hashPrefix();
}
