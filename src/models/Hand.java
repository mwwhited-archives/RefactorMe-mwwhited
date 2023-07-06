package models;

import presenters.IteratorPresenter;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> hand = new ArrayList<>();

    // Calculates the total of a hand and also decides whether ace is 1 or 11
    public int calculateTotal() {
        int total = 0;
        boolean aceFlag = false;

        for (Card card : hand) {
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

    public String toString() {
        return IteratorPresenter.toString(hand);
    }

//    public String toString(boolean isDealer, boolean hideHoleCard) {
//        String str;
//        int total = 0;
//        boolean aceFlag = false;
//        String aceString = "";
//        StringBuilder strBuilder = new StringBuilder();
//        for (int i = 0; i < numberOfCards; i++) {
//            if (isDealer && hideHoleCard && i == 0) {
//                strBuilder = new StringBuilder(" Showing");
//            } else {
//                int value = theHand[i].getValue();
//                String valueName;
//                if (value > 10) {
//                    valueName = theHand[i].getValueName().substring(0, 1);
//                } else if (value == 1) {
//                    valueName = "A";
//                } else {
//                    valueName = Integer.toString(value);
//                }
//                strBuilder.append(" ").append(valueName).append(theHand[i].getSuit());
//                if (value > 10) {
//                    value = 10;
//                } else if (value == 1) {
//                    aceFlag = true;
//                }
//                total += value;
//            }
//        }
//        str = strBuilder.toString();
//        if (aceFlag && total + 10 <= 21) {
//            aceString = " or " + (total + 10);
//        }
//        if (hideHoleCard) {
//            return str;
//        } else {
//            return str + " totals " + total + aceString;
//        }
//
//    }

    public void addCard(Card card) { hand.add(card); }
    public void clearHand() { hand.clear(); }

//    public boolean dealerPeek() {
//        int value = theHand.get(0).getValue().getValue();
//        return value == 1 || value >= 10;
//    }
}