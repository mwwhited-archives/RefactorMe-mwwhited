package models;

public enum Suits {
    HEART("Hearts"),
    DIAMOND("Diamonds"),
    CLUB("Clubs"),
    SPADE("Spades");

    private final String name;

    public String getName() {
        return name;
    }

    Suits(String name){
        this.name = name;
    }
}
