module willhero.will_hero_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens willhero.will_hero_game to javafx.fxml;
    exports willhero.will_hero_game;
}