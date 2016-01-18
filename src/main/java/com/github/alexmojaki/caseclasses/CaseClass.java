package com.github.alexmojaki.caseclasses;

/**
 * An aggregate of multiple components (values with names)
 * allowing various utilities to be applied concisely.
 */
public interface CaseClass {

    /**
     * Adds the components to the given {@link ResultBuilder}.
     * To implement, repeatedly call {@code builder.add} with values
     * that the object is composed of and a name of your choice.
     * Avoid redundant values that can be computed from others.
     * Since the builder returns itself, multiple calls can be chained.
     * For example:
     * {@code builder.add("name", name).add("age", age).add(...);}
     * <p/>
     * Note that adding a value to a builder exposes it as part of the
     * class's interface, so if you don't want to have a public
     * getter method for a value, you probably don't want to add it here.
     */
    void buildResult(ResultBuilder builder);
}
