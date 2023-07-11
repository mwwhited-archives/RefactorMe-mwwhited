package commands;

import factories.Factory;
import factories.PlayerActionsFactory;
import models.Card;
import models.Dealer;
import models.Player;
import models.PlayerActions;

import java.util.Iterator;
import java.util.Scanner;

public class PlayCommand {

    public static final int BLACKJACK_VALUE = 21;

    private final Factory<PlayerActions> playerActions = new PlayerActionsFactory();
    private final Scanner scanner;

    public PlayCommand(Scanner scanner){
        this.scanner = scanner;
    }

    public boolean play(Player player, Iterator<Card> deal) {
        if (player instanceof Dealer){
            return dealerPlay((Dealer)player, deal);
        } else {
            return playerPlay(player, deal);
        }
    }

    private boolean dealerPlay(Dealer dealer, Iterator<Card> deal) {
        System.out.println();
        var hand = dealer.getHand();
        while (hand.getTotal() <= 16) {
            System.out.println("Dealer has " + hand.getTotal() + " and hits");
            hand.add(deal.next());
            System.out.println("Dealer " + hand);
        }
        if (hand.getTotal() > BLACKJACK_VALUE) {
            System.out.println("Dealer busts. " + hand);
            return false;
        } else {
            System.out.println("Dealer stands. " + hand);
            return true;
        }
    }

    private boolean playerPlay(Player player, Iterator<Card> deal) {
        if (player.getBet() > 0) {
            System.out.println();
            System.out.println(player.getName() + " has " + player);

            PlayerActions command;
            do {
                do {
                    System.out.print(player.getName() + " (H)it or (S)tand? ");
                    var commandString = scanner.next();
                    command = playerActions.create(commandString);
                } while (command == PlayerActions.UNKNOWN);
                if (command == PlayerActions.HIT) {
                    player.add(deal.next());
                    System.out.println(player);
                }
            } while (command != PlayerActions.STAND && player.getTotal() <= BLACKJACK_VALUE);

            return player.getTotal() <= BLACKJACK_VALUE;

        } else {
            return false;
        }
    }
}
