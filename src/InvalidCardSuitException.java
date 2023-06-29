public class InvalidCardSuitException extends Exception {
    private final char suitIdentifier;

    public InvalidCardSuitException(char invalidSuit) {
super("Attempted to create card with invalid suit argument " + invalidSuit);
        suitIdentifier = invalidSuit;
    }

    public char getSuitDesignator() {
        return suitIdentifier;
    }
}