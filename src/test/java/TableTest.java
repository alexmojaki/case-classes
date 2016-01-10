import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TableTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private class Person extends AbstractCaseClass {

        String firstName;
        String lastName;
        Integer age;

        Person(String firstName, String lastName, Integer age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        @Override
        public void buildResult(ResultBuilder builder) {
            builder.add("First name", firstName).add("Last name", lastName).add("Age", age);
        }
    }

    @Test
    public void testTable() {
        String expected =
                "+------------+----------------+------+\n" +
                        "| First name | Last name      | Age  |\n" +
                        "+------------+----------------+------+\n" +
                        "| John       | Doe            |   23 |\n" +
                        "| Arnold     | Schwarzenegger |   68 |\n" +
                        "| null       | null           | null |\n" +
                        "+------------+----------------+------+\n";
        String actual = CaseClasses.getTable(Arrays.asList(
                new Person("John", "Doe", 23),
                new Person("Arnold", "Schwarzenegger", 68),
                new Person(null, null, null)
        ));
        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyTable() {
        List<CaseClass> emptyList = Collections.emptyList();
        assertEquals("[Empty table]", CaseClasses.getTable(emptyList));
    }

    @Test
    public void testMismatchedNames() {
        exception.expect(IllegalArgumentException.class);
        CaseClasses.getTable(Arrays.asList(new SimpleCaseClass("a", 1), new SimpleCaseClass("b", 1)));
    }

    @Test
    public void testSingleUseIterable() {
        final Iterator<SimpleCaseClass> iterator = Arrays.asList(new SimpleCaseClass("a", 1), new SimpleCaseClass("a", 2)).iterator();
        Iterable<SimpleCaseClass> iterable = new Iterable<SimpleCaseClass>() {

            @Override
            public Iterator<SimpleCaseClass> iterator() {
                return iterator;
            }
        };
        assertEquals(
                "+---+\n" +
                        "| a |\n" +
                        "+---+\n" +
                        "| 1 |\n" +
                        "| 2 |\n" +
                        "+---+\n", CaseClasses.getTable(iterable));
    }


}
