package com.github.alexmojaki.caseclasses;

import com.google.common.primitives.*;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractResultBuilder implements ResultBuilder {

    protected abstract void simpleAdd(String name, Object value);

    protected abstract boolean convertArraysToLists();

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
        simpleAdd(name, convertArraysToLists() ? arrayToList(value) : value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, char value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, boolean value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, byte value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, short value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, int value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, long value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, float value) {
        simpleAdd(name, value);
        return this;
    }

    @Override
    public final ResultBuilder add(String name, double value) {
        simpleAdd(name, value);
        return this;
    }

    private static Object arrayToList(Object arr) {
        if (arr instanceof Object[]) {
            return new DeepArrayList<Object>((Object[]) arr);
        } else if (arr instanceof int[]) {
            return Ints.asList((int[]) arr);
        } else if (arr instanceof short[]) {
            return Shorts.asList((short[]) arr);
        } else if (arr instanceof byte[]) {
            return Bytes.asList((byte[]) arr);
        } else if (arr instanceof long[]) {
            return Longs.asList((long[]) arr);
        } else if (arr instanceof float[]) {
            return Floats.asList((float[]) arr);
        } else if (arr instanceof double[]) {
            return Doubles.asList((double[]) arr);
        } else if (arr instanceof boolean[]) {
            return Booleans.asList((boolean[]) arr);
        } else if (arr instanceof char[]) {
            return Chars.asList((char[]) arr);
        } else {
            return arr;
        }
    }

    private static class DeepArrayList<E> extends AbstractList<E>
            implements RandomAccess, java.io.Serializable {
        private static final long serialVersionUID = -2762017481408945198L;
        private final E[] a;

        DeepArrayList(E[] array) {
            a = array;
        }

        @Override
        public int size() {
            return a.length;
        }

        @Override
        public Object[] toArray() {
            return a.clone();
        }

        @Override
        @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
        public <T> T[] toArray(T[] a) {
            int size = size();
            if (a.length < size)
                return Arrays.copyOf(this.a, size,
                        (Class<? extends T[]>) a.getClass());
            System.arraycopy(this.a, 0, a, 0, size);
            if (a.length > size)
                a[size] = null;
            return a;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E get(int index) {
            return (E) arrayToList(a[index]);
        }

        @Override
        public int indexOf(Object o) {
            E[] a = this.a;
            if (o == null) {
                for (int i = 0; i < a.length; i++)
                    if (a[i] == null)
                        return i;
            } else {
                for (int i = 0; i < a.length; i++)
                    if (o.equals(a[i]))
                        return i;
            }
            return -1;
        }

        @Override
        public boolean contains(Object o) {
            return indexOf(o) != -1;
        }

        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.spliterator(a, Spliterator.ORDERED);
        }

        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            for (E e : a) {
                action.accept(e);
            }
        }

        @Override
        public boolean equals(Object o) {
            return o == this || o instanceof DeepArrayList && Arrays.deepEquals(a, ((DeepArrayList) o).a);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(a);
        }

        @Override
        public String toString() {
            return Arrays.deepToString(a);
        }
    }

}
