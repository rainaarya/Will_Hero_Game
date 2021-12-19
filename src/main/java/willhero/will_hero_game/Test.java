package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Test implements Initializable {

    private ArrayList<GameObjects> gameObjects;
    private Hero hero;
    private Princess princess;
    private ArrayList<Orc> orcs;
    private ArrayList<Chest> chests;
    private ArrayList<TNT> tnts;
    private float previousX = 0;
    private Integer moves = 0;
    private static Integer coins = 0;
    private Integer coins_temp;
    private int numOfislands = 1;
    private Integer heroCollision = 0;
    private static boolean serialised = false;
    private Timeline timeline;
    private Timeline temporary;
    private GameObjects collidedObject;
    private Integer timesRevived = 0;

    @FXML
    private AnchorPane gamePlayAnchorPane;
    @FXML
    private Text movesLabel;
    @FXML
    private Text coinLabel;
    @FXML
    private Group reviveGroup;

    @FXML
    private Group pauseGroup;


    @FXML
    void quitGame(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void restartGame(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GameplayNew.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void resumeGame(MouseEvent event) {
        hero.getxMovementTimeline().play();
        hero.getyMovementTimeline().play();
        pauseGroup.setVisible(false);
        pauseGroup.setDisable(true);
    }

    @FXML
    void saveGame(MouseEvent event) throws IOException {
        serialize();
        System.out.println("Game Saved!");
        pauseGroup.setVisible(false);
        pauseGroup.setDisable(true);
        hero.getxMovementTimeline().play();
        hero.getyMovementTimeline().play();
    }

    @FXML
    public void pause(MouseEvent event) {
        hero.getxMovementTimeline().pause();
        hero.getyMovementTimeline().pause();
        pauseGroup.setVisible(true);
        pauseGroup.setDisable(false);
        pauseGroup.toFront();

    }


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
            out.writeObject(timesRevived);

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
        timesRevived = gameInfo.get(3);

        gameObjects = deserialiseHelper.regenerateGameObjects();
        orcs = new ArrayList<>();
        chests = new ArrayList<>();
        tnts = new ArrayList<>();
        for (GameObjects obj : gameObjects) {
            obj.display(gamePlayAnchorPane);

            if (obj instanceof Hero) {
                hero = (Hero) obj;
                hero.setGameObjects(gameObjects);
            } else if (obj instanceof Orc) {
                orcs.add((Orc) obj);
            } else if (obj instanceof CoinChest) {
                chests.add((CoinChest) obj);
                obj.getImageView().toBack();
            } else if (obj instanceof WeaponChest) {
                chests.add((WeaponChest) obj);
                obj.getImageView().toBack();
            } else if (obj instanceof Trees) {
                obj.getImageView().toBack();
            } else if (obj instanceof Cloud) {
                obj.getImageView().toBack();
            } else if (obj instanceof TNT) {
                tnts.add((TNT) obj);
            } else if (obj instanceof Princess) {
                princess = (Princess) obj;
            }

        }
        previousX = (int) gameObjects.get((gameObjects.size() - 1)).getImageView().getBoundsInParent().getMaxX(); //check??!!!!!!!
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

    private void throwWeapon(Hero hero) {
        if (hero.getcurrentWeapon() == 1) {
            ThrowingKnife knife = new ThrowingKnife(0, 0, hero);
            knife.display(gamePlayAnchorPane);
            gameObjects.add(knife);
            knife.throwKnife();
            //gameObjects.remove(knife);
        }
        if (hero.getcurrentWeapon() == 2) {
            Shuriken shuriken = new Shuriken(0, 0, hero);
            shuriken.display(gamePlayAnchorPane);
            gameObjects.add(shuriken);
            shuriken.throwShuriken();
            //gameObjects.remove(shuriken);
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

            WeaponChest chest = new WeaponChest((float) obs1.getImageView().getBoundsInParent().getMinX() + 100, (float) obs1.getImageView().getBoundsInParent().getMinY());
            chest.getImageView().setLayoutY(chest.getImageView().getLayoutY() - chest.getImageView().getBoundsInParent().getHeight());
            chest.setLayoutXY((float) chest.getImageView().getLayoutX(), (float) chest.getImageView().getLayoutY());
            chest.display(gamePlayAnchorPane);
            chest.getImageView().toBack();
            gameObjects.add(chest);
            chests.add(chest);

            addEnvironment(obs1);

            TNT tnt = new TNT((float) obs1.getImageView().getBoundsInParent().getMinX() + 30, (float) obs1.getImageView().getBoundsInParent().getMinY());
            tnt.getImageView().setLayoutY(tnt.getImageView().getLayoutY() - tnt.getImageView().getBoundsInParent().getHeight());
            tnt.setLayoutXY((float) tnt.getImageView().getLayoutX(), (float) tnt.getImageView().getLayoutY());
            tnt.display(gamePlayAnchorPane);
            //tnt.getImageView().toBack();
            gameObjects.add(tnt);
            tnts.add(tnt);


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

            TNT tnt = new TNT((float) obs1.getImageView().getBoundsInParent().getMinX() + 40, (float) obs1.getImageView().getBoundsInParent().getMinY());
            tnt.getImageView().setLayoutY(tnt.getImageView().getLayoutY() - tnt.getImageView().getBoundsInParent().getHeight());
            tnt.setLayoutXY((float) tnt.getImageView().getLayoutX(), (float) tnt.getImageView().getLayoutY());
            tnt.display(gamePlayAnchorPane);
            //tnt.getImageView().toBack();
            gameObjects.add(tnt);
            tnts.add(tnt);


            previousX = previousX + distance + (float) obs1.getImageView().getBoundsInParent().getWidth();
        }
        if (c == 4) {
            GameObjects obs0 = f.createObject(1, previousX + distance, 333);
            obs0.display(gamePlayAnchorPane);
            gameObjects.add(obs0);
            previousX = previousX + distance + (float) obs0.getImageView().getBoundsInParent().getWidth();

            obs1 = f.createObject(1, previousX, 333);
            obs1.display(gamePlayAnchorPane);
            gameObjects.add(obs1);

            Boss orc = new Boss((float) obs0.getImageView().getBoundsInParent().getMinX() + 100, 292);
            orc.display(gamePlayAnchorPane);
            gameObjects.add(orc);
            orcs.add(orc);

            addEnvironment(obs1);

            previousX = previousX + distance + (float) obs1.getImageView().getBoundsInParent().getWidth();

        }
        if (c == 5) {
            GameObjects obs0 = f.createObject(1, previousX + distance, 333);
            obs0.display(gamePlayAnchorPane);
            gameObjects.add(obs0);
            previousX = previousX + distance + (float) obs0.getImageView().getBoundsInParent().getWidth();

            obs1 = f.createObject(1, previousX, 333);
            obs1.display(gamePlayAnchorPane);
            gameObjects.add(obs1);

            Princess princess = new Princess((float) obs1.getImageView().getBoundsInParent().getMinX() + 100, 292);
            this.princess = princess;
            princess.display(gamePlayAnchorPane);
            gameObjects.add(princess);

            addEnvironment(obs1);

            previousX = previousX + distance + (float) obs1.getImageView().getBoundsInParent().getWidth();
        }


    }

    @FXML
    void reviveHero(MouseEvent event) {
        if (coins >= 1 && timesRevived == 0) {
            System.out.println("Lets Revive you!");
            if (collidedObject instanceof Orc) {
                collidedObject.getImageView().setY(260); //disappear
                collidedObject.setXY((float) collidedObject.getImageView().getX(), (float) collidedObject.getImageView().getY());
            }

            Island prevIsland = null;
            for (int i = 0; i < gameObjects.size(); i++) {
                if (gameObjects.get(i) instanceof Island) {
                    if (((Island) gameObjects.get(i)).getImageView().getBoundsInParent().getMinX() < hero.getImageView().getBoundsInParent().getMinX()) {
                        prevIsland = (Island) gameObjects.get(i);
                    } else {
                        break;
                    }
                }

            }
            if (prevIsland != null) {
                boolean loop = true;
                int amttraveled = 0;
                while (loop) {
                    if (prevIsland.getImageView().getBoundsInParent().getMinX() < hero.getImageView().getBoundsInParent().getMinX()) {
                        hero.getImageView().setX(hero.getImageView().getX() - 1);
                        hero.setXY((float) hero.getImageView().getX(), (float) hero.getImageView().getY());
                        amttraveled++;
                    } else {
                        loop = false;
                    }
                    if (amttraveled % 60 == 0) { //every 60 frames as hero moves 60 pixels for 1 move
                        moves--;
                    }
                }
                hero.getImageView().setY(-80);
                hero.setXY((float) hero.getImageView().getX(), (float) hero.getImageView().getY());


                heroCollision = 0;

                for (int i = 0; i < gameObjects.size(); i++) {

                    gameObjects.get(i).getImageView().setLayoutX(gameObjects.get(i).getImageView().getLayoutX() + amttraveled);
                    if (gameObjects.get(i) instanceof TNT) {
                        ((TNT) gameObjects.get(i)).moveAllTNTcomponents(amttraveled); //check and pls serialise this !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    }
                    gameObjects.get(i).setLayoutXY((float) gameObjects.get(i).getImageView().getLayoutX(), (float) gameObjects.get(i).getImageView().getLayoutY());

                }

                coins -= 1;
                timesRevived++;
                hero.getxMovementTimeline().play();
                hero.getyMovementTimeline().play();

            }
            temporary.stop();
            timeline.play();

        } else {
            if (timesRevived == 1) {
                System.out.println("You have already used your revive!");
            }
            if (coins < 1) {
                System.out.println("You do not have enough coins to revive!");
            }
        }
        reviveGroup.setVisible(false);
        reviveGroup.setDisable(true);

    }

    private void reviveScreen() {
        reviveGroup.setVisible(true);
        reviveGroup.setDisable(false);
        reviveGroup.toFront();

    }


    public boolean detectCollision() {
        boolean tmp = false;
        boolean checkHeroCollision = false;
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObjects o = gameObjects.get(i);
            tmp = o.onCollide(hero);
            if (tmp) {
                checkHeroCollision = true;
                collidedObject = o;
            }
            if (o instanceof Island) {
                for (int j = 0; j < orcs.size(); j++) {
                    o.onCollide(orcs.get(j));

                }
                for (int j = 0; j < chests.size(); j++) {
                    o.onCollide(chests.get(j));
                }
                for (int j = 0; j < tnts.size(); j++) {
                    o.onCollide(tnts.get(j));
                }
                o.onCollide(princess);

            }
            if (o instanceof Orc) {
                for (int j = 0; j < orcs.size(); j++) {
                    o.onCollide(orcs.get(j));
                }
            }
            if (o instanceof ThrowingKnife || o instanceof Shuriken) {
                for (int j = 0; j < orcs.size(); j++) {
                    o.onCollide(orcs.get(j));
                }
            }
            if (o instanceof TNT) {
                for (int j = 0; j < orcs.size(); j++) {
                    o.onCollide(orcs.get(j));
                }
                if (((TNT) o).getisExploded()) {
                    //System.out.println("TNT exploded");
                    ((TNT) o).setCheckedAllNearbyEntities(true);
                }
            }

        }

        return checkHeroCollision;
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
            tnts = new ArrayList<>();
            hero = new Hero(140, 232);
            hero.display(gamePlayAnchorPane);
            hero.setGameObjects(gameObjects);
            gameObjects.add(hero);

            while (numOfislands <= 20) {
                if (numOfislands % 3 == 0) {
                    addObject((int) (Math.random() * 2) + 2);
                } else if (numOfislands == 20) {
                    addObject(4);
                    addObject(5);
                } else {
                    addObject(1);
                }
                numOfislands++;
            }

        }

        temporary = new Timeline(new KeyFrame(Duration.millis(6), e -> {
            detectCollision();
        }
        ));
        temporary.setCycleCount(Timeline.INDEFINITE);


        timeline = new Timeline(new KeyFrame(Duration.millis(6), e -> {
            coinLabel.setText("" + coins);
            movesLabel.setText("Moves: " + moves);
            if (detectCollision()) {
                System.out.println("collision");
                heroCollision = 1;
                hero.getxMovementTimeline().stop();
                hero.getyMovementTimeline().stop();
                timeline.stop();
                temporary.play();
                reviveScreen();

//                System.out.println("Lets Revive you!");
//                if (collidedObject instanceof Orc) {
//                    collidedObject.getImageView().setY(260); //disappear
//                }
//
//                Island prevIsland = null;
//                for (int i = 0; i < gameObjects.size(); i++) {
//                    if (gameObjects.get(i) instanceof Island) {
//                        if (((Island) gameObjects.get(i)).getImageView().getBoundsInParent().getMinX() < hero.getImageView().getBoundsInParent().getMinX()) {
//                            prevIsland = (Island) gameObjects.get(i);
//                        }
//                    }
//
//                }
//                if (prevIsland != null) {
//                    hero.getImageView().setLayoutX(prevIsland.getImageView().getBoundsInParent().getMinX() + 10);
//                    hero.getImageView().setX(0);
//                    hero.getImageView().setLayoutY(prevIsland.getImageView().getBoundsInParent().getMinY() - 100);
//                    heroCollision = 0;//
//
//                    hero.getxMovementTimeline().play();
//                    hero.getyMovementTimeline().play();
//
//                }


            }
        }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        //add mouse click to anchor pane
        gamePlayAnchorPane.setOnMouseClicked(e -> {
            if (heroCollision == 0) {

                hero.moveHero(60);
                throwWeapon(hero);
                moves++;

                //move all game objects to the left
                Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(10), e1 -> {
                    for (int i = 0; i < gameObjects.size(); i++) {

                        gameObjects.get(i).getImageView().setLayoutX(gameObjects.get(i).getImageView().getLayoutX() - 2);
                        gameObjects.get(i).setLayoutXY((float) gameObjects.get(i).getImageView().getLayoutX(), (float) gameObjects.get(i).getImageView().getLayoutY());

                        if (gameObjects.get(i) instanceof TNT) {
                            ((TNT) gameObjects.get(i)).moveAllTNTcomponents(-2);
                        }

                    }
                }
                ));
                timeline1.setCycleCount(30);
                timeline1.play();

            }
        });


    }
}
