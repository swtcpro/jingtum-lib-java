package com.blink.jtblc.core.serialized;

import com.blink.jtblc.core.fields.Type;

public interface SerializedType {
    Object toJSON();
    byte[] toBytes();
    String toHex();
    void toBytesSink(BytesSink to);
    Type type();
}
