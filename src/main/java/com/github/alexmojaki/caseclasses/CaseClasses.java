package com.github.alexmojaki.caseclasses;

import java.util.*;

/**
 * This is where most of the utilities of the library live, as simple static methods.
 */
public enum CaseClasses {
    ;

    /**
     * Return the value from given {@code CaseClass} with the given name. If there is no such value,
     * returns {@code null}. If there is more than one, returns the last
     * one, i.e. the value in the most recent call to {@code add}.
     */
    public static Object getValueByName(CaseClass obj, String name) {
        return GetByNameBuilder.getValueByName(obj, name);
    }

    /**
     * Return a {@code hashCode} constructed from the values of the {@code CaseClass}.
     * Use this along with {@link CaseClasses#equals(CaseClass, Object)}.
     */
    public static int hashCode(CaseClass obj) {
        return HashCodeBuilder.hash(obj);
    }

    /**
     * Compare the arguments for equality. Return {@code true} if and only if
     * the arguments have compatible types (see below) and they consist
     * of equal sequences of names and values, in the same order.
     * <p>
     * By default the types of the arguments are compatible if and only if they
     * are equal, i.e. {@code o1.getClass() == o2.getClass()}. However if both
     * arguments implement {@link FlexiblyEqual}, then the types are compatible
     * if each is a subtype of the class returned by the other's {@link FlexiblyEqual#equalsBaseClass()}.
     * This is analogous to a traditional implementation of {@code equals} using
     * an {@code instanceof} check.
     * <p>
     * Use this along with {@link CaseClasses#hashCode(CaseClass)}.
     */
    public static boolean equals(CaseClass o1, Object o2) {
        return EqualsBuilder.equals(o1, o2);
    }

    /**
     * Return a string representation of the given {@code CaseClass},
     * including the simple class name and the names and corresponding values.
     */
    public static String toString(CaseClass obj) {
        return ToStringBuilder.toString(obj);
    }

    /**
     * Add the values of the given {@code CaseClass} to the given collection.
     * Throws a {@code ClassCastException} if any of the values are not instances
     * of the given type.
     *
     * @see CaseClasses#addValues(CaseClass, Collection)
     * @see CaseClasses#getValuesList(CaseClass, Class)
     */
    public static <T extends Collection<E>, E> void addValues(CaseClass obj, T collection, Class<E> type) {
        CollectionBuilder.addValues(obj, collection, type);
    }

    /**
     * Add the values of the given {@code CaseClass} to the given collection,
     * without checking types.
     *
     * @see CaseClasses#addValues(CaseClass, Collection, Class)
     * @see CaseClasses#getValuesList(CaseClass)
     */
    public static <T extends Collection> void addValues(CaseClass obj, T collection) {
        CollectionBuilder.addValues(obj, collection);
    }

    /**
     * Return a list of the values in the given {@code CaseClass}.
     * Throws a {@code ClassCastException} if any of the values are not instances
     * of the given type.
     *
     * @see CaseClasses#getValuesList(CaseClass)
     * @see CaseClasses#addValues(CaseClass, Collection, Class)
     */
    public static <E> List<E> getValuesList(CaseClass obj, Class<E> type) {
        List<E> values = new ArrayList<E>();
        addValues(obj, values, type);
        return values;
    }

    /**
     * Return a list of the values in the given {@code CaseClass}.
     * without checking types.
     *
     * @see CaseClasses#getValuesList(CaseClass, Class)
     * @see CaseClasses#addValues(CaseClass, Collection)
     */
    public static List getValuesList(CaseClass obj) {
        return getValuesList(obj, Object.class);
    }

    /**
     * Return a list of the names in the given {@code CaseClass}.
     *
     * @see CaseClasses#getValuesList(CaseClass)
     */
    public static List<String> getNameList(CaseClass obj) {
        return NamesBuilder.getNameList(obj);
    }

    /**
     * Put each component of the given {@code CaseClass} into the given
     * {@code Map}, using the names as keys.
     * Throws a {@code ClassCastException} if any of the values are not instances
     * of the given type.
     *
     * @see CaseClasses#putValues(CaseClass, Map)
     * @see CaseClasses#toMap(CaseClass, Class)
     */
    public static <T extends Map<String, V>, V> void putValues(CaseClass obj, T map, Class<V> type) {
        MapBuilder.putValues(obj, map, type);
    }

    /**
     * Put each component of the given {@code CaseClass} into the given
     * {@code Map}, using the names as keys, without checking types.
     *
     * @see CaseClasses#putValues(CaseClass, Map, Class)
     * @see CaseClasses#toMap(CaseClass)
     */
    public static <T extends Map> void putValues(CaseClass obj, T map) {
        MapBuilder.putValues(obj, map);
    }

    /**
     * Return a {@code Map} whose entries are the components of the given {@code CaseClass},
     * using the names as keys.
     * Throws a {@code ClassCastException} if any of the values are not instances
     * of the given type.
     *
     * @see CaseClasses#toMap(CaseClass)
     * @see CaseClasses#putValues(CaseClass, Map, Class)
     */
    public static <V> Map<String, V> toMap(CaseClass obj, Class<V> type) {
        Map<String, V> map = new HashMap<String, V>();
        putValues(obj, map, type);
        return map;
    }

    /**
     * Return a {@code Map} whose entries are the components of the given {@code CaseClass},
     * using the names as keys, without checking types.
     *
     * @see CaseClasses#toMap(CaseClass, Class)
     * @see CaseClasses#putValues(CaseClass, Map)
     */
    public static Map<String, Object> toMap(CaseClass obj) {
        return toMap(obj, Object.class);
    }

    /**
     * Return a human readable table representing the given {@code CaseClass}es
     * suitable for console output. The column headers are the names of the objects
     * and each row is one object.
     * 
     * @throws IllegalArgumentException if the names of each object don't exactly match.
     */
    public static String getPrettyTable(Iterable<? extends CaseClass> objects) {
        return PrettyTableBuilder.getPrettyTable(objects);
    }

    private static class MapCaseClass extends AbstractCaseClass {
        private Map<String, ?> map;

        @Override
        public void buildResult(ResultBuilder builder) {
            for (Map.Entry<String, ?> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Return a {@code CaseClass} where the components are the entries of the given
     * {@code Map}.
     */
    public static CaseClass toCaseClass(final Map<String, ?> map) {
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

    /**
     * Return a {@code CaseClass} where the values are from the given
     * {@code Iterable}, and the names are the indices. Beware of using
     * this if the iteration order is unpredictable.
     */
    public static CaseClass toCaseClass(final Iterable<?> iterable) {
        IterableCaseClass result = new IterableCaseClass();
        result.iterable = iterable;
        return result;
    }

    /**
     * Throw an {@code AssertionError} if the arguments are not equal,
     * with a message explaining why not, assuming that {@code Object.equals}
     * has been implemented using {@link CaseClasses#equals(CaseClass, Object)}.
     */
    public static void assertEquals(CaseClass expected, CaseClass actual) {
        DiffBuilder.assertEquals(expected, actual);
    }

    /**
     * Return a message explaining why the arguments are not equal,
     * assuming that {@code Object.equals}
     * has been implemented using {@link CaseClasses#equals(CaseClass, Object)}.
     * Note that this will not check if the arguments are equal.
     */
    public static String getDifferenceReport(CaseClass expected, CaseClass actual) {
        return DiffBuilder.getDifferenceReport(expected, actual);
    }

    /**
     * This comparator orders the arguments lexicographically. That is,
     * the arguments are ordered according to the first value, and if they're
     * equal then the second value, and so on. The values must be an instance of
     * {@code Comparable} or {@code CaseClass} (in which case the comparator
     * is applied recursively).
     * A {@code ClassCastException} is thrown if one of the values is neither
     * of these, or if the sequences of names do not match exactly.
     */
    public static final Comparator<CaseClass> COMPARATOR = new Comparator<CaseClass>() {
        @Override
        public int compare(CaseClass o1, CaseClass o2) {
            return ComparisonBuilder.compare(o1, o2);
        }
    };

}
