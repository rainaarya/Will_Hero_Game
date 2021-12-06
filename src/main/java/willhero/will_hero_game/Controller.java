package willhero.will_hero_game;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ImageView princessMsg;
    @FXML
    private ImageView princess;
    @FXML
    private ImageView orc;
    @FXML
    private ImageView island;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // make all jump using TranslateTransition princessMsg, princess, orc, island
        TranslateTransition tt1 = new TranslateTransition(javafx.util.Duration.millis(400), princessMsg);
        tt1.setByY(-60);
        tt1.setCycleCount(TranslateTransition.INDEFINITE);
        tt1.setAutoReverse(true);

        TranslateTransition tt2 = new TranslateTransition(javafx.util.Duration.millis(400), princess);
        tt2.setByY(-60);
        tt2.setCycleCount(TranslateTransition.INDEFINITE);
        tt2.setAutoReverse(true);

        TranslateTransition tt3 = new TranslateTransition(javafx.util.Duration.millis(500), orc);
        tt3.setByY(-60);
        tt3.setCycleCount(TranslateTransition.INDEFINITE);
        tt3.setAutoReverse(true);

        tt1.play();
        tt2.play();
        tt3.play();


    }
}

