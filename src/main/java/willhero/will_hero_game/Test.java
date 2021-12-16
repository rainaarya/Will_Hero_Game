package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Test implements Initializable {

    private ArrayList<GameObjects> gameObjects;
    private Hero hero;
    private ArrayList<Orc> orcs;
    private ArrayList<Chest> chests;
    private float previousX = 0;
    private Integer moves = 0;
    private static Integer coins = 0;
    private Integer coins_temp;
    private int numOfislands = 1;
    private Integer heroCollision = 0;
    private static boolean serialised = false;

    @FXML
    private AnchorPane gamePlayAnchorPane;
    @FXML
    private Text movesLabel;
    @FXML
    private Text coinLabel;


    public static void setSerialised(boolean serialised) {
        Test.serialised = serialised;
    }

    public void serialize() throws IOException {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("save.ser"));
            out.writeObject(moves);
            Integer coins_temp = coins;
            out.writeObject(coins_temp);
            out.writeObject(heroCollision);

            for (GameObjects obj : gameObjects) {
                out.writeObject(obj);
            }

        } finally {
            assert out != null;
            out.close();
        }

    }

    public void deserialize() throws IOException, ClassNotFoundException {
        DeserialiseHelper deserialiseHelper = new DeserialiseHelper();
        deserialiseHelper.deserialize("save.ser");

        ArrayList<Integer> gameInfo = deserialiseHelper.getGameInfo();
        moves = gameInfo.get(0);
        coins = gameInfo.get(1);
        heroCollision = gameInfo.get(2);

        gameObjects = deserialiseHelper.regenerateGameObjects();
        orcs = new ArrayList<>();
        chests = new ArrayList<>();
        for (GameObjects obj : gameObjects) {
            obj.display(gamePlayAnchorPane);

            if (obj.getObjectType().equals("Hero")) {
                hero = (Hero) obj;
            } else if (obj.getObjectType().equals("Orc")) {
                orcs.add((Orc) obj);
            } else if (obj.getObjectType().equals("CoinChest")) {
                chests.add((CoinChest) obj);
                obj.getImageView().toBack();
            } else if (obj.getObjectType().equals("WeaponChest")) {
                chests.add((WeaponChest) obj);
                obj.getImageView().toBack();
            }
            else if(obj.getObjectType().equals("Trees")){
                obj.getImageView().toBack();
            }
            else if(obj.getObjectType().equals("Cloud")){
                obj.getImageView().toBack();
            }

        }
        previousX = (int) gameObjects.get((gameObjects.size() - 1)).getImageView().getBoundsInParent().getMaxX(); //check??
    }

    private void addEnvironment(GameObjects obs1) {
        Trees tree1 = new Trees((float) obs1.getImageView().getBoundsInParent().getMinX() + 10, (float) obs1.getImageView().getBoundsInParent().getMinY());
        tree1.getImageView().setLayoutY(tree1.getImageView().getLayoutY() - tree1.getImageView().getBoundsInParent().getHeight());
        tree1.setLayoutXY((float) tree1.getImageView().getLayoutX(), (float) tree1.getImageView().getLayoutY());
        tree1.display(gamePlayAnchorPane);
        tree1.getImageView().toBack();
        gameObjects.add(tree1);

        Trees tree2 = new Trees((float) obs1.getImageView().getBoundsInParent().getMaxX() - 50, (float) obs1.getImageView().getBoundsInParent().getMinY());
        tree2.getImageView().setLayoutY(tree2.getImageView().getLayoutY() - tree2.getImageView().getBoundsInParent().getHeight());
        tree2.setLayoutXY((float) tree2.getImageView().getLayoutX(), (float) tree2.getImageView().getLayoutY());
        tree2.display(gamePlayAnchorPane);
        tree2.getImageView().toBack();
        gameObjects.add(tree2);

        Cloud cloud = new Cloud((float) obs1.getImageView().getBoundsInParent().getMinX() + 15, 50);
        cloud.display(gamePlayAnchorPane);
        cloud.getImageView().toBack();
        gameObjects.add(cloud);

    }

    private void throwKnife(Hero hero) {
        if (hero.getcurrentWeapon() == 1) {
            ThrowingKnife knife = new ThrowingKnife(0, 0, hero);
            knife.display(gamePlayAnchorPane);
            gameObjects.add(knife);
            knife.throwKnife();
            //gameObjects.remove(knife);
        }


    }


    public static void setCoins(int coins) {
        Test.coins += coins;
    }

    private void addObject(int c) {
        int distance = 120;
        ObjectFactory f = new ObjectFactory();
        GameObjects obs1 = null;
//        if(c==6||c==9)obs1=f.createObstacle(c,263,prevobstacley-(2*distance));


        if (c == 1) {

            obs1 = f.createObject(c, previousX + distance, 333);
            obs1.display(gamePlayAnchorPane);
            gameObjects.add(obs1);

            Orc orc = new Orc((float) obs1.getImageView().getBoundsInParent().getMinX() + 100, 292);
            orc.display(gamePlayAnchorPane);
            gameObjects.add(orc);
            orcs.add(orc);

            addEnvironment(obs1);

            previousX = previousX + distance + (float) obs1.getImageView().getBoundsInParent().getWidth();
        }
        if (c == 2) {
            obs1 = f.createObject(1, previousX + distance, 333);
            obs1.display(gamePlayAnchorPane);
            gameObjects.add(obs1);

            WeaponChest chest = new WeaponChest((float) obs1.getImageView().getBoundsInParent().getMinX() + 50, (float) obs1.getImageView().getBoundsInParent().getMinY());
            chest.getImageView().setLayoutY(chest.getImageView().getLayoutY() - chest.getImageView().getBoundsInParent().getHeight());
            chest.setLayoutXY((float) chest.getImageView().getLayoutX(), (float) chest.getImageView().getLayoutY());
            chest.display(gamePlayAnchorPane);
            chest.getImageView().toBack();
            gameObjects.add(chest);
            chests.add(chest);

            addEnvironment(obs1);


            previousX = previousX + distance + (float) obs1.getImageView().getBoundsInParent().getWidth();
        }
        if (c == 3) {
            obs1 = f.createObject(1, previousX + distance, 333);
            obs1.display(gamePlayAnchorPane);
            gameObjects.add(obs1);

            CoinChest chest = new CoinChest((float) obs1.getImageView().getBoundsInParent().getMinX() + 100, (float) obs1.getImageView().getBoundsInParent().getMinY());
            chest.getImageView().setLayoutY(chest.getImageView().getLayoutY() - chest.getImageView().getBoundsInParent().getHeight());
            chest.setLayoutXY((float) chest.getImageView().getLayoutX(), (float) chest.getImageView().getLayoutY());
            chest.display(gamePlayAnchorPane);
            chest.getImageView().toBack();
            gameObjects.add(chest);
            chests.add(chest);

            addEnvironment(obs1);

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
                for (int j = 0; j < chests.size(); j++) {
                    o.onCollide(chests.get(j));
                }
            }
            if (o instanceof Orc) {
                for (int j = 0; j < orcs.size(); j++) {
                    o.onCollide(orcs.get(j));
                }
            }
            if (o instanceof ThrowingKnife) {
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

    @FXML
    public void pause(MouseEvent event) throws IOException {
        serialize();
        System.out.println(hero.getImageView().getLayoutX() + " " + hero.getImageView().getLayoutY() + " " + hero.getImageView().getX() + " " + hero.getImageView().getY());
        //exit the game
        System.exit(0);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (serialised) {
            System.out.println("serialised");
            try {
                deserialize();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {

            gameObjects = new ArrayList<>();
            orcs = new ArrayList<>();
            chests = new ArrayList<>();
            hero = new Hero(140, 292);
            hero.display(gamePlayAnchorPane);
            gameObjects.add(hero);
//        addObject(1);
//        addObject(1);
//        addObject(1);
//        addObject(2);
//        addObject(1);
//        addObject(1);
//        addObject(1);
//        addObject(1);
//        addObject(1);
            while (numOfislands <= 20) {
                if (numOfislands % 3 == 0) {
                    addObject((int) (Math.random() * 2) + 2);
                } else {
                    addObject(1);
                }
                numOfislands++;
            }
        }

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


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(6), e -> {
            coinLabel.setText("" + coins);
            movesLabel.setText("Moves: " + moves);
            if (detectCollision()) {
                System.out.println("collision");
                heroCollision = 1;
                hero.getxMovementTimeline().stop();
                hero.getyMovementTimeline().stop();
            }
        }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        //add mouse click to anchor pane
        gamePlayAnchorPane.setOnMouseClicked(e -> {
            if (heroCollision == 0) {

                hero.moveHero(60);
                throwKnife(hero);
                moves++;

                //move all game objects to the left
                Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(10), e1 -> {
                    for (int i = 0; i < gameObjects.size(); i++) {

                        gameObjects.get(i).getImageView().setLayoutX(gameObjects.get(i).getImageView().getLayoutX() - 2);
                        gameObjects.get(i).setLayoutXY((float) gameObjects.get(i).getImageView().getLayoutX(), (float) gameObjects.get(i).getImageView().getLayoutY());

                    }
                }
                ));
                timeline1.setCycleCount(30);
                timeline1.play();

            }
        });


    }
}
