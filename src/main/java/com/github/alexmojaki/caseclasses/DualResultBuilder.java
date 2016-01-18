package com.github.alexmojaki.caseclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * This class builds a result from two {@code CaseClasses}, pairing
 * the components that are added in the same 'index' and supplying them
 * to the {@link DualResultBuilder#add(String, Object, String, Object)} method.
 * If one {@code CaseClass} adds more components than the other, either
 * {@link DualResultBuilder#extraFirstValue(String, Object)} or
 * {@link DualResultBuilder#extraSecondValue(String, Object)} will be called.
 */
public abstract class DualResultBuilder {

    private List<String> names = new ArrayList<String>();
    private List<Object> values = new ArrayList<Object>();

    public void buildResult(CaseClass o1, CaseClass o2) {
        o1.buildResult(new FirstResultBuilder());
        SecondResultBuilder secondResultBuilder = new SecondResultBuilder();
        o2.buildResult(secondResultBuilder);
        int size = names.size();
        for (int i = secondResultBuilder.index; i < size; i++) {
            extraFirstValue(names.get(i), values.get(i));
        }
    }

    protected abstract void extraFirstValue(String name, Object value);

    protected abstract void extraSecondValue(String name, Object value);

    protected abstract void add(String name1, Object value1, String name2, Object value2);

    private class FirstResultBuilder extends AbstractResultBuilder {
        @Override
        protected void simpleAdd(String name, Object value) {
            names.add(name);
            values.add(value);
        }

    }

    private class SecondResultBuilder extends AbstractResultBuilder {

        private int index;

        @Override
        protected void simpleAdd(String name, Object value) {
            if (index < names.size()) {
                DualResultBuilder.this.add(names.get(index), values.get(index), name, value);
                index++;
            } else {
                extraSecondValue(name, value);
            }
        }

    }


}
