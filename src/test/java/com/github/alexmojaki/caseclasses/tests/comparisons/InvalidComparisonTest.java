package com.github.alexmojaki.caseclasses.tests.comparisons;

import com.github.alexmojaki.caseclasses.SimpleCaseClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.github.alexmojaki.caseclasses.tests.Utils.compare;

public class InvalidComparisonTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(ClassCastException.class);
    }

    @Test
    public void differentTypes() {
        compare(new SimpleCaseClass("a", 1), new SimpleCaseClass("a", "1"));
    }

    @Test
    public void differentNames() {
        compare(new SimpleCaseClass("a", 1), new SimpleCaseClass("b", 1));
    }

    @Test
    public void extraFirstName() {
        compare(new SimpleCaseClass("a", 1, "c", 2), new SimpleCaseClass("a", 1));
    }

    @Test
    public void extraSecondName() {
        compare(new SimpleCaseClass("a", 1), new SimpleCaseClass("a", 1, "c", 2));
    }

    @Test
    public void notComparable() {
        Object o = new Object();
        compare(new SimpleCaseClass("a", o), new SimpleCaseClass("a", o));
    }

}
