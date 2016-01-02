public abstract class DualResultBuilder extends AbstractResultBuilder {

    private SecondResultBuilder secondBuilder = new SecondResultBuilder();
    protected CaseClass o1;
    protected CaseClass o2;
    private int position = 0;
    private String currentName;

    public DualResultBuilder(CaseClass o1, CaseClass o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    @Override
    public void simpleName(String name) {
        currentName = name;
    }

    @Override
    public boolean value(Object value) {
        buildSecondResult();
        if (foundSecondValue()) {
            position++;
            return apply(currentName, value, secondBuilder.currentName, secondBuilder.currentValue);
        }
        return extraFirstValue(currentName, value);
    }

    private boolean foundSecondValue() {
        return secondBuilder.position > position;
    }

    private void buildSecondResult() {
        secondBuilder.position = 0;
        o2.buildResult(secondBuilder);
    }

    protected void checkForExtraSecondValue() {
        buildSecondResult();
        if (foundSecondValue()) {
            extraSecondValue(secondBuilder.currentName, secondBuilder.currentValue);
        }
    }

    protected abstract boolean extraFirstValue(String name, Object value);

    protected abstract void extraSecondValue(String name, Object value);

    protected abstract boolean apply(String name1, Object value1, String name2, Object value2);

    private class SecondResultBuilder extends AbstractResultBuilder {

        private int position;
        private String currentName;
        private Object currentValue;

        @Override
        public boolean name(String name) {
            currentName = name;
            return position++ == DualResultBuilder.this.position;
        }

        @Override
        public boolean value(Object value) {
            currentValue = value;
            return true;
        }
    }


}
