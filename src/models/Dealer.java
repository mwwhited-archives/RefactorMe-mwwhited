package models;

import presenters.IteratorPresenter;

import java.util.Iterator;

public class Dealer extends Player {

    public Dealer(){
        super(-1, "models.Dealer!");
    }

    // This automates the dealer's play
    public void dealerPlay(Iterator<Card> cards) {
        System.out.println();

        while (hand.calculateTotal() <= 16) {
            System.out.println("models.Dealer has " + hand.calculateTotal() + " and hits");
            hand.addCard(cards.next());
            System.out.println("models.Dealer " + this.hand);
        }
        if (hand.calculateTotal() > 21) {
            System.out.println("models.Dealer busts. " + this.hand);
        } else {
            System.out.println("models.Dealer stands. " + this.hand);
        }
    }
}