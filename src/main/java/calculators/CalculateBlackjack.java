package calculators;

import commands.PlayCommand;
import models.Card;
import models.Hand;

public class CalculateBlackjack implements  Calculate<Hand, Boolean> {
    private final  Calculate<Iterable<Card>, Integer> calculateCards = new CalculateCards();

    @Override
    public Boolean calculate(Hand hand) {
        return calculateCards.calculate(hand) == PlayCommand.BLACKJACK_VALUE && hand.count() == 2;
    }
}
