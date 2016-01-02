public interface ResultBuilder {

    boolean name(String name);

    boolean value(Object value);

    boolean value(int value);

    boolean value(long value);

    boolean value(float value);

    boolean value(double value);

    boolean value(byte value);

    boolean value(short value);

    boolean value(boolean value);

}
