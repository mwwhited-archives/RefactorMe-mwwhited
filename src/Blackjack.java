public class Blackjack {
    public static void main(String[] args) throws Exception {
        new BlackjackGame()
                .initializeGame()
                .play()
        ;
    }
}