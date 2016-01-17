package com.github.alexmojaki.caseclasses;

import java.util.ArrayList;
import java.util.List;

class NamesBuilder extends AbstractResultBuilder {

    private List<String> list = new ArrayList<String>();

    public static List<String> getNameList(CaseClass obj) {
        NamesBuilder builder = new NamesBuilder();
        obj.buildResult(builder);
        return builder.list;
    }

    @Override
    protected void simpleAdd(String name, Object value) {
        list.add(name);
    }

}