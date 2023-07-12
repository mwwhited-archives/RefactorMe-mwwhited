import calculators.Calculate;
import calculators.CalculateBlackjack;
import calculators.CalculateCards;
import commands.PlayCommand;
import models.*;
import presenters.Presenter;
import presenters.RulesPresenter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame {

    private final PlayCommand playCommand;
    private final Scanner scanner;
    private final PrintStream output;
    private final Presenter<List<String>> rules;
    private final GameContext context;
    private final Calculate<Hand, Boolean> blackjack = new CalculateBlackjack();
    private final Calculate<Iterable<Card>, Integer> cards = new CalculateCards();

    public BlackjackGame() {
        this.output = System.out;
        this.scanner = new Scanner(System.in);
        this.playCommand = new PlayCommand(
                this.scanner,
                this.output
        );
        this.rules = new RulesPresenter();
        this.context = new GameContext(1);
    }


    // Starts game and displays the rules
    public BlackjackGame initializeGame() {
        for(var rule : rules.present()){
            output.println(rule);
        }

        // Gets the amount of players and creates them
        do {
            output.print("How many people are playing (1-6)? ");
            var users = scanner.nextInt();
            if (users > 0 && users <= 6) {

                // Asks for player names and assigns them
                for (int userIndex = 0; userIndex < users; userIndex++) {
                    output.print("What is player " + (userIndex + 1) + "'s name? ");
                    var playerName = scanner.next();
                    context.getPlayers().add(new Player(context.getDefaultBank(), playerName));
                }
                return this;
            }
        } while (true);
    }

    @SuppressWarnings("UnusedReturnValue")
    public BlackjackGame play() {

        do {
            this.clearHands();
            this.shuffle();
            this.getBets();
            this.dealCards();
            this.printStatus();
            this.checkBlackjack();
            this.playRound();
            this.settleBets();
            this.printMoney();
        } while (this.playAgain());
        endGame();
        return this;
    }

    // Shuffles the deck
    private void shuffle() {
        context.getDeck().shuffle();
        context.resetDeal();
    }

    // Gets the bets from the players
    private void getBets() {
        int betValue;

        for (var player : context.getPlayers()) {
            if (player.getBank() <= 0) continue;

            do {
                output.print("How much do you want to bet " + player.getName() + (" (1-" + player.getBank()) + ")? ");
                betValue = scanner.nextInt();
                player.setBet(betValue);
            } while (!(betValue > 0 && betValue <= player.getBank()));

            output.println();
        }
    }

    // Deals the cards to the players and dealer
    private void dealCards() {
        for (int cardCount = 0; cardCount < 2; cardCount++) {
            for (var player : context.getPlayers()) {
                player.getHand().add(context.getDeal().next());
            }
            context.getDealer().getHand().add(context.getDeal().next());
        }
    }

    // Initial check for dealer or player Blackjack
    private void checkBlackjack() {
        if (blackjack.calculate(context.getDealer().getHand())) {
            output.println(context.getDealer() + " has BlackJack!");

            for (var player : context.getPlayers()) {
                if (blackjack.calculate(player.getHand())) {
                    output.println(player.getName() + " pushes");
                    player.push();
                } else {
                    output.println(player.getName() + " loses");
                    player.bust();
                }
            }

        } else {
//            if (context.getDealer().peek()) { //TODO: check this logic, not sure if correct
//                output.println("models.Dealer peeks and does not have a BlackJack");
//            }

            for (var player : context.getPlayers()) {
                if (blackjack.calculate(player.getHand())) {
                    output.println(player.getName() + " has blackjack!");
                    player.blackjack();
                }
            }
        }

    }

    // This code takes the user commands to hit or stand
    private void playRound() {
        boolean inPlay = false;
        for (var player : context.getPlayers()) {
            inPlay = inPlay || playCommand.play(player, context.getDeal());
        }
        if (inPlay) {
            playCommand.play(context.getDealer(), context.getDeal());
        } else {
            output.println("all players bust");
        }
    }

    // This code calculates all possible outcomes and adds or removes the player bets
    private void settleBets() {
        output.println();

        var dealerTotal = cards.calculate(context.getDealer().getHand());
        for (var player : context.getPlayers()) {
            var playerTotal = cards.calculate(player.getHand());
            if (player.getBet() > 0) {
                if (playerTotal > PlayCommand.BLACKJACK_VALUE) {
                    output.println(player.getName() + " has busted");
                    player.bust();
                } else if (playerTotal == dealerTotal) {
                    output.println(player.getName() + " has pushed");
                    player.push();
                } else if (playerTotal< dealerTotal && dealerTotal <= PlayCommand.BLACKJACK_VALUE) {
                    output.println(player.getName() + " has lost");
                    player.loss();
                } else if (playerTotal == PlayCommand.BLACKJACK_VALUE) {
                    output.println(player.getName() + " has won with blackjack!");
                    player.blackjack();
                } else {
                    output.println(player.getName() + " has won");
                    player.win();
                }
            }
        }
    }

    // This prints the players hands
    private void printStatus() {
        for (var player : context.getPlayers()) {
            if (player.getBank() > 0) {
                output.println(player);
            }
        }
        output.println("Dealer has " + context.getDealer());
    }

    // This prints the players banks and tells the player if s/he is out of the game
    private void printMoney() {
        for (var player : context.getPlayers()) {
            if (player.getBank() > 0) {
                output.println(player.getName() + " has " + player.getBank());
            }
            if (player.getBank() == 0) {
                output.println(player.getName() + " has " + player.getBank() + " and is out of the game.");
                player.removeFromGame();
            }
        }
    }

    // This code resets all hands
    private void clearHands() {
        for (var player : context.getPlayers()) {
            player.getHand().clear();
        }
        context.getDealer().getHand().clear();
    }

    // This decides to force the game to end when all players lose or lets players choose to keep playing or not
    private boolean playAgain() {
        if (forceEnd()) {
            return false;
        } else {
            do {
                output.println();
                output.print("Do you want to play again (Y)es or (N)o? ");
                var c = scanner.next().toUpperCase().charAt(0);
                if (c == 'N') {
                    return false;
                } else if (c == 'Y') {
                    return true;
                }
            } while (true);
        }
    }

    // This says true or false to forcing the game to end
    private boolean forceEnd() {
        for (var player : context.getPlayers()) {
            if (player.getBank() > 0) {
                return false;
            }
        }
        output.println();
        output.println("All players have lost and the game ends.");
        return true;
    }

    // This is the endgame code for when all players are out of the game or players decide to stop playing
    private void endGame() {
        int endAmount;
        String endState = " no change.";
        output.println();
        for (var player : context.getPlayers()) {
            if (player.getBank() < 0) {
                player.resetBank();
            }
            endAmount = player.getBank() - player.getStartingBank();
            if (endAmount > 0) {
                endState = " gain of ";
            } else if (endAmount < 0) {
                endState = " loss of ";
            }
            output.println(player.getName() + " has ended the game with " + player.getBank() + ".");
            if (!endState.equals(" no change.")) {
                output.println("A" + endState + Math.abs(endAmount) + ".");
            } else {
                output.println("No change from their starting value.");
            }
            output.println();
        }
        output.println();
        output.println();
        output.println("Thank you for playing!");
    }
} 