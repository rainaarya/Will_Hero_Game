package willhero.will_hero_game;

public class InsufficientCoinsException extends Exception {
    public InsufficientCoinsException() {
        super("Insufficient coins to revive");
    }
}
