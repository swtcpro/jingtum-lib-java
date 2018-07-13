package com.blink.jtblc.core.coretypes;

import com.blink.jtblc.core.fields.Type;
import com.blink.jtblc.core.serialized.BytesSink;
import com.blink.jtblc.core.serialized.SerializedType;

import java.util.BitSet;

// TODO
public class Flags extends BitSet implements SerializedType {
    @Override
    public Object toJSON() {
        return null;
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public String toHex() {
        return null;
    }

    @Override
    public void toBytesSink(BytesSink to) {

    }

    @Override
    public Type type() {
        return Type.UInt32;
    }
}
