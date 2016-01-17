package com.github.alexmojaki.caseclasses.tests;

import com.github.alexmojaki.caseclasses.CaseClass;
import com.github.alexmojaki.caseclasses.CaseClasses;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class Utils {

    public static void assertAllEqual(Object... objects) {
        assertHashEqual(objects);
        for (Object o1 : objects) {
            for (Object o2 : objects) {
                assertEquals(o1, o2);
                assertTrue(o1.toString().equals(o2.toString()) || o1.getClass() != o2.getClass());
            }
        }
    }

    public static void assertHashEqual(Object... objects) {
        for (Object o1 : objects) {
            for (Object o2 : objects) {
                assertEquals(o1.hashCode(), o2.hashCode());
            }
        }
    }

    public static void assertUnequal(Object... objects) {
        for (Object o1 : objects) {
            for (Object o2 : objects) {
                if (o1 == o2) {
                    continue;
                }
                assertNotEquals(o1, o2);
            }
        }
    }

    public static void assertEquivalenceClasses(List<?>... classes) {
        for (int i = 0; i < classes.length; i++) {
            List<?> class1 = classes[i];
            assertAllEqual(class1.toArray());
            for (int j = i + 1; j < classes.length; j++) {
                List<?> class2 = classes[j];
                for (Object o1 : class1) {
                    for (Object o2 : class2) {
                        assertNotEquals(o1, o2);
                        assertNotEquals(o2, o1);
                    }
                }
            }
        }
    }

    @Test
    public void equivalenceClasses() {
        assertEquivalenceClasses(
                singletonList(1),
                asList(2, 2),
                asList(3, 3, 3));
    }

    private static void assertNotEquivalenceClasses(List<?>... classes) {
        try {
            assertEquivalenceClasses(classes);
        } catch (AssertionError e) {
            return;
        }
        fail();
    }

    @Test
    public void notAnEquivalenceClassIfNotAllEqual() {
        assertNotEquivalenceClasses(
                singletonList(1),
                asList(4, 2),
                asList(3, 3, 3));
    }

    @Test
    public void equivalenceClassesCantOverlap() {
        assertNotEquivalenceClasses(
                singletonList(1),
                asList(2, 2),
                asList(2, 2),
                asList(3, 3, 3));
    }

    public static int compare(CaseClass o1, CaseClass o2) {
        return CaseClasses.COMPARATOR.compare(o1, o2);
    }

}
