package presenters;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IteratorPresenter<T> implements ItemPresenter<List<T>, String> {

    public String present(List<T> items) {
        return Stream.of(items)
                .map(Object::toString)
                .collect(Collectors.joining(", "))
                ;
    }
}
