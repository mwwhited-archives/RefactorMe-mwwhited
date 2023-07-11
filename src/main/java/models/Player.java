package models;

public class Player {
    public static final int BLACKJACK_VALUE = 21;

    protected int bank;
    protected int bet;
    protected final String name;
    protected final Hand hand = new Hand();

    // Creates a player object
    public Player(int bank, String name) {
        this.bank = bank;
        this.name = name;
    }

    public Hand getHand(){
        return hand;
    }

    public boolean isBlackjack() {
        return hand.getTotal() == BLACKJACK_VALUE;
    }

    // Gets a player's bank amount
    public int getBank() {
        return bank;
    }

    // Removes a player's bet from their bank if they bust. Sets bet to zero afterward.
    public void bust() {
        bank -= bet;
        bet = 0;
    }

    // Adds a player's bet from their bank if they win. Sets bet to zero afterward.
    public void win() {
        bank += bet;
        bet = 0;
    }

    // Removes a player's bet from their bank if they lose. Sets bet to zero afterward.
    public void loss() {
        bank -= bet;
        bet = 0;
    }

    // This sets the player bank to -1. -1 is unreachable, they are removed from the game
    public void removeFromGame() {
        bank = -1;
    }

    // This resets the bank to 0. Currently used to reset a removed player's bank from -1 to 0.
    public void resetBank() {
        bank = 0;
    }

    // This calculates the bet for a player who has a Blackjack
    public void blackjack() {
        bank += bet * 1.5;
        bet = 0;
    }

    // Sets a player's bet to 0 if the "push". Notice, no bet is added or removed.
    public void push() {
        bet = 0;
    }

    // Sets a player's bet
    public void setBet(int value) {
        bet = value;
    }

    // Gets a player's name
    public String getName() {
        return name;
    }

    // Gets a player's hand total
    public int getTotal() {
        return hand.getTotal();
    }

    // Gets a player's bet
    public int getBet() {
        return this.bet;
    }

    // Adds a card to a player's hand
    public void add(Card card) {
        hand.add(card);
    }

    @Override
    public String toString() {
        return name + " has " + hand + " (" + getTotal() + ")";
    }

    // Clears a player's hand
    public void clear() {
        hand.clear();
    }
}