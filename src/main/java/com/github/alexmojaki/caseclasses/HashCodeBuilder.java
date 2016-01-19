package com.github.alexmojaki.caseclasses;

import java.util.Objects;

class HashCodeBuilder extends AbstractResultBuilder {

    static int hash(CaseClass obj) {
        if (obj == null) {
            return 0;
        }
        HashCodeBuilder builder = new HashCodeBuilder();
        obj.buildResult(builder);
        return builder.result;
    }

    private int result = 1;

    @Override
    protected void simpleAdd(String name, Object value) {
        extendHashCode(Objects.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, boolean value) {
        extendHashCode(value ? 1231 : 1237);
    }

    @Override
    protected void simpleAdd(String name, int value) {
        extendHashCode(value);
    }

    @Override
    protected void simpleAdd(String name, char value) {
        extendHashCode((int) value);
    }

    @Override
    protected void simpleAdd(String name, byte value) {
        extendHashCode((int) value);
    }

    @Override
    protected void simpleAdd(String name, short value) {
        extendHashCode((int) value);
    }

    @Override
    protected void simpleAdd(String name, long value) {
        extendHashCode((int) (value ^ (value >>> 32)));
    }

    @Override
    protected void simpleAdd(String name, float value) {
        extendHashCode(Float.floatToIntBits(value));
    }

    @Override
    protected void simpleAdd(String name, double value) {
        long bits = Double.doubleToLongBits(value);
        extendHashCode((int) (bits ^ (bits >>> 32)));
    }

    private void extendHashCode(int hash) {
        result = 31 * result + hash;
    }
}
