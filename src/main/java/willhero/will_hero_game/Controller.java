package willhero.will_hero_game;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
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
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Group LoadGameWindowGroup;
    @FXML
    private Text coinsText;
    @FXML
    private Text movesText;
    @FXML
    private Group gameSelectedGroup;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Game gameController;

    private TranslateTransition transition(ImageView imageView, double time, double ByY) {
        TranslateTransition tt = new TranslateTransition(javafx.util.Duration.millis(time), imageView);
        tt.setByY(ByY);
        tt.setCycleCount(javafx.animation.Animation.INDEFINITE);
        tt.setAutoReverse(true);
        return tt;
    }

    @FXML
    void closeWindow(MouseEvent event) {
        LoadGameWindowGroup.setDisable(true);
        gameSelectedGroup.setDisable(true);
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), LoadGameWindowGroup);
        tt.setByY(600);
        tt.setCycleCount(1);
        tt.play();
        //on finish
        tt.setOnFinished(e -> {
            LoadGameWindowGroup.setVisible(false);
            gameSelectedGroup.setVisible(false);
        });


    }

    @FXML
    public void switchToSettings(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToInfo(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Information.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //exit the game
    @FXML
    public void exitGame(MouseEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void switchToGame(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameplayNew.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.show();
        //change the song of the media
        Settings.setMediaPlayer("willherodrums_1.mp3");
        Settings.getMediaPlayer().setVolume(0.5);
        Settings.getMediaPlayer().play();

    }

    @FXML
    public void loadGame(MouseEvent event) throws IOException {
        LoadGameWindowGroup.setVisible(true);
        LoadGameWindowGroup.setDisable(false);
        coinsText.setText("Coins:");
        movesText.setText("Moves:");
        //clear all the choices if there are any
        choiceBox.getItems().clear();
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), LoadGameWindowGroup);
        tt.setByY(-600);
        tt.setCycleCount(1);
        tt.play();

        ArrayList<String> games = new ArrayList<>();
        File[] directory = new File("src\\main\\savedGames").listFiles();
        for (File file : directory) {
            games.add(file.getName());
        }
        if (games.contains(".gitkeep")) {
            games.remove(".gitkeep");
        }
        choiceBox.getItems().addAll(games);

    }

    @FXML
    public void loadGameSave(MouseEvent event) throws IOException {
        String game = choiceBox.getValue();
        System.out.println(game);

        if (game != null) {
            Game.setSerialised(true);
            Game.setLoadgame(game);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameplayNew.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getRoot().requestFocus();
            stage.setScene(scene);
            stage.show();
            //change the song of the media
            Settings.setMediaPlayer("willherodrums_1.mp3");
            Settings.getMediaPlayer().setVolume(0.5);
            Settings.getMediaPlayer().play();
        } else {
            System.out.println("No game selected");
        }

    }

    @FXML
    public void deleteSave(MouseEvent event) throws IOException {
        String game = choiceBox.getValue();
        //delete the file named game from the savedGames folder if it exists
        File file = new File("src\\main\\savedGames\\" + game);
        if (file.exists()) {
            file.delete();
            choiceBox.getItems().remove(game);
            choiceBox.setValue(null);
            gameSelectedGroup.setDisable(true);
            gameSelectedGroup.setVisible(false);
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Settings.initMediaPlayer();
        // make all jump using TranslateTransition princessMsg, princess, orc, island

        ArrayList<TranslateTransition> transitions = new ArrayList<>();
        transitions.add(transition(princessMsg, 400, -60));
        transitions.add(transition(princess, 400, -60));
        transitions.add(transition(orc, 500, -60));

        ParallelTransition pt = new ParallelTransition(transitions.toArray(new TranslateTransition[0]));
        pt.play();

        //on choice box change do this
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (choiceBox.getValue() != null) {

                    gameSelectedGroup.setDisable(false);
                    gameSelectedGroup.setVisible(true);
                    System.out.println("showSelectedGameDetails");
                    DeserialiseHelper deserialiseHelper = new DeserialiseHelper();
                    //System.out.println("src\\main\\savedGames\\" + choiceBox.getValue());
                    try {
                        deserialiseHelper.deserialize("src\\main\\savedGames\\" + choiceBox.getValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    ArrayList<Integer> gameInfo = null;
                    try {
                        gameInfo = deserialiseHelper.getGameInfo();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    int moves = gameInfo.get(0);
                    int coins = gameInfo.get(1);
                    int heroCollision = gameInfo.get(2);
                    int timesRevived = gameInfo.get(3);

                    coinsText.setText("Coins: " + coins);
                    movesText.setText("Moves: " + moves);
                }
            }
        });

    }


}

