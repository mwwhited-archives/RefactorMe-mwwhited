package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GameContext {

    private static final int DEFAULT_BANK = 100;

    private final Dealer dealer;
    private final List<Player> players;
    private final Deck deck;
    private Iterator<Card> deal = null;

    public GameContext(
            int deckCount
    ) {
        this.dealer = new Dealer();
        this.players = new ArrayList<>();
        this.deck = new Deck(deckCount);
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getDefaultBank() {
        return DEFAULT_BANK;
    }

    public Iterator<Card> getDeal() {
        if (deal == null)
            deal = getDeck().deal();
        return deal;
    }

    public void resetDeal() {
        deal = null;
    }
}
