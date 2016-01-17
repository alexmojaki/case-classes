package com.github.alexmojaki.caseclasses.tests;

import com.github.alexmojaki.caseclasses.CaseClass;
import com.github.alexmojaki.caseclasses.SimpleCaseClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayTest {

    private static CaseClass obj() {
        return new SimpleCaseClass(
                "bytes", new byte[]{1, 2, 3},
                "shorts", new short[]{1, 2, 3},
                "ints", new int[]{1, 2, 3},
                "longs", new long[]{1, 2, 3},
                "floats", new float[]{1, 2, 3},
                "doubles", new double[]{1, 2, 3},
                "booleans", new boolean[]{true, false, true},
                "chars", new char[]{'1', '2', '3'}).add(
                "2D_ints", new int[][]{new int[]{1, 2, 3}, new int[]{4, 5, 6}, new int[]{7, 8, 9}},
                "3D_ints", new int[][][]{new int[][]{new int[]{1, 2, 3}, new int[]{4, 5, 6}, new int[]{7, 8, 9}}, null},
                "2D_Strings", new String[][]{new String[]{"1", "2", "3"}, new String[]{"4", "5", "6"}, new String[]{"7", "8", "9"}}
        );
    }

    @Test
    public void testToString() {
        assertEquals("SimpleCaseClass(bytes = [1, 2, 3], shorts = [1, 2, 3], ints = [1, 2, 3], longs = [1, 2, 3], floats = [1.0, 2.0, 3.0], doubles = [1.0, 2.0, 3.0], booleans = [true, false, true], chars = [1, 2, 3], 2D_ints = [[1, 2, 3], [4, 5, 6], [7, 8, 9]], 3D_ints = [[[1, 2, 3], [4, 5, 6], [7, 8, 9]], null], 2D_Strings = [[1, 2, 3], [4, 5, 6], [7, 8, 9]])", obj().toString());
    }

    @Test
    public void testEquals() {

    }
}
