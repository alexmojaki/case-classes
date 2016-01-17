package com.github.alexmojaki.caseclasses;

import java.util.List;

class ComparisonBuilder extends DualResultBuilder {

    static <T extends CaseClass> int compare(T o1, T o2) {
        ComparisonBuilder builder = new ComparisonBuilder();
        builder.buildResult(o1, o2);
        return builder.result;
    }

    private int result = 0;

    @Override
    protected void extraFirstValue(String name, Object value) {
        throw new ClassCastException("Extra value in first argument with name " + name);
    }

    @Override
    protected void extraSecondValue(String name, Object value) {
        throw new ClassCastException("Extra value in second argument with name " + name);
    }

    private void updateResult(int newResult) {
        if (result == 0) {
            result = newResult;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void apply(String name1, Object value1, String name2, Object value2) {
        if (!name1.equals(name2)) {
            throw new ClassCastException("Differing names: " + name1 + " and " + name2);
        }
        if (value1 == null) {
            throw new NullPointerException("First value with name " + name1 + " is null");
        }
        if (value2 == null) {
            throw new NullPointerException("Second value with name " + name1 + " is null");
        }
        if (value1 instanceof Comparable) {
            updateResult(((Comparable) value1).compareTo(value2));
        } else if (value1 instanceof CaseClass) {
            updateResult(compare(
                    (CaseClass) value1,
                    (CaseClass) value2));
        } else if (value1 instanceof List) {
            updateResult(compare(
                    CaseClasses.toCaseClass((List) value1),
                    CaseClasses.toCaseClass((List) value2)));
        } else {
            throw new ClassCastException("Couldn't compare getValuesList with name " + name1);
        }
    }

    @Override
    protected boolean convertArraysToLists() {
        return true;
    }
}
