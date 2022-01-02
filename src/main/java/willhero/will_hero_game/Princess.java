package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Princess extends GameObjects {
    private transient ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    private int dy = 1;
    public void setDy(int dy) {
        this.dy = dy;
    }
    private transient Timeline timeline2;
    public Timeline getTimeline2() {
        return timeline2;
    }


    Princess(float x, float y) {
        super(x, y);
        imageView = new ImageView(new Image(getClass().getResourceAsStream("Princess.png")));
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(41);
        imageView.setPreserveRatio(true);
    }

    @Override
    public boolean onCollide(GameObjects collider) {
        return false;
    }

    @Override
    public void display(AnchorPane gamePane) {
        gamePane.getChildren().add(imageView);
        if (imageView.getY() < 250) {
            timeline2 = new Timeline(new KeyFrame(Duration.millis(8), e -> {
                imageView.setY(imageView.getY() - dy);
                setXY((float) imageView.getX(), (float) imageView.getY());
                if (imageView.getY() == -80) {
                    dy = -1;
                }

//                if (imageView.getY() > 250) {
//                    timeline2.stop();
//                    imageView.setVisible(false);
//                }
            }
            ));
            timeline2.setCycleCount(Timeline.INDEFINITE);
            timeline2.play();
        }
    }

    @Override
    public void cleanup(AnchorPane gamePane) {
        gamePane.getChildren().remove(imageView);
        if(timeline2 != null) {
            timeline2.stop();
        }
    }

}
