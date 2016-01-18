package com.github.alexmojaki.caseclasses;

/**
 * A convenience class with implementations of the essential
 * methods of {@code Object}: {@code equals}, {@code hashCode},
 * and {@code toString}. If extending this class is not an option,
 * just copy the source code for the required methods.
 *
 * @see CaseClasses#equals(CaseClass, Object)
 * @see CaseClasses#toString(CaseClass)
 * @see CaseClasses#hashCode(CaseClass)
 */
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
