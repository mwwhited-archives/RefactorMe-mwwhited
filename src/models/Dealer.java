package models;

import java.util.Iterator;

public class Dealer extends Player {

    public Dealer(){
        super(-1, "models.Dealer!");
    }

    // This automates the dealer's play
    public void dealerPlay(Iterator<Card> cards) {
        System.out.println();

        while (hand.getTotal() <= 16) {
            System.out.println("Dealer has " + hand.getTotal() + " and hits");
            hand.add(cards.next());
            System.out.println("Dealer " + this.hand);
        }
        if (hand.getTotal() > 21) {
            System.out.println("Dealer busts. " + this.hand);
        } else {
            System.out.println("Dealer stands. " + this.hand);
        }
    }
}