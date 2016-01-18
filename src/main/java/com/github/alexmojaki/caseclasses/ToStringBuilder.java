package com.github.alexmojaki.caseclasses;

class ToStringBuilder extends AbstractResultBuilder {

    private final StringBuilder stringBuilder = new StringBuilder(40);
    private boolean first = true;

    static String toString(CaseClass obj) {
        if (obj == null) {
            return "null";
        }
        ToStringBuilder builder = new ToStringBuilder();
        StringBuilder stringBuilder = builder.stringBuilder;
        stringBuilder.append(obj.getClass().getSimpleName());
        stringBuilder.append('(');
        obj.buildResult(builder);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    @Override
    protected void simpleAdd(String name, Object value) {
        if (first) {
            first = false;
        } else {
            stringBuilder.append(", ");
        }
        stringBuilder.append(name);
        stringBuilder.append(" = ");
        stringBuilder.append(value);
    }

}
