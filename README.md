# Case Classes

[![Join the chat at https://gitter.im/alexmojaki/case-classes](https://badges.gitter.im/alexmojaki/case-classes.svg)](https://gitter.im/alexmojaki/case-classes?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Build status](https://travis-ci.org/alexmojaki/case-classes.svg?branch=master)](https://travis-ci.org/alexmojaki/case-classes) [![Coverage Status](https://coveralls.io/repos/github/alexmojaki/case-classes/badge.svg?branch=master)](https://coveralls.io/github/alexmojaki/case-classes?branch=master)

* [Setup](#setup)
* [Writing your own utilities](#writing-your-own-utilities)
* [The equals method](#the-equals-method)
* [Javadocs](http://alexmojaki.github.io/case-classes/javadoc/apidocs/index.html?com/github/alexmojaki/caseclasses/package-summary.html)

## Introduction

This library provides a framework to elegantly refactor the pattern of computing a result from the essential values that one or more objects are composed of. This is best demonstrated by an example. Consider the class below:

```java
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
```

This is mostly your average POJO with private fields and a constructor. The special part is the `buildResult` method, from the `CaseClass` interface. It defines the components (named values) that an instance of `Employee` consists of by adding them to the passed `ResultBuilder`. A `ResultBuilder` accepts these components and can do pretty much anything with them: we shall soon see examples. Finally, note that the class `extends AbstractCaseClass`. This provides implementations for `equals`, `hashCode`, and `toString`. This is just to save a bit of standard boilerplate; if you want the implementations without extending a class you can easily copy paste them from the [source](https://github.com/alexmojaki/case-classes/blob/master/src/main/java/com/github/alexmojaki/caseclasses/AbstractCaseClass.java). Each implementation is just a single static method call. Now look at how much can be done straight out of the box:

```java
// other imports excluded
import static com.github.alexmojaki.caseclasses.CaseClasses.*;

public class Intro {

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
        println(john.equals(bob));
        // false
        println(john.hashCode() == bob.hashCode());
        // false
        println(john.equals(johnCopy));
        // true
        println(john.hashCode() == johnCopy.hashCode());
        // true

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
        println(getValueByName(john, "salary"));
        // 2000

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
        // (This is just a basic demo. The CSVWriter is highly configurable. See the javadocs for more)
    }

    private static void println(Object o) {
        System.out.println(o);
    }

}
```

Who says Java has to be verbose? Speaking of which, sometimes you may want to make use of these utilities without having to write an entire class. The `Employee` class definition above still looks awful - each field name is mentioned 6 times! Here are some other options:

```java
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
```

## Setup

For maven projects, in your pom.xml:

```xml
<dependency>
  <groupId>com.github.alexmojaki</groupId>
  <artifactId>case-classes</artifactId>
  <version>0.1.0</version>
</dependency>
```

## Writing your own utilities

It's very easy to write your own utilities using `ResultBuilder`s. Here is a simple implementation of the `CaseClasses.toMap` method:

```java
class MapBuilder extends AbstractResultBuilder {

    private Map<String, Object> map = new HashMap<String, Object>();

    static Map<String, Object> toMap(CaseClass caseClass) {
        MapBuilder builder = new MapBuilder();
        caseClass.buildResult(builder);
        return builder.map;
    }

    @Override
    protected void simpleAdd(String name, Object value) {
        map.put(name, value);
    }

}
```

Let's break this down. `AbstractResultBuilder` is a skeletal implementation of `ResultBuilder` that only requires you to implement one method: `simpleAdd`. The `name` and `value` parameters come from a `CaseClass` making calls like `builder.add("firstName", firstName)` in `CaseClass.buildResult`. These are put in the `map` field of the builder, which is retrieved from the builder after calling `caseClass.buildResult(builder)`.

Sometimes you want to build a result from two `CaseClass`es, pairing the value components that are at the same position. The `DualResultBuilder` class can help with this. See the [javadoc](http://alexmojaki.github.io/case-classes/javadoc/apidocs/com/github/alexmojaki/caseclasses/DualResultBuilder.html) for more details, and the source of some subclasses (e.g. [`EqualsBuilder`](https://github.com/alexmojaki/case-classes/blob/master/src/main/java/com/github/alexmojaki/caseclasses/EqualsBuilder.java)) for example implementations.

## The equals method

One utility in this library that deserves special mention is the [`CaseClasses.equals`](http://alexmojaki.github.io/case-classes/javadoc/apidocs/com/github/alexmojaki/caseclasses/CaseClasses.html#equals-com.github.alexmojaki.caseclasses.CaseClass-java.lang.Object-) method. This doesn't just save boilerplate - it solves an annoying problem in Java. According to Item 8 in *Effective Java* by Joshua Bloch:

> There is no way to extend an instantiable class and add a value component while preserving the equals contract, unless you are willing to forgo the benefits of object-oriented abstraction.

Case classes solve this problem quite simply and elegantly. Firstly, by default, `CaseClasses.equals(o1, o2)` requires that `o1.getClass() == o2.getClass()`. This means that instances of a subclass cannot be equal to instances of the parent class, so child classes can freely add value components. However, as the book points out, this violates the Liskov Substitution Principle (LSP) because now even instances of subclasses that don't add a value component cannot be equal to instances of the parent class, which is unacceptable. To allow instances of different classes to be considered equal, implement the `FlexiblyEqual` interface. Here is an example adapted from the book:

```java
class Point extends AbstractCaseClass implements FlexiblyEqual {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Class equalsBaseClass() {
        return Point.class;
    }

    @Override
    public void buildResult(ResultBuilder builder) {
        builder.add("x", x).add("y", y);
    }
}

class ColorPoint extends Point {
    private final String color;

    public ColorPoint(int x, int y, String color) {
        super(x, y);
        this.color = color;
    }

    @Override
    public void buildResult(ResultBuilder builder) {
        super.buildResult(builder);
        builder.add("color", color);
    }
}

class CounterPoint extends Point {
    public CounterPoint(int x, int y) {
        super(x, y);
    }
    // does other stuff
}
```

Here we have a superclass `Point` with two subclasses: the `ColorPoint` subclass which adds (literally) a value component 'color', and the `CounterPoint` subclass which has no additional value components. Note the `equalsBaseClass` implementation from the `FlexiblyEqual` interface. This means that given some `Point` instance `p`, `p.equals(other)` will require that `other` is an instance of the class returned by `equalsBaseClass`, i.e. `other instanceof Point`. It will not require that the classes are identical, so a `Point` and a `CounterPoint` can be equal. However a `Point` cannot equal a `ColorPoint` because the additional component 'color' will be detected. To summarise in code:

```java
Point point = new Point(1, 2);
ColorPoint colorPoint = new ColorPoint(1, 2, "red");
CounterPoint counterPoint = new CounterPoint(1, 2);

// Use equals in both directions to ensure symmetry
System.out.println(point.equals(colorPoint));
System.out.println(colorPoint.equals(point));
// both false

System.out.println(point.equals(counterPoint));
System.out.println(counterPoint.equals(point));
// both true
```

However this will not always be enough. Sometimes you will need a custom definition of `equals` that doesn't match the behaviour of `CaseClasses.equals`. For example, you may want to add a value component in the `equals` method but not in the `buildResult` method, because the latter exposes the value to the outside world (e.g. via the `CaseClasses.getValueByName` method) and you may want to keep it out of your interface. In this case you can simply refine `equalsBaseClass`. For example:

```java
class SpecialPoint extends Point {
    private final String secret;

    public SpecialPoint(int x, int y, String secret) {
        super(x, y);
        this.secret = secret;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && Objects.equals(secret, ((SpecialPoint) obj).secret);
    }

    @Override
    public Class equalsBaseClass() {
        return SpecialPoint.class;
    }
}
```

Here `SpecialPoint` specifies that it can only be equal to another `SpecialPoint`. This works because the `equalsBaseClass` is checked both ways: when evaluating `point.equals(specialPoint)` the code finds that `specialPoint instanceof Point` but not `point instanceof SpecialPoint`, so `false` is returned. To demonstrate:

```java
SpecialPoint specialPoint = new SpecialPoint(1, 2, "password");
System.out.println(point.equals(specialPoint));
System.out.println(specialPoint.equals(point));
// both false
```

(Incidentally, a similar trick could be done in `ColorPoint` to exit early when comparing a plain `Point` with a `ColorPoint` rather than letting it find that the components don't match)