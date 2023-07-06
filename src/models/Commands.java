package models;

public enum Commands {

    UNKNOWN("Unknown"),
    STAND("Stand"),
    HIT("Hit");

    String name;

    public String getName() {
        return name;
    }

    Commands(String name){
        this.name = name;
    }

    public static Commands get(String input){
        if (input.toUpperCase() == "H") return Commands.HIT;
        if (input.toUpperCase() == "HIT") return Commands.HIT;

        if (input.toUpperCase() == "S") return Commands.STAND;
        if (input.toUpperCase() == "STAND") return Commands.STAND;

        return Commands.UNKNOWN;
    }
}