public abstract class AbstractResultBuilder implements ResultBuilder {

    protected abstract void voidAdd(String name, Object value);

    @Override
    public final ResultBuilder add(String name, Object value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        voidAdd(name, value);
        return this;
    }
}
