import java.util.Collection;

class CollectionBuilder<E> extends AbstractResultBuilder {

    private Collection<E> collection;
    private Class<E> type;

    @SuppressWarnings("unchecked")
    static <T extends Collection<E>, E> void addValues(CaseClass obj, T collection, Class<E> type) {
        CollectionBuilder builder = new CollectionBuilder();
        builder.collection = collection;
        builder.type = type;
        obj.buildResult(builder);
    }

    static <T extends Collection> void addValues(CaseClass obj, T collection) {
        addValues(obj, collection, Object.class);
    }

    @Override
    public void simpleAdd(String name, Object value) {
        collection.add(type.cast(value));
    }
}
