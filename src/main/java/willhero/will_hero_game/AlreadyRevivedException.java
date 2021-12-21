package willhero.will_hero_game;

public class AlreadyRevivedException extends Exception {
    public AlreadyRevivedException() {
        super("Already revived once");
    }
}
