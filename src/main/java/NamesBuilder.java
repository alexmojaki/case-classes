import java.util.Collection;

class NamesBuilder extends AbstractResultBuilder {

    private Collection<String> collection;

    @SuppressWarnings("unchecked")
    static <T extends Collection<String>> void addNames(CaseClass obj, T collection) {
        NamesBuilder builder = new NamesBuilder();
        builder.collection = collection;
        obj.buildResult(builder);
    }

    @Override
    public boolean name(String name) {
        collection.add(name);
        return false;
    }

}