package com.github.alexmojaki.caseclasses.tests;

import com.github.alexmojaki.caseclasses.SimpleCaseClass;
import org.junit.Assert;
import org.junit.Test;

public class SimpleCaseClassTest {
    @Test
    public void testConstructors() {
        Assert.assertEquals("SimpleCaseClass()", new SimpleCaseClass().toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1)", new SimpleCaseClass("1", 1).toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1, 2 = 2)", new SimpleCaseClass("1", 1, "2", 2).toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3)", new SimpleCaseClass("1", 1, "2", 2, "3", 3).toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4).toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5).toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6).toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6, 7 = 7)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6, "7", 7).toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6, 7 = 7, 8 = 8)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6, "7", 7, "8", 8).toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6, 7 = 7, 8 = 8, 9 = 9)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6, "7", 7, "8", 8, "9", 9).toString());
        Assert.assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6, 7 = 7, 8 = 8, 9 = 9, 10 = 10)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6, "7", 7, "8", 8, "9", 9, "10", 10).toString());
    }
}
