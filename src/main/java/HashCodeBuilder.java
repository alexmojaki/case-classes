import java.util.Objects;

class HashCodeBuilder extends AbstractResultBuilder {

    static int hash(CaseClass obj) {
        HashCodeBuilder builder = new HashCodeBuilder();
        obj.buildResult(builder);
        return builder.result;
    }

    private int result = 1;

    @Override
    protected void simpleAdd(String name, Object value) {
        extendHashCode(Objects.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, boolean value) {
        extendHashCode(Boolean.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, int value) {
        extendHashCode(Integer.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, char value) {
        extendHashCode(Character.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, byte value) {
        extendHashCode(Byte.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, short value) {
        extendHashCode(Short.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, long value) {
        extendHashCode(Long.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, float value) {
        extendHashCode(Float.hashCode(value));
    }

    @Override
    protected void simpleAdd(String name, double value) {
        extendHashCode(Double.hashCode(value));
    }

    private void extendHashCode(int hash) {
        result = 31 * result + hash;
    }
}
