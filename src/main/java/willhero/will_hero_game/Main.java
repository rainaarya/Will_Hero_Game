package willhero.will_hero_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Will Hero");
            stage.setScene(scene);
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("Knight.png")));
            stage.show();
            File folder = new File("src\\main\\savedGames");
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}