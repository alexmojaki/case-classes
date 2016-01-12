package com.github.alexmojaki.caseclasses;

import java.util.ArrayList;
import java.util.List;

public class SimpleCaseClass extends AbstractCaseClass {

    private List<String> names = new ArrayList<String>();
    private List<Object> values = new ArrayList<Object>();

    public SimpleCaseClass() {
    }

    public SimpleCaseClass(String name1, Object value1) {
        add(name1, value1);
    }

    public SimpleCaseClass add(String name1, Object value1) {
        ensureNoNulls(name1 == null);
        names.add(name1);
        values.add(value1);
        return this;
    }

    public SimpleCaseClass(String name1, Object value1, String name2, Object value2) {
        add(name1, value1, name2, value2);
    }

    public SimpleCaseClass add(String name1, Object value1, String name2, Object value2) {
        ensureNoNulls(name1 == null || name2 == null);
        names.add(name1);
        names.add(name2);
        values.add(value1);
        values.add(value2);
        return this;
    }

    public SimpleCaseClass(String name1, Object value1, String name2, Object value2, String name3, Object value3) {
        add(name1, value1, name2, value2, name3, value3);
    }

    public SimpleCaseClass add(String name1, Object value1, String name2, Object value2, String name3, Object value3) {
        ensureNoNulls(name1 == null || name2 == null || name3 == null);
        names.add(name1);
        names.add(name2);
        names.add(name3);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        return this;
    }

    public SimpleCaseClass(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4) {
        add(name1, value1, name2, value2, name3, value3, name4, value4);
    }

    public SimpleCaseClass add(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4) {
        ensureNoNulls(name1 == null || name2 == null || name3 == null || name4 == null);
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        values.add(value4);
        return this;
    }

    public SimpleCaseClass(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5) {
        add(name1, value1, name2, value2, name3, value3, name4, value4, name5, value5);
    }

    public SimpleCaseClass add(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5) {
        ensureNoNulls(name1 == null || name2 == null || name3 == null || name4 == null || name5 == null);
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        names.add(name5);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        values.add(value4);
        values.add(value5);
        return this;
    }

    public SimpleCaseClass(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6) {
        add(name1, value1, name2, value2, name3, value3, name4, value4, name5, value5, name6, value6);
    }

    public SimpleCaseClass add(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6) {
        ensureNoNulls(name1 == null || name2 == null || name3 == null || name4 == null || name5 == null || name6 == null);
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        names.add(name5);
        names.add(name6);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        values.add(value4);
        values.add(value5);
        values.add(value6);
        return this;
    }

    public SimpleCaseClass(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6, String name7, Object value7) {
        add(name1, value1, name2, value2, name3, value3, name4, value4, name5, value5, name6, value6, name7, value7);
    }

    public SimpleCaseClass add(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6, String name7, Object value7) {
        ensureNoNulls(name1 == null || name2 == null || name3 == null || name4 == null || name5 == null || name6 == null || name7 == null);
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        names.add(name5);
        names.add(name6);
        names.add(name7);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        values.add(value4);
        values.add(value5);
        values.add(value6);
        values.add(value7);
        return this;
    }

    public SimpleCaseClass(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6, String name7, Object value7, String name8, Object value8) {
        add(name1, value1, name2, value2, name3, value3, name4, value4, name5, value5, name6, value6, name7, value7, name8, value8);
    }

    public SimpleCaseClass add(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6, String name7, Object value7, String name8, Object value8) {
        ensureNoNulls(name1 == null || name2 == null || name3 == null || name4 == null || name5 == null || name6 == null || name7 == null || name8 == null);
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        names.add(name5);
        names.add(name6);
        names.add(name7);
        names.add(name8);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        values.add(value4);
        values.add(value5);
        values.add(value6);
        values.add(value7);
        values.add(value8);
        return this;
    }

    public SimpleCaseClass(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6, String name7, Object value7, String name8, Object value8, String name9, Object value9) {
        add(name1, value1, name2, value2, name3, value3, name4, value4, name5, value5, name6, value6, name7, value7, name8, value8, name9, value9);
    }

    public SimpleCaseClass add(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6, String name7, Object value7, String name8, Object value8, String name9, Object value9) {
        ensureNoNulls(name1 == null || name2 == null || name3 == null || name4 == null || name5 == null || name6 == null || name7 == null || name8 == null || name9 == null);
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        names.add(name5);
        names.add(name6);
        names.add(name7);
        names.add(name8);
        names.add(name9);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        values.add(value4);
        values.add(value5);
        values.add(value6);
        values.add(value7);
        values.add(value8);
        values.add(value9);
        return this;
    }

    public SimpleCaseClass(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6, String name7, Object value7, String name8, Object value8, String name9, Object value9, String name10, Object value10) {
        add(name1, value1, name2, value2, name3, value3, name4, value4, name5, value5, name6, value6, name7, value7, name8, value8, name9, value9, name10, value10);
    }

    public SimpleCaseClass add(String name1, Object value1, String name2, Object value2, String name3, Object value3, String name4, Object value4, String name5, Object value5, String name6, Object value6, String name7, Object value7, String name8, Object value8, String name9, Object value9, String name10, Object value10) {
        ensureNoNulls(name1 == null || name2 == null || name3 == null || name4 == null || name5 == null || name6 == null || name7 == null || name8 == null || name9 == null || name10 == null);
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        names.add(name5);
        names.add(name6);
        names.add(name7);
        names.add(name8);
        names.add(name9);
        names.add(name10);
        values.add(value1);
        values.add(value2);
        values.add(value3);
        values.add(value4);
        values.add(value5);
        values.add(value6);
        values.add(value7);
        values.add(value8);
        values.add(value9);
        values.add(value10);
        return this;
    }

    private void ensureNoNulls(boolean anyNulls) {
        if (anyNulls) {
            throw new IllegalArgumentException("Name must not be null");
        }
    }

    @Override
    public void buildResult(ResultBuilder builder) {
        int size = names.size();
        for (int i = 0; i < size; i++) {
            builder.add(names.get(i), values.get(i));
        }
    }
}
