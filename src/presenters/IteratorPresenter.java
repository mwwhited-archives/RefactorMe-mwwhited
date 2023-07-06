package presenters;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class IteratorPresenter {
    public static <T> String toString(List<T> items) {
        return Stream.of(items)
                .map(Object::toString)
                .collect(Collectors.joining(", "))
                ;
    }
}
