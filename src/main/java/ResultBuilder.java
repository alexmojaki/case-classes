public interface ResultBuilder {

    ResultBuilder add(String name, Object value);
    ResultBuilder add(String name, int value);
    ResultBuilder add(String name, boolean value);
    ResultBuilder add(String name, float value);
    ResultBuilder add(String name, double value);
    ResultBuilder add(String name, long value);
    ResultBuilder add(String name, byte value);
    ResultBuilder add(String name, short value);
    ResultBuilder add(String name, char value);

}
