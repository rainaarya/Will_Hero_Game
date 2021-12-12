package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Random;

public class Cloud extends GameObjects {
    transient ImageView imageView;
    private transient String path;

    public ImageView getImageView() {
        return imageView;
    }

    private void setPathName() {
        Random ran = new Random();
        int x = ran.nextInt(6) + 1;
        path = "Cloud_" + x + ".png";
    }


    Cloud(float x, float y) {
        super(x, y);
        setPathName();
        imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitHeight(70);
        imageView.setPreserveRatio(true);
        setObjectType("Cloud");
    }

    @Override
    public boolean onCollide(GameObjects collider) {
        return false;
    }

    @Override
    public void display(AnchorPane gamePane) {
        gamePane.getChildren().add(imageView);
    }

}
