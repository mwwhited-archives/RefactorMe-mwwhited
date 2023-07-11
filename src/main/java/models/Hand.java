package models;

import presenters.IteratorPresenter;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> hand = new ArrayList<>();

    // Calculates the total of a hand and also decides whether ace is 1 or 11
    public int getTotal() {
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

    public int count() {
        return hand.size();
    }
    public void add(Card card) {
        hand.add(card);
    }
    public void clear() {
        hand.clear();
    }
}