public class AbstractResultBuilder implements ResultBuilder {

    @Override
    public boolean name(String name) {
        simpleName(name);
        return true;
    }

    public void simpleName(String name) {
    }

    @Override
    public boolean value(Object value) {
        simpleValue(value);
        return false;
    }

    public void simpleValue(Object value) {
    }

    @Override
    public boolean value(int value) {
        return value((Object) value);
    }

    @Override
    public boolean value(long value) {
        return value((Object) value);
    }

    @Override
    public boolean value(float value) {
        return value((Object) value);
    }

    @Override
    public boolean value(double value) {
        return value((Object) value);
    }

    @Override
    public boolean value(byte value) {
        return value((Object) value);
    }

    @Override
    public boolean value(short value) {
        return value((Object) value);
    }

    @Override
    public boolean value(boolean value) {
        return value((Object) value);
    }
}
