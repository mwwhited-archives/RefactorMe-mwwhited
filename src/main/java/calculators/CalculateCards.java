package calculators;

import models.Card;
import models.Values;

public class CalculateCards implements Calculate<Iterable<Card>, Integer> {
    public Integer calculate(Iterable<Card> cards){

            int total = 0;
            boolean aceFlag = false;

            for (var card : cards) {
                total += card.getValue().getValue();
                if (card.getValue() == Values.ACE){
                    aceFlag = true;
                }
            }

            if (aceFlag && total + 10 <= 21) {
                total += 10;
            }

            return total;
    }
}
