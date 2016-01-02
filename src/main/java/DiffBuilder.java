class DiffBuilder extends DualResultBuilder {

    public DiffBuilder(CaseClass o1, CaseClass o2) {
        super(o1, o2);
    }

    @Override
    protected boolean extraFirstValue(String name, Object value) {
        return false;
    }

    @Override
    protected void extraSecondValue(String name, Object value) {

    }

    @Override
    protected boolean apply(String name1, Object value1, String name2, Object value2) {
        return false;
    }
}
