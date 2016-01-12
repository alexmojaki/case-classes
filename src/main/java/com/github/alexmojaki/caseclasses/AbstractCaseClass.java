package com.github.alexmojaki.caseclasses;

public abstract class AbstractCaseClass implements CaseClass {

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return CaseClasses.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return CaseClasses.hashCode(this);
    }

    @Override
    public String toString() {
        return CaseClasses.toString(this);
    }

}
