package presenters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RulesPresenter implements Presenter<List<String>> {

    private final List<String> rules = new ArrayList<>(){{
        add("");
        add("  BLACKJACK RULES: ");
        add("	-Each player is dealt 2 cards. The dealer is dealt 2 cards with one face-up and one face-down.");
        add("	-Cards are equal to their value with face cards being 10 and an Ace being 1 or 11.");
        add("	-The players cards are added up for their total.");
        add("	-Players “Hit” to gain another card from the deck. Players “Stay” to keep their current card total.");
        add("	-models.Dealer “Hits” until they equal or exceed 17.");
        add("	-The goal is to have a higher card total than the dealer without going over 21.");
        add("	-If the player total equals the dealer total, it is a “Push” and the hand ends.");
        add("	-Players win their bet if they beat the context.getDealer(). Players win 1.5x their bet if they get “Blackjack” which is 21.");
        add("");
        add("");
    }};

    @Override
    public List<String>  present() {
        return rules;
    }
}
