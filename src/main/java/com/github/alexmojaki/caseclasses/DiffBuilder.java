package com.github.alexmojaki.caseclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class DiffBuilder extends DualResultBuilder {

    private boolean namesMatch = true;
    private List<CaseClass> matchingValues = new ArrayList<CaseClass>();
    private List<CaseClass> differingValues = new ArrayList<CaseClass>();

    static void assertEquals(CaseClass expected, CaseClass actual) {
        if (Objects.equals(expected, actual)) {
            return;
        }
        String message = "Expected != actual. " + getDifferenceReport(expected, actual);
        throw new AssertionError(message);
    }

    static String getDifferenceReport(CaseClass expected, CaseClass actual) {
        if (expected == null) {
            return "Expected value is null but actual value is: " + actual;
        } else if (actual == null) {
            return "Actual value is null but expected value is: " + expected;
        }
        if (!EqualsBuilder.classesMatch(expected, actual)) {
            return "The expected class is " + expected.getClass().getName() + " but the actual class is " + actual.getClass().getName();
        }
        DiffBuilder builder = new DiffBuilder();
        builder.buildResult(expected, actual);
        if (builder.namesMatch) {
            if (builder.differingValues.isEmpty()) {
                return "The value components of the objects are equal.\n" +
                        "Expected: " + expected + "\n" +
                        "Actual: " + actual;
            }
            return builder.getTables();
        } else {
            return "The names of the getValuesList do not match.\n" +
                    "Expected: " + CaseClasses.toString(expected) + "\n" +
                    "Actual: " + CaseClasses.toString(actual);
        }
    }

    private String getTables() {
        String tables = "Differences:\n\n" + CaseClasses.getTable(differingValues);
        if (!matchingValues.isEmpty()) {
            tables += "\n" +
                    "Matching getValuesList:\n\n" + CaseClasses.getTable(matchingValues);
        }
        return tables;
    }

    @Override
    protected void extraFirstValue(String name, Object value) {
        namesMatch = false;
    }

    @Override
    protected void extraSecondValue(String name, Object value) {
        namesMatch = false;
    }

    @Override
    protected void apply(String name1, Object value1, String name2, Object value2) {
        if (!name1.equals(name2)) {
            namesMatch = false;
        }
        if (!namesMatch) {
            return;
        }
        SimpleCaseClass row = new SimpleCaseClass("Name", name1);
        if (Objects.equals(value1, value2)) {
            matchingValues.add(row.add("Value", value1));
        } else {
            differingValues.add(row.add("First value", value1, "Second value", value2));
        }
    }
}
