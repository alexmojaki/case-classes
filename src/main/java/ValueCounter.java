class ValueCounter extends AbstractResultBuilder {

    private int count;

    static int countValues(CaseClass obj) {
        return new ValueCounter().count(obj);
    }

    int count(CaseClass obj) {
        count = 0;
        obj.buildResult(this);
        return count;
    }

    @Override
    public boolean name(String name) {
        count++;
        return false;
    }
}
