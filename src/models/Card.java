package models;

public class Card {

    private final Suits suit;
    private final Values value;

    public Card(Suits suit, Values value) {
        this.value = value;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return value + " " + suit;
    }

    public Values getValue() {
        return this.value;
    }
}