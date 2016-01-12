package com.github.alexmojaki.caseclasses;

import java.util.Collection;

class NamesBuilder extends AbstractResultBuilder {

    private Collection<String> collection;

    @SuppressWarnings("unchecked")
    static <T extends Collection<String>> void addNames(CaseClass obj, T collection) {
        NamesBuilder builder = new NamesBuilder();
        builder.collection = collection;
        obj.buildResult(builder);
    }

    @Override
    protected void simpleAdd(String name, Object value) {
        collection.add(name);
    }

}