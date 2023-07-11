package factories;

import models.PlayerActions;

public class PlayerActionsFactory implements Factory<PlayerActions> {
    @Override
    public PlayerActions create(String input) {
        if (input.equalsIgnoreCase("H")) return PlayerActions.HIT;
        if (input.equalsIgnoreCase("HIT")) return PlayerActions.HIT;

        if (input.equalsIgnoreCase("S")) return PlayerActions.STAND;
        if (input.equalsIgnoreCase("STAND")) return PlayerActions.STAND;

        return PlayerActions.UNKNOWN;
    }
}
