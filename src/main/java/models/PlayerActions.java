package models;

public enum PlayerActions {

    UNKNOWN("Unknown"),
    STAND("Stand"),
    HIT("Hit");

    private final String name;

    public String getName() {
        return name;
    }

    PlayerActions(String name){
        this.name = name;
    }
}