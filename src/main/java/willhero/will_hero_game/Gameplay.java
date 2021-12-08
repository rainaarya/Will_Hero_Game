package willhero.will_hero_game;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Gameplay implements Initializable {

    @FXML
    private ImageView topIsland1;
    @FXML
    private ImageView floatIsland1;
    @FXML
    private ImageView weaponChest1;
    @FXML
    private ImageView hero;
    @FXML
    private ImageView topIsland1tree1;
    @FXML
    private ImageView topIsland1tree2;

    private TranslateTransition transition(ImageView imageView, double time, double ByY) {
        TranslateTransition tt = new TranslateTransition(javafx.util.Duration.millis(time), imageView);
        tt.setByY(ByY);
        tt.setCycleCount(javafx.animation.Animation.INDEFINITE);
        tt.setAutoReverse(true);
        return tt;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<TranslateTransition> transitions = new ArrayList<>();
        transitions.add(transition(topIsland1, 2000, -25));
        transitions.add(transition(floatIsland1, 2000, -25));
        transitions.add(transition(weaponChest1, 400, -15));
        transitions.add(transition(hero, 500, -60));
        transitions.add(transition(topIsland1tree1, 2000, -25));
        transitions.add(transition(topIsland1tree2, 2000, -25));
        ParallelTransition pt = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
        pt.play();


    }
}
