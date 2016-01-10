public abstract class AbstractResultBuilder implements ResultBuilder {

    protected abstract void simpleAdd(String name, Object value);

    protected void simpleAdd(String name, int value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, boolean value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, float value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, double value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, long value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, byte value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, short value) {
        simpleAdd(name, (Object) value);
    }

    protected void simpleAdd(String name, char value) {
        simpleAdd(name, (Object) value);
    }

    @Override
    public final ResultBuilder add(String name, Object value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, char value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, boolean value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, byte value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, short value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, int value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, long value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, float value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, double value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        simpleAdd(name, value);
        return this;
    }

}
