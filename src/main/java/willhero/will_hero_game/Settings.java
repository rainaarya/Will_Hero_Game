package willhero.will_hero_game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Settings implements Initializable {
    @FXML
    private CheckBox Music;

    private static MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (mediaPlayer == null) {
            URL resource = getClass().getResource("GameMusic.mp3");
            assert resource != null;
            mediaPlayer = new MediaPlayer(new Media(resource.toString()));
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                Music.setSelected(true);
            } else {
                Music.setSelected(false);
            }
        }

    }

    public void PlayPauseMusic(ActionEvent event) {
        if (Music.isSelected()) {
            mediaPlayer.play();
            //loop the media player
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                }
            });

        } else {
            mediaPlayer.pause();
        }

    }

    public void switchToMain(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
