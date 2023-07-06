package presenters;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class IteratorPresenter {

    public static <T> String toString(List<T> items) {
        return Stream.of(items)
                .map(i -> i.toString())
                .collect(Collectors.joining(", "))
                ;
    }

    public static <T> String toString(Iterator<T> items) {
        return Stream.of(items)
                .map(i -> i.toString())
                .collect(Collectors.joining(", "))
                ;
    }

}
