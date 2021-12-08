package willhero.will_hero_game;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

    private Stage stage;
    private Scene scene;
    private Parent root;

    private TranslateTransition transition(ImageView imageView, double time, double ByY) {
        TranslateTransition tt = new TranslateTransition(javafx.util.Duration.millis(time), imageView);
        tt.setByY(ByY);
        tt.setCycleCount(javafx.animation.Animation.INDEFINITE);
        tt.setAutoReverse(true);
        return tt;
    }


    public void switchToSettings(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToInfo(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Information.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Gameplay.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // make all jump using TranslateTransition princessMsg, princess, orc, island

        ArrayList<TranslateTransition> transitions = new ArrayList<>();
        transitions.add(transition(princessMsg, 400, -60));
        transitions.add(transition(princess, 400, -60));
        transitions.add(transition(orc, 500, -60));

        ParallelTransition pt = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
        pt.play();


    }


}

