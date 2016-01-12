package com.github.alexmojaki.caseclasses;

import java.util.ArrayList;
import java.util.List;

public abstract class DualResultBuilder extends AbstractResultBuilder {

    private List<String> names = new ArrayList<String>();
    private List<Object> values = new ArrayList<Object>();

    protected void buildResult(CaseClass o1, CaseClass o2) {
        o1.buildResult(this);
        SecondResultBuilder secondResultBuilder = new SecondResultBuilder();
        o2.buildResult(secondResultBuilder);
        int size = names.size();
        for (int i = secondResultBuilder.index; i < size; i++) {
            extraFirstValue(names.get(i), values.get(i));
        }
    }

    @Override
    protected void simpleAdd(String name, Object value) {
        names.add(name);
        values.add(value);
    }

    protected abstract void extraFirstValue(String name, Object value);

    protected abstract void extraSecondValue(String name, Object value);

    protected abstract void apply(String name1, Object value1, String name2, Object value2);

    private class SecondResultBuilder extends AbstractResultBuilder {

        private int index;

        @Override
        protected void simpleAdd(String name, Object value) {
            if (index < names.size()) {
                apply(names.get(index), values.get(index), name, value);
                index++;
            } else {
                extraSecondValue(name, value);
            }
        }
    }


}
