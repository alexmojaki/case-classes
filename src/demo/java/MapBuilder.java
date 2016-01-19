import com.github.alexmojaki.caseclasses.AbstractResultBuilder;
import com.github.alexmojaki.caseclasses.CaseClass;
import com.github.alexmojaki.caseclasses.SimpleCaseClass;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder extends AbstractResultBuilder {

    private Map<String, Object> map = new HashMap<String, Object>();

    public static Map<String, Object> toMap(CaseClass caseClass) {
        MapBuilder builder = new MapBuilder();
        caseClass.buildResult(builder);
        return builder.map;
    }

    @Override
    protected void simpleAdd(String name, Object value) {
        map.put(name, value);
    }

    public static void main(String[] args) {
        System.out.println(toMap(new SimpleCaseClass("a", 1, "b", 2)));
    }

}
