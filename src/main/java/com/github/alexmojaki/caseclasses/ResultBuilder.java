package com.github.alexmojaki.caseclasses;

/**
 * This interface receives components from the {@link CaseClass#buildResult(ResultBuilder)}
 * method and uses it to construct some result. Each {@code add} method
 * should return the same builder so that method calls can be chained. The builder
 * should behave identically whether it receives a primitive value or the boxed value
 * of the corresponding wrapper type.
 */
public interface ResultBuilder {

    ResultBuilder add(String name, Object value);

    ResultBuilder add(String name, int value);

    ResultBuilder add(String name, boolean value);

    ResultBuilder add(String name, float value);

    ResultBuilder add(String name, double value);

    ResultBuilder add(String name, long value);

    ResultBuilder add(String name, byte value);

    ResultBuilder add(String name, short value);

    ResultBuilder add(String name, char value);

}
