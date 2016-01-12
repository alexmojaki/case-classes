package com.github.alexmojaki.caseclasses.tests.comparisons;

import com.github.alexmojaki.caseclasses.SimpleCaseClass;
import com.github.alexmojaki.caseclasses.tests.Utils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.github.alexmojaki.caseclasses.tests.Utils.compare;

public class NullComparisonTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        exception.expect(NullPointerException.class);
    }

    @Test
    public void firstNull() {
        compare(null, new SimpleCaseClass());
    }


    @Test
    public void secondNull() {
        compare(new SimpleCaseClass(), null);
    }

    @Test
    public void bothNull() {
        compare(null, null);
    }

    @Test
    public void nullValueFirst() {
        compare(new SimpleCaseClass("a", null), new SimpleCaseClass("a", 1));
    }

    @Test
    public void nullValueSecond() {
        compare(new SimpleCaseClass("a", 1), new SimpleCaseClass("a", null));
    }

    @Test
    public void nullValueBoth() {
        compare(new SimpleCaseClass("a", null), new SimpleCaseClass("a", null));
    }

}
