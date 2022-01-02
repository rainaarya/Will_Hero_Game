package willhero.will_hero_game;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Random;

public class Shuriken extends GameObjects {
    private transient ImageView imageView;
    private transient ImageView imageView2;
    private transient String path;
    private transient Timeline timeline;
    private transient Timeline timeline2;
    private transient RotateTransition rotateTransition;
    private transient RotateTransition rotateTransition2;
    private Hero hero;

    public ImageView getImageView() {
        return imageView;
    }

    private void setPathName() {


        path = "Shuriken" + ".png";
    }


    Shuriken(float x, float y, Hero hero) {
        super(x, y);
        this.hero = hero;
        setPathName();
        imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        imageView.setVisible(false);

        imageView2 = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView2.setLayoutX(x);
        imageView2.setLayoutY(y);
        imageView2.setFitWidth(40);
        imageView2.setPreserveRatio(true);
        imageView2.setVisible(false);

        //add rotation animation to imageView and imageView2
        rotateTransition = new RotateTransition(Duration.millis(500), imageView);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(true);

        rotateTransition2 = new RotateTransition(Duration.millis(500), imageView2);
        rotateTransition2.setByAngle(360);
        rotateTransition2.setCycleCount(1);
        rotateTransition2.setAutoReverse(true);

    }

    @Override
    public boolean onCollide(GameObjects collider) {
        if (imageView.isVisible() || (hero.getweapon2Level() == 2 && imageView2.isVisible())) {
            if (imageView.isVisible()) {
                if (collider instanceof Orc) {
                    if (collider instanceof Boss) {

                        if (((Boss) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                            System.out.println(((Boss) collider).getHealth());
                            //TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), ((Orc) collider).getImageView());
                            if (((Boss) collider).getHealth() == 1) {
                                ((Boss) collider).getImageView().setY(260);
                                ((Boss) collider).setXY((float) ((Boss) collider).getImageView().getX(), (float) ((Boss) collider).getImageView().getY());
                                ((Boss) collider).setHealth(-1);
                            }
                            ((Boss) collider).setHealth(-1);


                            timeline.stop();
                            imageView.setY(260);
                            setXY((float) imageView.getX(), (float) imageView.getY());
                            imageView.setVisible(false);


                        } else {
                            //System.out.println("No collision with Orc");
                        }

                    } else {
                        if (((Orc) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                            //TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), ((Orc) collider).getImageView());
                            ((Orc) collider).getImageView().setY(260);
                            ((Orc) collider).setXY((float) ((Orc) collider).getImageView().getX(), (float) ((Orc) collider).getImageView().getY());


                            timeline.stop();
                            imageView.setY(260);
                            setXY((float) imageView.getX(), (float) imageView.getY());
                            imageView.setVisible(false);


                        } else {
                            //System.out.println("No collision with Orc");
                        }
                    }

                }
            }
            if (hero.getweapon2Level() == 2 && imageView2.isVisible()) {

                if (collider instanceof Orc) {
                    if (collider instanceof Boss) {

                        if (((Boss) collider).getImageView().getBoundsInParent().intersects(imageView2.getBoundsInParent())) {
                            System.out.println(((Boss) collider).getHealth());
                            //TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), ((Orc) collider).getImageView());
                            if (((Boss) collider).getHealth() == 1) {
                                ((Boss) collider).getImageView().setY(260);
                                ((Boss) collider).setXY((float) ((Boss) collider).getImageView().getX(), (float) ((Boss) collider).getImageView().getY());
                                ((Boss) collider).setHealth(-1);
                            }
                            ((Boss) collider).setHealth(-1);


                            timeline2.stop();
                            imageView2.setY(260);
                            //setXY((float) imageView.getX(), (float) imageView.getY());
                            imageView2.setVisible(false);


                        } else {
                            //System.out.println("No collision with Orc");
                        }

                    } else {
                        if (((Orc) collider).getImageView().getBoundsInParent().intersects(imageView2.getBoundsInParent())) {
                            //TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), ((Orc) collider).getImageView());
                            ((Orc) collider).getImageView().setY(260);
                            ((Orc) collider).setXY((float) ((Orc) collider).getImageView().getX(), (float) ((Orc) collider).getImageView().getY());


                            timeline2.stop();
                            imageView2.setY(260);
                            //setXY((float) imageView.getX(), (float) imageView.getY());
                            imageView2.setVisible(false);


                        } else {
                            //System.out.println("No collision with Orc");
                        }
                    }

                }

            }
        }
        return false;
    }

    @Override
    public void display(AnchorPane gamePane) {
        gamePane.getChildren().add(imageView);
        if (hero.getweapon2Level() == 2) {
            gamePane.getChildren().add(imageView2);
        }
        timeline = new Timeline(new KeyFrame(Duration.millis(7), e -> {
            imageView.setX(imageView.getX() + 4);
            setXY((float) imageView.getX(), (float) imageView.getY());
        }
        ));
        timeline.setCycleCount(70);
        //timeline.play();
        //on timeline end, set imageView to invisible
        timeline.setOnFinished(e -> {
            imageView.setY(260);
            imageView.setVisible(false);
            rotateTransition.stop();

        });
        timeline2 = new Timeline(new KeyFrame(Duration.millis(13), e -> {
            imageView2.setX(imageView2.getX() + 4);
            imageView2.setY(imageView2.getY() - 2);
            //setXY((float) imageView2.getX(), (float) imageView2.getY());

        }));
        timeline2.setCycleCount(70);
        timeline2.setOnFinished(e -> {
            imageView2.setY(260);
            imageView2.setVisible(false);
            rotateTransition2.stop();
        });

    }

    public void throwShuriken() {
        imageView.setLayoutX(hero.getImageView().getBoundsInParent().getMaxX());
        imageView.setLayoutY(hero.getImageView().getBoundsInParent().getMinY() + hero.getImageView().getBoundsInParent().getHeight() / 2);
        if (hero.getweapon2Level() == 2) {
            imageView2.setLayoutX(hero.getImageView().getBoundsInParent().getMaxX());
            imageView2.setLayoutY(hero.getImageView().getBoundsInParent().getMinY() + hero.getImageView().getBoundsInParent().getHeight() / 2);
        }
        setLayoutXY((float) imageView.getLayoutX(), (float) imageView.getLayoutY());
        imageView.setVisible(true);
        if (hero.getweapon2Level() == 2) {
            imageView2.setVisible(true);
        }
        rotateTransition.play();
        timeline.play();
        if (hero.getweapon2Level() == 2) {
            rotateTransition2.play();
            timeline2.play();
        }

    }

    @Override
    public void cleanup(AnchorPane gamePane) {
        gamePane.getChildren().remove(imageView);
        gamePane.getChildren().remove(imageView2);
        if (timeline != null) {
            timeline.stop();
        }
        if (timeline2 != null) {
            timeline2.stop();
        }
        if (rotateTransition != null) {
            rotateTransition.stop();
        }
        if (rotateTransition2 != null) {
            rotateTransition2.stop();
        }

    }

}
