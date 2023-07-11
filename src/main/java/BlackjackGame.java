import commands.PlayCommand;
import models.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame {

    private final PlayCommand playCommand;
    private final Scanner scanner;
    private final Dealer dealer = new Dealer();
    private final List<Player> players = new ArrayList<>();
    private final Deck deck = new Deck(1);

    public BlackjackGame() {
        this.scanner = new Scanner(System.in);
        this.playCommand = new PlayCommand(this.scanner);
    }


    private Iterator<Card> deal;

    // Starts game and displays the rules
    public BlackjackGame initializeGame() {
        System.out.println("Welcome to Blackjack!");
        System.out.println();
        System.out.println("  BLACKJACK RULES: ");
        System.out.println("	-Each player is dealt 2 cards. The dealer is dealt 2 cards with one face-up and one face-down.");
        System.out.println("	-Cards are equal to their value with face cards being 10 and an Ace being 1 or 11.");
        System.out.println("	-The players cards are added up for their total.");
        System.out.println("	-Players “Hit” to gain another card from the deck. Players “Stay” to keep their current card total.");
        System.out.println("	-models.Dealer “Hits” until they equal or exceed 17.");
        System.out.println("	-The goal is to have a higher card total than the dealer without going over 21.");
        System.out.println("	-If the player total equals the dealer total, it is a “Push” and the hand ends.");
        System.out.println("	-Players win their bet if they beat the dealer. Players win 1.5x their bet if they get “Blackjack” which is 21.");
        System.out.println();
        System.out.println();

        // Gets the amount of players and creates them
        do {
            System.out.print("How many people are playing (1-6)? ");
            var users = scanner.nextInt();
            if (users > 0 && users < 6) {

                // Asks for player names and assigns them
                for (int index = 0; index < users; index++) {
                    System.out.print("What is player " + (index + 1) + "'s name? ");
                    var names = scanner.next();
                    players.add(new Player(100, names));
                }
                return this;
            }
        } while (true);
    }

    @SuppressWarnings("UnusedReturnValue")
    public BlackjackGame play() {

        do {
            this.shuffle();
            this.getBets();
            this.dealCards();
            this.printStatus();
            this.checkBlackjack();
            this.playRound();
            this.settleBets();
            this.printMoney();
            this.clearHands();
        } while (this.playAgain());
        endGame();
        return this;
    }

    // Shuffles the deck
    private void shuffle() {
        deck.shuffle();
        deal = deck.deal();
    }

    // Gets the bets from the players
    private void getBets() {
        int betValue;

        for (var player : players) {
            if (player.getBank() <= 0) continue;

            do {
                System.out.print("How much do you want to bet " + player.getName() + (" (1-" + player.getBank()) + ")? ");
                betValue = scanner.nextInt();
                player.setBet(betValue);
            } while (!(betValue > 0 && betValue <= player.getBank()));

            System.out.println();
        }
    }

    // Deals the cards to the players and dealer
    private void dealCards() {
        for (int cardCount = 0; cardCount < 2; cardCount++) {
            for (var player : players) {
                player.add(deal.next());
            }
            dealer.add(deal.next());
        }
    }

    // Initial check for dealer or player Blackjack
    private void checkBlackjack() {
        if (dealer.isBlackjack()) {
            System.out.println(dealer + " has BlackJack!");

            for (var player : players) {
                if (player.getTotal() == 21) {
                    System.out.println(player.getName() + " pushes");
                    player.push();
                } else {
                    System.out.println(player.getName() + " loses");
                    player.bust();
                }
            }

        } else {
//            if (dealer.peek()) { //TODO: check this logic, not sure if correct
//                System.out.println("models.Dealer peeks and does not have a BlackJack");
//            }

            for (var player : players) {
                if (player.getTotal() == 21) {
                    System.out.println(player.getName() + " has blackjack!");
                    player.blackjack();
                }
            }
        }

    }

    // This code takes the user commands to hit or stand
    private void playRound() {
        boolean inPlay = false;
        for (var player : players) {
            inPlay = inPlay || playCommand.play(player, deal);
        }
        if (inPlay){
            playCommand.play(dealer, deal);
        }else {
            System.out.println("all players bust");
        }
    }

    // This code calculates all possible outcomes and adds or removes the player bets
    private void settleBets() {
        System.out.println();

        for (var player : players) {
            if (player.getBet() > 0) {
                if (player.getTotal() > 21) {
                    System.out.println(player.getName() + " has busted");
                    player.bust();
                } else if (player.getTotal() == dealer.getTotal()) {
                    System.out.println(player.getName() + " has pushed");
                    player.push();
                } else if (player.getTotal() < dealer.getTotal() && dealer.getTotal() <= 21) {
                    System.out.println(player.getName() + " has lost");
                    player.loss();
                } else if (player.getTotal() == 21) {
                    System.out.println(player.getName() + " has won with blackjack!");
                    player.blackjack();
                } else {
                    System.out.println(player.getName() + " has won");
                    player.win();
                }
            }
        }

    }

    // This prints the players hands
    private void printStatus() {
        for (var player : players) {
            if (player.getBank() > 0) {
                System.out.println(player);
            }
        }
        System.out.println("Dealer has " + dealer);
    }

    // This prints the players banks and tells the player if s/he is out of the game
    private void printMoney() {
        for (var player : players) {
            if (player.getBank() > 0) {
                System.out.println(player.getName() + " has " + player.getBank());
            }
            if (player.getBank() == 0) {
                System.out.println(player.getName() + " has " + player.getBank() + " and is out of the game.");
                player.removeFromGame();
            }
        }
    }

    // This code resets all hands
    private void clearHands() {
        for (var player : players) {
            player.clear();
        }
        dealer.clear();
    }

    // This decides to force the game to end when all players lose or lets players choose to keep playing or not
    private boolean playAgain() {
        if (forceEnd()) {
            return false;
        } else {
            do {
                System.out.println();
                System.out.print("Do you want to play again (Y)es or (N)o? ");
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
        int endCount = 0;

        for (var player : players) {
            if (player.getBank() <= 0) {
                endCount++;
            }
        }
        if (endCount == players.size()) {
            System.out.println();
            System.out.println("All players have lost and the game ends.");
            return true;
        }

        return false;
    }

    // This is the endgame code for when all players are out of the game or players decide to stop playing
    private void endGame() {
        int endAmount;
        String endState = " no change.";
        System.out.println();
        for (var player : players) {
            if (player.getBank() < 0) {
                player.resetBank();
            }
            endAmount = player.getBank() - 100;
            if (endAmount > 0) {
                endState = " gain of ";
            } else if (endAmount < 0) {
                endState = " loss of ";
            }
            System.out.println(player.getName() + " has ended the game with " + player.getBank() + ".");
            if (!endState.equals(" no change.")) {
                System.out.println("A" + endState + Math.abs(endAmount) + ".");
            } else {
                System.out.println("No change from their starting value.");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println("Thank you for playing!");
    }
} 