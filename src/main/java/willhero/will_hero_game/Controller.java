package willhero.will_hero_game;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    @FXML
    private ImageView princessMsg;
    @FXML
    private ImageView princess;
    @FXML
    private ImageView orc;
    @FXML
    private ImageView island;
    @FXML
    private CheckBox Music;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Media media;
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private TimerTask task;
    private boolean running;
    private boolean checker;




    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

        URL resource = getClass().getResource("GameMusic.mp4");
        assert resource != null;
        mediaPlayer = new MediaPlayer(new Media(resource.toString()));
    }

    public void PlayPauseMusic(ActionEvent event){
        checker = Music.isSelected();
        if(checker){
            mediaPlayer.play();
        }
        else{
            mediaPlayer.pause();
        }
    }

}

