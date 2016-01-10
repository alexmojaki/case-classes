import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleCaseClassTest {
    @Test
    public void testConstructors() {
        assertEquals("SimpleCaseClass()", new SimpleCaseClass().toString());
        assertEquals("SimpleCaseClass(1 = 1)", new SimpleCaseClass("1", 1).toString());
        assertEquals("SimpleCaseClass(1 = 1, 2 = 2)", new SimpleCaseClass("1", 1, "2", 2).toString());
        assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3)", new SimpleCaseClass("1", 1, "2", 2, "3", 3).toString());
        assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4).toString());
        assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5).toString());
        assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6).toString());
        assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6, 7 = 7)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6, "7", 7).toString());
        assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6, 7 = 7, 8 = 8)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6, "7", 7, "8", 8).toString());
        assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6, 7 = 7, 8 = 8, 9 = 9)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6, "7", 7, "8", 8, "9", 9).toString());
        assertEquals("SimpleCaseClass(1 = 1, 2 = 2, 3 = 3, 4 = 4, 5 = 5, 6 = 6, 7 = 7, 8 = 8, 9 = 9, 10 = 10)", new SimpleCaseClass("1", 1, "2", 2, "3", 3, "4", 4, "5", 5, "6", 6, "7", 7, "8", 8, "9", 9, "10", 10).toString());
    }
}
