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
    public boolean name(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean value(Object value) {
        this.value = value;
        return true;
    }
}
