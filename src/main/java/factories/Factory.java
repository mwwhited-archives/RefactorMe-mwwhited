package factories;

public interface Factory<TModel> {
    TModel create(String input);
}
