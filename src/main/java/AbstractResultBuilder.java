public abstract class AbstractResultBuilder implements ResultBuilder {

    protected abstract void simpleAdd(String name, Object value);

    @Override
    public final ResultBuilder add(String name, Object value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }
}
