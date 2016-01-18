package com.github.alexmojaki.caseclasses;

/**
 * Implementing this class allows greater flexibility for the
 * {@link CaseClasses#equals(CaseClass, Object)} method (used
 * by {@link AbstractCaseClass}). An instance of a class that implements this
 * interface may be considered equal to another object only if that
 * object is an instance of the class returned by the first instance's
 * {@link FlexiblyEqual#equalsBaseClass()} method.
 */
public interface FlexiblyEqual extends CaseClass {

    /**
     * Return the class that other objects must be an instance of in
     * order to be potentially equal to this object.
     * The implementation should simply return a constant: either
     * the class that the method is declared in, or an interface
     * that the class implements so that different implementations
     * of the interface can be considered equal.
     */
    Class equalsBaseClass();

}
