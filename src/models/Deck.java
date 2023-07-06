package models;

import presenters.IteratorPresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Deck {
    private final List<Card> deck = new ArrayList<>();
    private static final Random random = new Random();

    public Deck() {
        for (Values value : Values.values()) {
            if (value == Values.UNKNOWN) continue;
            for (Suits suit : Suits.values()) {
                deck.add(new Card(suit, value));
            }
        }
    }

    public String toString() {
        return IteratorPresenter.toString(deck);
    }

    public void shuffle() {
        for (int index = deck.size(); index >= 0; index--) {
            var current = deck.get(index);
            var targetIndex = random.nextInt(index);
            var target = deck.get(targetIndex);

            deck.set(index, target);
            deck.set(targetIndex, current);
        }
    }

    public Iterator<Card> deal() {
        return deck.iterator();
    }
}