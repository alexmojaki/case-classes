import com.github.alexmojaki.caseclasses.AbstractCaseClass;
import com.github.alexmojaki.caseclasses.CaseClasses;
import com.github.alexmojaki.caseclasses.ResultBuilder;
import com.github.alexmojaki.caseclasses.SimpleCaseClass;
import com.github.alexmojaki.caseclasses.serialization.CSVWriter;

import java.io.StringWriter;
import java.util.*;

import static com.github.alexmojaki.caseclasses.CaseClasses.*;

public class Demo {

    public static void main(String[] args) {
        Employee richJane = new Employee("Jane", "Doe", 50000);
        Employee poorJane = new Employee("Jane", "Doe", 1000);
        Employee john = new Employee("John", "Smith", 2000);
        Employee johnCopy = new Employee("John", "Smith", 2000);
        Employee bob = new Employee("Bob", "Builder", 1000000);

        // toString is implemented
        println(john);
        // Employee(firstName = John, lastName = Smith, salary = 2000)

        // equals and hashCode are implemented
        println(john.equals(bob));  // false
        println(john.hashCode() == bob.hashCode());  // false
        println(john.equals(johnCopy));  // true
        println(john.hashCode() == johnCopy.hashCode());  // true

        List<Employee> employees = Arrays.asList(richJane, poorJane, john, johnCopy, bob);

        // Collections can be printed out nicely
        println(getPrettyTable(employees));
        /*
        +-----------+----------+---------+
        | firstName | lastName | salary  |
        +-----------+----------+---------+
        | Jane      | Doe      |   50000 |
        | Jane      | Doe      |    1000 |
        | John      | Smith    |    2000 |
        | John      | Smith    |    2000 |
        | Bob       | Builder  | 1000000 |
        +-----------+----------+---------+
         */

        // CaseClasses can easily be compared lexicographically,
        // i.e. by firstName, then lastName, then salary
        employees.sort(CaseClasses.COMPARATOR);
        println(getPrettyTable(employees));
        /*
        +-----------+----------+---------+
        | firstName | lastName | salary  |
        +-----------+----------+---------+
        | Bob       | Builder  | 1000000 |
        | Jane      | Doe      |    1000 |
        | Jane      | Doe      |   50000 |
        | John      | Smith    |    2000 |
        | John      | Smith    |    2000 |
        +-----------+----------+---------+
         */

        // Just to reiterate equals and hashCode: a HashSet works nicely
        println(getPrettyTable(new HashSet<Employee>(employees)));
        /*
        +-----------+----------+---------+
        | firstName | lastName | salary  |
        +-----------+----------+---------+
        | John      | Smith    |    2000 |
        | Jane      | Doe      |   50000 |
        | Bob       | Builder  | 1000000 |
        | Jane      | Doe      |    1000 |
        +-----------+----------+---------+
         */

        // Collections and maps can easily be constructed
        List<String> names = getNameList(john);
        println(names);
        // [firstName, lastName, salary]
        List values = getValuesList(john);
        println(values);
        // [John, Smith, 2000]
        Map<String, Object> map = toMap(john);
        println(map);
        // {firstName=John, lastName=Smith, salary=2000}
        
        // Extracting single values is easy
        println(getValueByName(john, "salary"));  // 2000
        
        // Values can be compared to determine why they are not equal
        println(getDifferenceReport(poorJane, richJane));
        /*
        Differences:

        +--------+-------------+--------------+
        | Name   | First value | Second value |
        +--------+-------------+--------------+
        | salary |        1000 |        50000 |
        +--------+-------------+--------------+

        Matching values:

        +-----------+-------+
        | Name      | Value |
        +-----------+-------+
        | firstName | Jane  |
        | lastName  | Doe   |
        +-----------+-------+
         */
        // (alternatively assertEquals will throw an exception with a similar message)

        // CSV files can be quickly constructed
        StringWriter stringWriter = new StringWriter();
        new CSVWriter(stringWriter).write(employees);
        println(stringWriter);
        /*
        firstName,lastName,salary
        Jane,Doe,50000
        Jane,Doe,1000
        John,Smith,2000
        John,Smith,2000
        Bob,Builder,1000000
        */
        // (the CSVWriter is highly configurable - this is just a basic demo)

        // CaseClasses are easy to construct from a map:
        Map<String, Object> michael = new HashMap<String, Object>();
        michael.put("firstName", "Anony");
        michael.put("lastName", "Mous");
        michael.put("salary", 50);
        println(toCaseClass(michael));
        // MapCaseClass(firstName = Anony, lastName = Mous, salary = 50)

        // or from any iterable:
        println(toCaseClass(michael.values()));
        // IterableCaseClass(0 = Anony, 1 = Mous, 2 = 50)

        // They can also be constructed directly very easily:
        println(new SimpleCaseClass("firstName", "Bill", "lastName", "Gates", "salary", 999999999));
        // SimpleCaseClass(firstName = Bill, lastName = Gates, salary = 999999999)
    }

    private static void println(Object o) {
        System.out.println(o);
    }

}

class Employee extends AbstractCaseClass {
    private String firstName;
    private String lastName;
    private int salary;

    public Employee(String firstName, String lastName, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    @Override
    public void buildResult(ResultBuilder builder) {
        builder.add("firstName", firstName)
                .add("lastName", lastName)
                .add("salary", salary);
    }

}