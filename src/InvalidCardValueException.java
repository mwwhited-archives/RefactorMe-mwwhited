public class InvalidCardValueException extends Exception {
    private final int valueIdentifier;

    public InvalidCardValueException(int invalidValue) {
        super("Attempted to create card with invalid suit argument " + invalidValue);
        valueIdentifier = invalidValue;
    }

    public int getValue() {
        return valueIdentifier;
    }

}