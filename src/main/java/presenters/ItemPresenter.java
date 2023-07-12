package presenters;

public interface ItemPresenter<T, R> {
    R present(T item);
}
