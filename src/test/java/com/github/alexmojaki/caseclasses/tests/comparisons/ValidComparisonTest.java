package com.github.alexmojaki.caseclasses.tests.comparisons;

import com.github.alexmojaki.caseclasses.CaseClass;
import com.github.alexmojaki.caseclasses.SimpleCaseClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.github.alexmojaki.caseclasses.tests.Utils.compare;
import static org.junit.Assert.assertTrue;

public class ValidComparisonTest {

    private static void assertEqual(CaseClass o1, CaseClass o2) {
        Assert.assertEquals(0, compare(o1, o2));
    }

    @Test
    public void equal() {
        assertEqual(new SimpleCaseClass(), new SimpleCaseClass());
        assertEqual(
                new SimpleCaseClass("a", 1),
                new SimpleCaseClass("a", 1));
        assertEqual(
                new SimpleCaseClass("a", 1, "b", 2),
                new SimpleCaseClass("a", 1, "b", 2));
        assertEqual(
                new SimpleCaseClass("a", "1"),
                new SimpleCaseClass("a", "1"));
        assertEqual(
                new SimpleCaseClass("a", "1", "b", 2),
                new SimpleCaseClass("a", "1", "b", 2));
        assertEqual(
                new SimpleCaseClass("a", 1, "b", "2"),
                new SimpleCaseClass("a", 1, "b", "2"));
        assertEqual(
                new SimpleCaseClass("a", "1", "b", "2"),
                new SimpleCaseClass("a", "1", "b", "2"));
        assertEqual(
                new SimpleCaseClass("a", new SimpleCaseClass("b", 1)),
                new SimpleCaseClass("a", new SimpleCaseClass("b", 1))
        );
        assertEqual(
                new SimpleCaseClass("a", new SimpleCaseClass("b", new SimpleCaseClass("c", 1, "d", 2), "e", 3)),
                new SimpleCaseClass("a", new SimpleCaseClass("b", new SimpleCaseClass("c", 1, "d", 2), "e", 3))
        );
    }

    private static void assertLess(CaseClass o1, CaseClass o2) {
        assertTrue(compare(o1, o2) < 0);
        assertTrue(compare(o2, o1) > 0);
    }

    @Test
    public void less() {
        assertLess(
                new SimpleCaseClass("a", 1),
                new SimpleCaseClass("a", 3));
        assertLess(
                new SimpleCaseClass("a", 1, "b", 2),
                new SimpleCaseClass("a", 1, "b", 3));
        assertLess(
                new SimpleCaseClass("a", "1"),
                new SimpleCaseClass("a", "3"));
        assertLess(
                new SimpleCaseClass("a", "1", "b", 2),
                new SimpleCaseClass("a", "1", "b", 3));
        assertLess(
                new SimpleCaseClass("a", 1, "b", "2"),
                new SimpleCaseClass("a", 1, "b", "3"));
        assertLess(
                new SimpleCaseClass("a", "1", "b", "2"),
                new SimpleCaseClass("a", "1", "b", "3"));
        assertLess(
                new SimpleCaseClass("a", 1, "b", 2),
                new SimpleCaseClass("a", 3, "b", 2));
        assertLess(
                new SimpleCaseClass("a", "1", "b", 2),
                new SimpleCaseClass("a", "3", "b", 2));
        assertLess(
                new SimpleCaseClass("a", 1, "b", "2"),
                new SimpleCaseClass("a", 3, "b", "2"));
        assertLess(
                new SimpleCaseClass("a", "1", "b", "2"),
                new SimpleCaseClass("a", "3", "b", "2"));
        assertLess(
                new SimpleCaseClass("a", new SimpleCaseClass("b", 1)),
                new SimpleCaseClass("a", new SimpleCaseClass("b", 5))
        );
        assertLess(
                new SimpleCaseClass("a", new SimpleCaseClass("b", new SimpleCaseClass("c", 1, "d", 2), "e", 3)),
                new SimpleCaseClass("a", new SimpleCaseClass("b", new SimpleCaseClass("c", 5, "d", 2), "e", 3))
        );
        assertLess(
                new SimpleCaseClass("a", new SimpleCaseClass("b", new SimpleCaseClass("c", 1, "d", 2), "e", 3)),
                new SimpleCaseClass("a", new SimpleCaseClass("b", new SimpleCaseClass("c", 1, "d", 5), "e", 3))
        );
        assertLess(
                new SimpleCaseClass("a", new SimpleCaseClass("b", new SimpleCaseClass("c", 1, "d", 2), "e", 3)),
                new SimpleCaseClass("a", new SimpleCaseClass("b", new SimpleCaseClass("c", 1, "d", 2), "e", 5))
        );
    }

}
