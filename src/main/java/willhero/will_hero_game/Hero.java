package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Hero extends GameObjects {
    private transient ImageView imageView;
    private int moves;
    private ArrayList<GameObjects> gameObjects;

    public ImageView getImageView() {
        return imageView;
    }

    private int dy = 1;
    public void setDy(int dy) {
        this.dy = dy;
    }
    private transient Timeline yMovementTimeline;
    private transient Timeline xMovementTimeline;
    private int weapon1Level = 0;
    private int weapon2Level = 0;
    private int currentWeapon = 0;

    public int getweapon1Level() {
        return weapon1Level;
    }
    public int getweapon2Level() {
        return weapon2Level;
    }
    public int getcurrentWeapon() {
        return currentWeapon;
    }

    public void setweapon1Level(int level) {
        this.weapon1Level = level;
    }
    public void setweapon2Level(int level) {
        this.weapon2Level = level;
    }
    public void setcurrentWeapon(int weapon) {
        this.currentWeapon = weapon;
    }

    public void moveHeroBackX(float x){
        imageView.setX(imageView.getX() - x);
        setXY((float) imageView.getX(), (float) imageView.getY());
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).getImageView().setLayoutX(gameObjects.get(i).getImageView().getLayoutX() + x);
            gameObjects.get(i).setLayoutXY((float) gameObjects.get(i).getImageView().getLayoutX(), (float) gameObjects.get(i).getImageView().getLayoutY());

            if (gameObjects.get(i) instanceof TNT) {
                ((TNT) gameObjects.get(i)).moveAllTNTcomponents(x);
            }
        }
    }


    public Timeline getyMovementTimeline() {
        return yMovementTimeline;
    }

    public Timeline getxMovementTimeline() {
        return xMovementTimeline;
    }

    public void setGameObjects(ArrayList<GameObjects> gameObjects) {
        this.gameObjects = gameObjects;
    }


    Hero(float x, float y) {
        super(x, y);
        imageView = new ImageView(new Image(getClass().getResourceAsStream("Knight.png")));
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(37);
        imageView.setPreserveRatio(true);
        setObjectType("Hero");
    }

    @Override
    public boolean onCollide(GameObjects collider) {

        if (imageView.getY() > 160) {
            xMovementTimeline.stop();
            yMovementTimeline.stop();
            return true;


        }
        return false;


    }

    @Override
    public void display(AnchorPane gamePane) {
        gamePane.getChildren().add(imageView);
        yMovementTimeline = new Timeline(new KeyFrame(Duration.millis(7), e -> {
            //System.out.println(hero.getX() + ", " + hero.getY());
            //System.out.println(hero.getLayoutX() + ", " + hero.getLayoutY());
            //System.out.println(imageView.getX() + ", " + imageView.getY());
            imageView.setY(imageView.getY() - dy);
            //System.out.println(imageView.getY());
            setXY((float) imageView.getX(), (float) imageView.getY());
            if (dy == 1) {
                moves++;
            }
            if (moves == 80) {

                dy = -1;
                moves = 0;

            }
//            if (hero.getY() == 0) {
//                dy.set(1);
//            }

            // check if hero is on island


        }
        ));
        yMovementTimeline.setCycleCount(Timeline.INDEFINITE);
        yMovementTimeline.play();

        xMovementTimeline = new Timeline(new KeyFrame(Duration.millis(9), e -> {

            imageView.setX(imageView.getX() + 10);
            //System.out.println(imageView.getX() + " " + imageView.getY());
            //System.out.println("Layout " + imageView.getLayoutX() + ", " + imageView.getLayoutY());
            //setXY((float) imageView.getX(), (float) imageView.getY());

        }
        ));
    }

    //move hero timeline
    public void moveHero(int dx) {

        xMovementTimeline.setCycleCount(6);
        xMovementTimeline.play();
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("move.mp3").toString()));
        mediaPlayer.play();


    }


    public void addWeapon(int weapon) {
        if (weapon == 1) {
            if (weapon1Level == 0) {
                weapon1Level = 1;
                currentWeapon = 1;
            } else if (weapon1Level == 1) {
                weapon1Level = 2;
                currentWeapon = 1;
            }
        } else if (weapon == 2) {
            if (weapon2Level == 0) {
                weapon2Level = 1;
                currentWeapon = 2;
            } else if (weapon2Level == 1) {
                weapon2Level = 2;
                currentWeapon = 2;
            }
        }

    }

    @Override
    public void cleanup(AnchorPane gamePane){
        if(xMovementTimeline != null){
            xMovementTimeline.stop();
        }
        if(yMovementTimeline != null){
            yMovementTimeline.stop();
        }
        gamePane.getChildren().remove(imageView);
    }


}
