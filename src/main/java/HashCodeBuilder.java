import java.util.Objects;

class HashCodeBuilder extends AbstractResultBuilder {

    static int hash(CaseClass obj) {
        HashCodeBuilder builder = new HashCodeBuilder();
        obj.buildResult(builder);
        return builder.result;
    }

    private int result = 17;

    @Override
    protected void simpleAdd(String name, Object value) {
        extendHashCode(Objects.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, boolean value) {
        extendHashCode(value ? 1 : 0);
    }

    @Override
    protected void simpleAdd(String name, int value) {
        extendHashCode(value);
    }

    @Override
    protected void simpleAdd(String name, char value) {
        extendHashCode((int) value);
    }

    @Override
    protected void simpleAdd(String name, byte value) {
        extendHashCode((int) value);
    }

    @Override
    protected void simpleAdd(String name, short value) {
        extendHashCode((int) value);
    }

    @Override
    protected void simpleAdd(String name, long value) {
        extendHashCode((int) (value ^ (value >>> 32)));
    }

    @Override
    protected void simpleAdd(String name, float value) {
        extendHashCode(Float.floatToIntBits(value));
    }

    @Override
    protected void simpleAdd(String name, double value) {
        simpleAdd(name, Double.doubleToLongBits(value));
    }

    private void extendHashCode(int hash) {
        result = 31 * result + hash;
    }
}
