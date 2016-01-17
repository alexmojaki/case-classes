package com.github.alexmojaki.caseclasses;

import com.google.common.primitives.*;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractResultBuilder implements ResultBuilder {

    protected abstract void simpleAdd(String name, Object value);

    protected void simpleAdd(String name, int value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, boolean value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, float value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, double value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, long value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, byte value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, short value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, char value) {
        simpleAdd(name, (Object) value);
    }

    @Override
    public final ResultBuilder add(String name, Object value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, char value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, boolean value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, byte value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, short value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, int value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, long value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, float value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, double value) {
        simpleAdd(name, value);
        return this;
    }

}
