package willhero.will_hero_game;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Random;

public class ThrowingKnife extends GameObjects {
    transient ImageView imageView;
    private transient String path;
    private Timeline timeline;
    private Hero hero;

    public ImageView getImageView() {
        return imageView;
    }

    private void setPathName() {


        path = "ThrowingKnife" + ".png";
    }


    ThrowingKnife(float x, float y, Hero hero) {
        super(x, y);
        this.hero = hero;
        setPathName();
        imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        imageView.setVisible(false);
        setObjectType("ThrowingKnife");
    }

    @Override
    public boolean onCollide(GameObjects collider) {
        if (imageView.isVisible()) {
            if (collider instanceof Orc) {
                if (((Orc) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    //TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), ((Orc) collider).getImageView());
                    ((Orc) collider).getImageView().setY(260);


                    if (hero.getweapon1Level() == 1) {
                        timeline.stop();
                        imageView.setY(260);
                        imageView.setVisible(false);
                    }
                    if(hero.getweapon1Level() == 2){
                        //continue throwing knife
                    }


                } else {
                    //System.out.println("No collision with Orc");
                }
            }
        }
        return false;
    }

    @Override
    public void display(AnchorPane gamePane) {
        gamePane.getChildren().add(imageView);
        timeline = new Timeline(new KeyFrame(Duration.millis(12), e -> {
            imageView.setX(imageView.getX() + 4);
        }
        ));
        timeline.setCycleCount(70);
        //timeline.play();
        //on timeline end, set imageView to invisible
        timeline.setOnFinished(e -> {
            imageView.setY(260);
            imageView.setVisible(false);
        });
    }

    public void throwKnife() {
        if (hero.getweapon1Level() == 1) {
            imageView.setLayoutX(hero.getImageView().getBoundsInParent().getMaxX());
            imageView.setLayoutY(hero.getImageView().getBoundsInParent().getMinY() + hero.getImageView().getBoundsInParent().getHeight() / 2);
            imageView.setVisible(true);
            timeline.play();
        } else if (hero.getweapon1Level() == 2) {
            imageView.setImage(new Image(getClass().getResourceAsStream("ThrowingKnifeUpgrade.png")));
            imageView.setLayoutX(hero.getImageView().getBoundsInParent().getMaxX());
            imageView.setLayoutY(hero.getImageView().getBoundsInParent().getMinY() + hero.getImageView().getBoundsInParent().getHeight() / 2);
            imageView.setVisible(true);
            timeline.play();
        }
    }

}
