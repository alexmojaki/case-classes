package com.github.alexmojaki.caseclasses.tests;

import com.github.alexmojaki.caseclasses.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class CaseClassTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private X x;

    private class X extends AbstractCaseClass {
        private int a;
        private int b;
        private int c;

        private X(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public void buildResult(ResultBuilder builder) {
            builder.add("a", a).add("b", b).add("c", c);
        }

    }

    @Before
    public void setUp() {
        x = new X(1, 2, 3);
    }

    @Test
    public void testEquality() {
        Utils.assertAllEqual(x, x, new X(1, 2, 3));
        Utils.assertAllEqual(new X(4, 5, 6), new X(4, 5, 6));
        Utils.assertUnequal(x, new X(1, 2, 4), new X(1, 4, 3), new X(4, 2, 3));
    }

    @Test
    public void testToString() {
        TestCase.assertEquals("X(a = 1, b = 2, c = 3)", x.toString());
    }

    @Test
    public void testGetByName() {
        TestCase.assertEquals(x.a, CaseClasses.getValueByName(x, "a"));
        TestCase.assertEquals(x.b, CaseClasses.getValueByName(x, "b"));
        TestCase.assertEquals(x.c, CaseClasses.getValueByName(x, "c"));
    }

    @Test
    public void testCollection() {
        List<Integer> list = CaseClasses.getValuesList(x, Integer.class);
        assertEquals(Arrays.asList(1, 2, 3), list);
        Set<Integer> set = new HashSet<Integer>();
        CaseClasses.addValues(x, set, Integer.class);
        CaseClasses.addValues(x, set);
        assertEquals(new TreeSet<Integer>(list), set);

        List<String> names = CaseClasses.getNameList(x);
        assertEquals(Arrays.asList("a", "b", "c"), names);
        for (String name : names) {
            Integer value = (Integer) CaseClasses.getValueByName(x, name);
            assertTrue(set.contains(value));
        }
    }

    @Test
    public void testMap() {
        Map<String, Integer> map = CaseClasses.toMap(x, Integer.class);
        TestCase.assertEquals(map, CaseClasses.toMap(x));
        assertEquals(new HashMap<String, Integer>() {
            {
                put("a", 1);
                put("b", 2);
                put("c", 3);
            }
        }, map);
        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
        CaseClasses.putValues(x, treeMap, Integer.class);
        CaseClasses.putValues(x, treeMap);
        assertEquals(map, treeMap);
        CaseClass treeMapCaseClass = CaseClasses.toCaseClass(treeMap);
        TestCase.assertEquals("MapCaseClass(a = 1, b = 2, c = 3)", treeMapCaseClass.toString());
    }

    @Test
    public void testIterableToCaseClass() {
        TestCase.assertEquals("IterableCaseClass(0 = a, 1 = b, 2 = c)",
                CaseClasses.toCaseClass(Arrays.asList("a", "b", "c")).toString());
    }

    @Test
    public void testValues() {
        TestCase.assertEquals(Arrays.asList(1, 2, 3), CaseClasses.getValuesList(x));
    }

    @Test
    public void testNoNullNameInSimpleCaseClass() {
        exception.expect(IllegalArgumentException.class);
        new SimpleCaseClass(null, "");
    }


    @Test
    public void testHashCodePrimitives() {
        Object[] objects = {true, false, 1, 2, 1L, 2L, 1f, 2f, 1.0, 2.0, (byte) 1, (byte) 2, (short) 1, (short) 2, "1", "2", '1', '2', null};
        AbstractCaseClass caseClass = new AbstractCaseClass() {

            @Override
            public void buildResult(ResultBuilder builder) {
                builder.add("", true).add("", false).add("", 1).add("", 2).add("", 1L).add("", 2L).add("", 1f).add("", 2f).add("", 1.0).add("", 2.0).add("", (byte) 1).add("", (byte) 2).add("", (short) 1).add("", (short) 2).add("", "1").add("", "2").add("", '1').add("", '2').add("", null);
            }
        };
        TestCase.assertEquals(Objects.hash(objects), caseClass.hashCode());
        TestCase.assertEquals(Arrays.asList(objects), CaseClasses.getValuesList(caseClass));
    }

    @Test
    public void testNulls() {
        assertFalse(CaseClasses.equals(null, new SimpleCaseClass()));
        assertEquals(0, CaseClasses.hashCode(null));
        assertEquals("null", CaseClasses.toString(null));
    }
}
