package willhero.will_hero_game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        if (welcomeText.getText().equals("Welcome to JavaFX Application!")) {
            welcomeText.setText("Hello Will!");
        } else {
            welcomeText.setText("Welcome to JavaFX Application!");
        }

    }
}

