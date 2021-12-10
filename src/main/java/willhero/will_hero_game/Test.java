package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Test implements Initializable {

    private ArrayList<GameObjects> gameObjects;
    private Hero hero;
    private ArrayList<Orc> orcs;
    private float previousX = 0;
    private int moves = 0;
    private static int coins = 0;
    private boolean heroCollision = false;
    @FXML
    private AnchorPane gamePlayAnchorPane;
    @FXML
    private Text movesLabel;
    @FXML
    private Text coinLabel;


    public static void setCoins(int coins) {
        Test.coins++;
    }
    private void addObject(int c) {
        int distance = 100;
        ObjectFactory f = new ObjectFactory();
        GameObjects obs1 = null;
//        if(c==6||c==9)obs1=f.createObstacle(c,263,prevobstacley-(2*distance));


        if (c == 1) {

            obs1 = f.createObject(c, previousX + distance, 333);
            obs1.display(gamePlayAnchorPane);
            gameObjects.add(obs1);

            Orc orc = new Orc((float) obs1.getImageView().getLayoutBounds().getMinX() + 35, 292);
            orc.setObjectType("Orc");
            orc.display(gamePlayAnchorPane);
            gameObjects.add(orc);
            orcs.add(orc);


            previousX = previousX + distance + (float) obs1.getImageView().getBoundsInParent().getWidth();
        }


    }


    public boolean detectCollision() {
        boolean tmp = false;
        boolean orcCollision = false;
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObjects o = gameObjects.get(i);
            tmp = o.onCollide(hero);
            if (tmp) {
                orcCollision = true;
            }
            if (o instanceof Island) {
                for (int j = 0; j < orcs.size(); j++) {
                    o.onCollide(orcs.get(j));
                }
            }

        }

        return orcCollision;
    }

//    @Override
//    public void start(Stage stage) throws Exception {
//
//
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameObjects = new ArrayList<>();
        orcs = new ArrayList<>();
        hero = new Hero(140, 292);
        hero.display(gamePlayAnchorPane);
        gameObjects.add(hero);
        addObject(1);
        addObject(1);
        addObject(1);

//        Island island = new Island(75, 333);
//        island.display(gamePlayAnchorPane);
//        gameObjects.add(island);
//
//        Island island2 = new Island(340, 333);
//        island2.display(gamePlayAnchorPane);
//        gameObjects.add(island2);
//
//        Orc orc = new Orc(217, 292);
//        orc.display(gamePlayAnchorPane);
//        orcs = new ArrayList<>();
//        orcs.add(orc);
//        gameObjects.add(orc);


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            coinLabel.setText("" + coins);
            if (detectCollision()) {
                heroCollision = true;
                hero.getxMovementTimeline().stop();
                hero.getyMovementTimeline().stop();
            }
        }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        //add mouse click to anchor pane
        gamePlayAnchorPane.setOnMouseClicked(e -> {
            if (!heroCollision) {

                hero.moveHero(60);
                moves++;
                movesLabel.setText("Moves: " + moves);
                //move all game objects to the left
                Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(20), e1 -> {
                    for (int i = 0; i < gameObjects.size(); i++) {

                        gameObjects.get(i).getImageView().setLayoutX(gameObjects.get(i).getImageView().getLayoutX() - 5);


                    }
                }
                ));
                timeline1.setCycleCount(12);
                timeline1.play();

            }
        });

    }
}
