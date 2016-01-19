import com.github.alexmojaki.caseclasses.AbstractCaseClass;
import com.github.alexmojaki.caseclasses.FlexiblyEqual;
import com.github.alexmojaki.caseclasses.ResultBuilder;

import java.util.Objects;


public class Equals {
    public static void main(String[] args) {
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

        SpecialPoint specialPoint = new SpecialPoint(1, 2, "password");
        System.out.println(point.equals(specialPoint));
        System.out.println(specialPoint.equals(point));
        // both false
    }
}

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