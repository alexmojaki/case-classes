import java.util.*;

public enum CaseClasses {
    ;

    public static Object getValueByName(CaseClass obj, String name) {
        return GetByNameBuilder.getValueByName(obj, name);
    }

    public static int hashCode(CaseClass obj) {
        return HashCodeBuilder.hash(obj);
    }

    public static boolean equals(CaseClass o1, Object o2) {
        return EqualsBuilder.equals(o1, o2);
    }

    public static String toString(CaseClass obj) {
        return ToStringBuilder.toString(obj);
    }

    public static <T extends Collection<E>, E> void addValues(CaseClass obj, T collection, Class<E> type) {
        CollectionBuilder.addValues(obj, collection, type);
    }

    public static <T extends Collection> void addValues(CaseClass obj, T collection) {
        CollectionBuilder.addValues(obj, collection);
    }

    public static <E> List<E> values(CaseClass obj, Class<E> type) {
        List<E> values = new ArrayList<E>();
        addValues(obj, values, type);
        return values;
    }

    public static List values(CaseClass obj) {
        return values(obj, Object.class);
    }

    public static <T extends Collection<String>> void addNames(CaseClass obj, T collection) {
        NamesBuilder.addNames(obj, collection);
    }

    public static List<String> getNameList(CaseClass obj) {
        List<String> names = new ArrayList<String>();
        addNames(obj, names);
        return names;
    }

    public static <T extends Map<String, V>, V> void putValues(CaseClass obj, T map, Class<V> type) {
        MapBuilder.putValues(obj, map, type);
    }

    public static <T extends Map> void putValues(CaseClass obj, T map) {
        MapBuilder.putValues(obj, map);
    }

    public static <V> Map<String, V> toMap(CaseClass obj, Class<V> type) {
        Map<String, V> map = new HashMap<String, V>();
        putValues(obj, map, type);
        return map;
    }

    public static Map<String, Object> toMap(CaseClass obj) {
        return toMap(obj, Object.class);
    }

    public static String getTable(Iterable<? extends CaseClass> objects) {
        return TableBuilder.getTable(objects);
    }

    private static class MapCaseClass extends AbstractCaseClass {
        private Map<? extends String, ?> map;

        @Override
        public void buildResult(ResultBuilder builder) {
            for (Map.Entry<? extends String, ?> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
    }

    public static CaseClass toCaseClass(final Map<? extends String, ?> map) {
        MapCaseClass result = new MapCaseClass();
        result.map = map;
        return result;
    }

    private static class IterableCaseClass extends AbstractCaseClass {
        private Iterable<?> iterable;

        @Override
        public void buildResult(ResultBuilder builder) {
            int i = 0;
            for (Object value : iterable) {
                builder.add("" + i++, value);
            }
        }
    }

    public static CaseClass toCaseClass(final Iterable<?> iterable) {
        IterableCaseClass result = new IterableCaseClass();
        result.iterable = iterable;
        return result;
    }

    public static void assertEquals(CaseClass expected, CaseClass actual) {
        DiffBuilder.assertEquals(expected, actual);
    }

    public static String getDifferenceReport(CaseClass expected, CaseClass actual) {
        return DiffBuilder.getDifferenceReport(expected, actual);
    }

}
