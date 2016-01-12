package com.github.alexmojaki.caseclasses.tests;

import com.github.alexmojaki.caseclasses.*;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class DiffTest {

    private static void assertDiff(CaseClass o1, CaseClass o2, String expectedReport) {
        try {
            CaseClasses.assertEquals(o1, o2);
        } catch (AssertionError e) {
            assertEquals("Expected != actual. " + expectedReport, e.getMessage());
            Assert.assertEquals(expectedReport, CaseClasses.getDifferenceReport(o1, o2));
            return;
        }
        if (expectedReport != null) {
            fail();
        }
    }

    @Test
    public void assertEqualsDoesNothingForEqualValues() {
        assertDiff(null, null, null);
        assertDiff(new SimpleCaseClass(), new SimpleCaseClass(), null);
        assertDiff(
                new SimpleCaseClass("a", 1, "b", 2),
                new SimpleCaseClass("a", 1, "b", 2),
                null);
    }

    @Test
    public void simplyDifferentValues() {
        assertDiff(new SimpleCaseClass("a", 1), new SimpleCaseClass("a", 2),
                "Differences:\n" +
                        "\n" +
                        "+------+-------------+--------------+\n" +
                        "| Name | First value | Second value |\n" +
                        "+------+-------------+--------------+\n" +
                        "| a    |           1 |            2 |\n" +
                        "+------+-------------+--------------+\n");
        assertDiff(new SimpleCaseClass("a", 1, "b", 3), new SimpleCaseClass("a", 2, "b", 4),
                "Differences:\n" +
                        "\n" +
                        "+------+-------------+--------------+\n" +
                        "| Name | First value | Second value |\n" +
                        "+------+-------------+--------------+\n" +
                        "| a    |           1 |            2 |\n" +
                        "| b    |           3 |            4 |\n" +
                        "+------+-------------+--------------+\n");
        assertDiff(
                new SimpleCaseClass("a", 1, "b", 3),
                new SimpleCaseClass("a", 2, "b", 3),
                "Differences:\n" +
                        "\n" +
                        "+------+-------------+--------------+\n" +
                        "| Name | First value | Second value |\n" +
                        "+------+-------------+--------------+\n" +
                        "| a    |           1 |            2 |\n" +
                        "+------+-------------+--------------+\n" +
                        "\n" +
                        "Matching values:\n" +
                        "\n" +
                        "+------+-------+\n" +
                        "| Name | Value |\n" +
                        "+------+-------+\n" +
                        "| b    |     3 |\n" +
                        "+------+-------+\n");
        assertDiff(
                new SimpleCaseClass("a", 1, "b", 2),
                new SimpleCaseClass("a", 1, "b", 3),
                "Differences:\n" +
                        "\n" +
                        "+------+-------------+--------------+\n" +
                        "| Name | First value | Second value |\n" +
                        "+------+-------------+--------------+\n" +
                        "| b    |           2 |            3 |\n" +
                        "+------+-------------+--------------+\n" +
                        "\n" +
                        "Matching values:\n" +
                        "\n" +
                        "+------+-------+\n" +
                        "| Name | Value |\n" +
                        "+------+-------+\n" +
                        "| a    |     1 |\n" +
                        "+------+-------+\n");
        assertDiff(
                new SimpleCaseClass("a", 1, "b", 3, "c", 4, "d", 6),
                new SimpleCaseClass("a", 2, "b", 3, "c", 5, "d", 6),
                "Differences:\n" +
                        "\n" +
                        "+------+-------------+--------------+\n" +
                        "| Name | First value | Second value |\n" +
                        "+------+-------------+--------------+\n" +
                        "| a    |           1 |            2 |\n" +
                        "| c    |           4 |            5 |\n" +
                        "+------+-------------+--------------+\n" +
                        "\n" +
                        "Matching values:\n" +
                        "\n" +
                        "+------+-------+\n" +
                        "| Name | Value |\n" +
                        "+------+-------+\n" +
                        "| b    |     3 |\n" +
                        "| d    |     6 |\n" +
                        "+------+-------+\n");
    }

    @Test
    public void differentNames() {
        assertDiff(new SimpleCaseClass("a", 1, "b", 2), new SimpleCaseClass("a", 1),
                "The names of the values do not match.\n" +
                        "Expected: SimpleCaseClass(a = 1, b = 2)\n" +
                        "Actual: SimpleCaseClass(a = 1)");
        assertDiff(new SimpleCaseClass("a", 1, "b", 2), new SimpleCaseClass("a", 3),
                "The names of the values do not match.\n" +
                        "Expected: SimpleCaseClass(a = 1, b = 2)\n" +
                        "Actual: SimpleCaseClass(a = 3)");
        assertDiff(new SimpleCaseClass("a", 1), new SimpleCaseClass("a", 1, "b", 2),
                "The names of the values do not match.\n" +
                        "Expected: SimpleCaseClass(a = 1)\n" +
                        "Actual: SimpleCaseClass(a = 1, b = 2)");
        assertDiff(new SimpleCaseClass("b", 2, "a", 1), new SimpleCaseClass("a", 1, "b", 2),
                "The names of the values do not match.\n" +
                        "Expected: SimpleCaseClass(b = 2, a = 1)\n" +
                        "Actual: SimpleCaseClass(a = 1, b = 2)");
        assertDiff(new SimpleCaseClass("b", 1), new SimpleCaseClass("a", 1),
                "The names of the values do not match.\n" +
                        "Expected: SimpleCaseClass(b = 1)\n" +
                        "Actual: SimpleCaseClass(a = 1)");
    }

    class A extends AbstractCaseClass implements FlexiblyEqual {

        @Override
        public void buildResult(ResultBuilder builder) {
            builder.add("a", 1);
        }

        @Override
        public Class equalsBaseClass() {
            return A.class;
        }
    }

    class B extends A {

        @Override
        public boolean equals(Object obj) {
            return false;
        }

        @Override
        public String toString() {
            return "BBB";
        }
    }

    @Test
    public void differentClasses() {
        assertDiff(new A(), new SimpleCaseClass("a", 1),
                "The expected class is com.github.alexmojaki.caseclasses.tests.DiffTest$A but the actual class is com.github.alexmojaki.caseclasses.SimpleCaseClass");
    }

    @Test
    public void oneNullValue() {
        assertDiff(new A(), null, "Actual value is null but expected value is: A(a = 1)");
        assertDiff(null, new A(), "Expected value is null but actual value is: A(a = 1)");
    }

    @Test
    public void unequalButSimilar() {
        assertDiff(new B(), new A(),
                "The value components of the objects are equal.\n" +
                        "Expected: BBB\n" +
                        "Actual: A(a = 1)");
    }
}
