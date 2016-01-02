import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class CaseClassTest {

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

        public boolean buildResult(ResultBuilder r) {
            return r.name("a") && r.value(a) || r.name("b") && r.value(b) || r.name("c") && r.value(c);
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
        assertEquals(new HashMap<String, Integer>() {
            {
                put("a", 1);
                put("b", 2);
                put("c", 3);
            }
        }, map);
    }
}
