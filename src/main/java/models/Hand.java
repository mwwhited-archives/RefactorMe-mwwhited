package models;

import presenters.IteratorPresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Hand implements Iterable<Card> {

    private final List<Card> hand = new ArrayList<>();
    private final IteratorPresenter iteratorPresenter = new IteratorPresenter();


    public String toString() {
        return iteratorPresenter.present(hand);
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

    @Override
    public Iterator<Card> iterator() {
        return hand.iterator();
    }
}