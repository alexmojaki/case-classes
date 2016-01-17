package com.github.alexmojaki.caseclasses;

class GetByNameBuilder extends AbstractResultBuilder {

    static Object getValueByName(CaseClass obj, String name) {
        GetByNameBuilder builder = new GetByNameBuilder();
        builder.name = name;
        obj.buildResult(builder);
        return builder.value;
    }

    private String name;
    private Object value;

    @Override
    protected void simpleAdd(String name, Object value) {
        if (this.name.equals(name)) {
            this.value = value;
        }
    }

    @Override
    protected boolean convertArraysToLists() {
        return false;
    }

}
