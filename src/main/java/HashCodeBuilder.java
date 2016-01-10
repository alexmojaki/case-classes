import java.util.Objects;

class HashCodeBuilder extends AbstractResultBuilder {

    static int hash(CaseClass obj) {
        HashCodeBuilder builder = new HashCodeBuilder();
        obj.buildResult(builder);
        return builder.result;
    }

    private int result = 17;

    @Override
    protected void simpleAdd(String name, Object value) {
        result = 31 * result + Objects.hashCode(value);
    }
}
