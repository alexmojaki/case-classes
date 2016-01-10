import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
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
        assertEquals("X(a = 1, b = 2, c = 3)", x.toString());
    }

    @Test
    public void testGetByName() {
        assertEquals(x.a, CaseClasses.getValueByName(x, "a"));
        assertEquals(x.b, CaseClasses.getValueByName(x, "b"));
        assertEquals(x.c, CaseClasses.getValueByName(x, "c"));
    }

    @Test
    public void testCollection() {
        List<Integer> list = CaseClasses.values(x, Integer.class);
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
        assertEquals(map, CaseClasses.toMap(x));
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
        CaseClass caseClass1 = CaseClasses.toCaseClass(map);
        CaseClass caseClass2 = CaseClasses.toCaseClass(treeMap);
        assertEquals(caseClass1, caseClass2);
        String expectedString = "MapCaseClass(a = 1, b = 2, c = 3)";
        assertEquals(expectedString, caseClass1.toString());
        assertEquals(expectedString, caseClass2.toString());
    }

    @Test
    public void testIterableToCaseClass() {
        assertEquals("IterableCaseClass(0 = a, 1 = b, 2 = c)",
                CaseClasses.toCaseClass(Arrays.asList("a", "b", "c")).toString());
    }

    @Test
    public void testValues() {
        assertEquals(Arrays.asList(1, 2, 3), CaseClasses.values(x));
    }

    @Test
    public void testNoNullNameInAbstractResultBuilder() {
        exception.expect(IllegalArgumentException.class);
        //noinspection ResultOfMethodCallIgnored
        new AbstractCaseClass() {

            @Override
            public void buildResult(ResultBuilder builder) {
                builder.add(null, 1);
            }
        }.hashCode();
    }

    @Test
    public void testNoNullNameInSimpleCaseClass() {
        exception.expect(IllegalArgumentException.class);
        new SimpleCaseClass(null, 1);
    }

}
