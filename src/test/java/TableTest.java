import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class TableTest {

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
        public boolean buildResult(ResultBuilder r) {
            return r.name("First name") && r.value(firstName) ||
                    r.name("Last name") && r.value(lastName) ||
                    r.name("Age") && r.value(age);
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
        String actual = CaseClasses.makeTable(Arrays.asList(
                new Person("John", "Doe", 23),
                new Person("Arnold", "Schwarzenegger", 68),
                new Person(null, null, null)
        ));
        assertEquals(expected, actual);
    }

}