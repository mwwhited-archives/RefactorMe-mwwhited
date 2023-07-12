package commands;

import calculators.Calculate;
import calculators.CalculateCards;
import factories.Factory;
import factories.PlayerActionsFactory;
import models.Card;
import models.Dealer;
import models.Player;
import models.PlayerActions;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

public class PlayCommand {

    public static final int BLACKJACK_VALUE = 21;

    private final Factory<PlayerActions> playerActions = new PlayerActionsFactory();
    private Calculate<Iterable<Card>, Integer> calculateCards = new CalculateCards();
    private final Scanner scanner;
    private final PrintStream output;

    public PlayCommand(
            Scanner scanner,
            PrintStream output
    ) {
        this.scanner = scanner;
        this.output = output;
    }

    public boolean play(Player player, Iterator<Card> deal) {
        if (player instanceof Dealer) {
            return dealerPlay((Dealer) player, deal);
        } else {
            return playerPlay(player, deal);
        }
    }

    private boolean dealerPlay(Dealer dealer, Iterator<Card> deal) {
        output.println();
        var hand = dealer.getHand();
        var total = calculateCards.calculate(hand);
        while (total <= 16) {
            output.println("Dealer has " + total + " and hits");
            hand.add(deal.next());
            output.println("Dealer " + hand);
        }
        if (total > BLACKJACK_VALUE) {
            output.println("Dealer busts. " + hand);
            return false;
        } else {
            output.println("Dealer has 2" + total + " and stands. " + hand);
            return true;
        }
    }

    private boolean playerPlay(Player player, Iterator<Card> deal) {
        if (player.getBet() > 0) {
            output.println();
            output.println(player.getName() + " has " + player);

            PlayerActions command;
            do {
                do {
                    output.print(player.getName() + " (H)it or (S)tand? ");
                    var commandString = scanner.next();
                    command = playerActions.create(commandString);
                } while (command == PlayerActions.UNKNOWN);
                if (command == PlayerActions.HIT) {
                    player.getHand().add(deal.next());
                    output.println(player);
                }
            } while (command != PlayerActions.STAND && calculateCards.calculate(player.getHand()) < BLACKJACK_VALUE);

            return calculateCards.calculate(player.getHand())  <= BLACKJACK_VALUE;

        } else {
            return false;
        }
    }
}
