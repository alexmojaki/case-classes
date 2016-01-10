import java.util.ArrayList;
import java.util.List;

public class SimpleCaseClass extends AbstractCaseClass {

    private List<String> names = new ArrayList<String>();
    private List<Object> values = new ArrayList<Object>();

    public SimpleCaseClass add(String name, Object value) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        names.add(name);
        values.add(value);
        return this;
    }

    @Override
    public void buildResult(ResultBuilder builder) {
        int size = names.size();
        for (int i = 0; i < size; i++) {
            builder.add(names.get(i), values.get(i));
        }
    }
}
