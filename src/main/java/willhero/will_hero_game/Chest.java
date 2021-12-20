package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Chest extends GameObjects {
    private transient ImageView imageView;
    private int dy = 1;
    private transient Timeline timeline;

    protected Timeline getTimeline() {
        return timeline;
    }

    public Chest(float x, float y, String imagePath) {
        super(x, y);
        imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(60);
        imageView.setPreserveRatio(true);


    }

    public abstract boolean onCollide(GameObjects collider);

    @Override
    public void display(AnchorPane gamePane) {
        gamePane.getChildren().add(imageView);
        System.out.println("Chest added to gamePane");
        timeline = new Timeline(new KeyFrame(Duration.millis(18), e -> {
            imageView.setY(imageView.getY() - dy);
            setXY((float) imageView.getX(), (float) imageView.getY());
            if (imageView.getY() == -18) {
                dy = -1;
            }

        }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    @Override
    public void cleanup(AnchorPane gamePane) {
        if(timeline != null) {
            timeline.stop();
        }
        gamePane.getChildren().remove(imageView);
    }

}
