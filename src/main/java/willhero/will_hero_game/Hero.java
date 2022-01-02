package willhero.will_hero_game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
    private transient ImageView jetpack;
    private int moves;
    private ArrayList<GameObjects> gameObjects;
    private boolean isFlying;

    public boolean getisFlying() {
        return isFlying;
    }

    public void setisFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ImageView getJetpack() {
        return jetpack;
    }


    private int dy = 1;

    public void setDy(int dy) {
        this.dy = dy;
    }

    private transient Timeline yMovementTimeline;
    private transient Timeline xMovementTimeline;
    private transient TranslateTransition upanddown;
    private transient TranslateTransition upanddown2;
    private transient Timeline goUp;
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

    public void moveHeroBackX(float x) {
        imageView.setX(imageView.getX() - x);
        setXY((float) imageView.getX(), (float) imageView.getY());
        for (int i = 0; i < gameObjects.size(); i++) {
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

    public TranslateTransition getupanddown() {
        return upanddown;
    }

    public TranslateTransition getupanddown2() {
        return upanddown2;
    }

    public Timeline getgoUp() {
        return goUp;
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
        jetpack = new ImageView(new Image(getClass().getResourceAsStream("jetpack.png")));
        jetpack.setFitWidth(getImageView().getFitWidth());
        jetpack.setPreserveRatio(true);
        //place jetpack below the hero
        jetpack.setLayoutY(getImageView().getLayoutY() + getImageView().getBoundsInParent().getHeight());
        jetpack.setY(getImageView().getY());
        //place jetpack in the middle of the hero
        jetpack.setLayoutX(getImageView().getLayoutX() + getImageView().getFitWidth() / 2 - jetpack.getFitWidth() / 2);
        jetpack.setX(getImageView().getX());
        //add jetpack to the game
        jetpack.setVisible(false);
        gamePane.getChildren().add(jetpack);


        yMovementTimeline = new Timeline(new KeyFrame(Duration.millis(7), e -> {
            //System.out.println(hero.getX() + ", " + hero.getY());
            //System.out.println(hero.getLayoutX() + ", " + hero.getLayoutY());
            //System.out.println(imageView.getX() + ", " + imageView.getY());
            imageView.setY(imageView.getY() - dy);
            jetpack.setY(jetpack.getY() - dy);
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
        if (!isFlying) {
            yMovementTimeline.play();
        } else {
            flywithJetpack();
        }

        xMovementTimeline = new Timeline(new KeyFrame(Duration.millis(9), e -> {

            imageView.setX(imageView.getX() + 10);
            //if ymovement is not playing, then play it
            if (!yMovementTimeline.getStatus().equals(Animation.Status.RUNNING)) {
                setXY((float) imageView.getX(), (float) imageView.getY());
            }
            jetpack.setX(jetpack.getX() + 10);
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

    public void moveHeroJetpack(int dx) {
        jetpack.setLayoutX(jetpack.getLayoutX() + dx);
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
    public void cleanup(AnchorPane gamePane) {
        if (xMovementTimeline != null) {
            xMovementTimeline.stop();
        }
        if (yMovementTimeline != null) {
            yMovementTimeline.stop();
        }
        if (upanddown != null) {
            upanddown.stop();
        }
        if (upanddown2 != null) {
            upanddown2.stop();
        }
        if (goUp != null) {
            goUp.stop();
        }
        gamePane.getChildren().remove(jetpack);
        gamePane.getChildren().remove(imageView);
    }

    private void flywithJetpack() {
        isFlying = true;

        //move jetpack up
        upanddown = new TranslateTransition(Duration.millis(300), getImageView());
        upanddown.setByY(30);
        upanddown.setCycleCount(Timeline.INDEFINITE);
        upanddown.setAutoReverse(true);
        upanddown2 = new TranslateTransition(Duration.millis(300), jetpack);
        upanddown2.setByY(30);
        upanddown2.setCycleCount(Timeline.INDEFINITE);
        upanddown2.setAutoReverse(true);
        goUp = new Timeline(new KeyFrame(Duration.millis(6), e -> {
            jetpack.setVisible(true);
            if (getImageView().getBoundsInParent().getMinY() > 120) {
                getImageView().setY(getImageView().getY() - 1);
                setXY((float) getImageView().getX(), (float) getImageView().getY());
                jetpack.setY(jetpack.getY() - 1);
            } else {
                if (upanddown.getStatus() != Animation.Status.RUNNING) {
                    jetpack.setVisible(true);
                    upanddown.play();
                    upanddown2.play();
                }
            }
        }
        ));

        goUp.setCycleCount(1000);
        goUp.setOnFinished(e -> {
            getyMovementTimeline().play();
            upanddown.stop();
            upanddown2.stop();
            jetpack.setVisible(false);
            isFlying = false;
        });
        goUp.play();

    }

    public void useJetPack(ImageView fly) {
        if (!fly.isDisabled()) {
            getyMovementTimeline().stop();
            flywithJetpack();
            fly.setDisable(true);
            fly.setOpacity(0.25);
            Game.setCoins(-5);
        }
    }


}
