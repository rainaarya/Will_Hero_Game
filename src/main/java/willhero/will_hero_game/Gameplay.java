package willhero.will_hero_game;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    private AnchorPane pauseAnchorPane;
    @FXML
    private ImageView orc;

    private ParallelTransition pt;

    @FXML
    void pauseGame(MouseEvent event) {


        pauseAnchorPane.setDisable(false);
        pauseAnchorPane.setVisible(true);

        pt.pause();
    }

    @FXML
    void quitGame(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void restartGame(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Gameplay.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resumeGame(MouseEvent event) {


        pauseAnchorPane.setDisable(true);
        pauseAnchorPane.setVisible(false);
        pt.play();
    }



    @FXML
    void saveGame(MouseEvent event) {
        System.out.println("Save Game");

    }

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
        transitions.add(transition(hero, 450, -60));
        transitions.add(transition(topIsland1tree1, 2000, -25));
        transitions.add(transition(topIsland1tree2, 2000, -25));
        transitions.add(transition(orc, 550, -60));
        pt = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
        pt.play();


    }
}
