public class InvalidDeckPositionException extends Exception {
    private final int positionIdentifier;

    public InvalidDeckPositionException(int inValidPosition)  {
        super("Attempted to get a card from a position not in Deck " + inValidPosition);
        positionIdentifier = inValidPosition;
    }

    public int getPositionValue() {
        return positionIdentifier;
    }
}