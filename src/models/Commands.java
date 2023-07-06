package models;

public enum Commands {

    UNKNOWN("Unknown"),
    STAND("Stand"),
    HIT("Hit");

    private final String name;

    public String getName() {
        return name;
    }

    Commands(String name){
        this.name = name;
    }

    public static Commands get(String input){
        if (input.equalsIgnoreCase("H")) return Commands.HIT;
        if (input.equalsIgnoreCase("HIT")) return Commands.HIT;

        if (input.equalsIgnoreCase("S")) return Commands.STAND;
        if (input.equalsIgnoreCase("STAND")) return Commands.STAND;

        return Commands.UNKNOWN;
    }
}