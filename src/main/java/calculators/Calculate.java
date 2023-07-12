package calculators;

import models.Card;

public interface Calculate<T, R> {
    R  calculate(T cards);
}
