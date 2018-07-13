package com.blink.jtblc.core.coretypes.hash;

import com.blink.jtblc.core.fields.Field;
import com.blink.jtblc.core.fields.Hash128Field;
import com.blink.jtblc.core.fields.Type;
import com.blink.jtblc.core.serialized.BytesSink;

public class Hash128 extends Hash<Hash128> {
    public Hash128(byte[] bytes) {
        super(bytes, 16);
    }

    @Override
    public Object toJSON() {
        return translate.toJSON(this);
    }

    @Override
    public byte[] toBytes() {
        return translate.toBytes(this);
    }

    @Override
    public String toHex() {
        return translate.toHex(this);
    }

    @Override
    public void toBytesSink(BytesSink to) {
        translate.toBytesSink(this, to);
    }

    @Override
    public Type type() {
        return Type.Hash128;
    }

    public static class Translator extends HashTranslator<Hash128> {
        @Override
        public Hash128 newInstance(byte[] b) {
            return new Hash128(b);
        }

        @Override
        public int byteWidth() {
            return 16;
        }
    }
    public static Translator translate = new Translator();

    public static Hash128Field hash128Field(final Field f) {
        return new Hash128Field(){ @Override public Field getField() {return f;}};
    }

    static public Hash128Field EmailHash = hash128Field(Field.EmailHash);

}
