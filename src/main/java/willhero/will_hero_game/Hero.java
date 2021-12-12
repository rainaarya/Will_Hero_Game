package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

public class Hero extends GameObjects{
    private transient String imagepath;
    transient ImageView imageView;
    public ImageView getImageView() {
        return imageView;
    }
    public int dy = 1;
    private Timeline yMovementTimeline;
    private Timeline xMovementTimeline;

    public Timeline getyMovementTimeline() {
        return yMovementTimeline;
    }
    public Timeline getxMovementTimeline() {
        return xMovementTimeline;
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

        return false;

        }

        @Override
        public void display(AnchorPane gamePane) {
            gamePane.getChildren().add(imageView);
            AtomicInteger moves = new AtomicInteger();
            yMovementTimeline = new Timeline(new KeyFrame(Duration.millis(7), e -> {
                //System.out.println(hero.getX() + ", " + hero.getY());
                //System.out.println(hero.getLayoutX() + ", " + hero.getLayoutY());
                //System.out.println(imageView.getX() + ", " + imageView.getY());
                imageView.setY(imageView.getY() - dy);
                if(dy == 1) {
                    moves.getAndIncrement();
                }
                if (moves.get() == 80) {

                    dy = -1;
                    moves.set(0);

                }
//            if (hero.getY() == 0) {
//                dy.set(1);
//            }

                // check if hero is on island

                if (imageView.getY() > 100) {
                    System.out.println("hero dead");
                    xMovementTimeline.stop();
                    yMovementTimeline.stop();


                }
            }
            ));
            yMovementTimeline.setCycleCount(Timeline.INDEFINITE);
            yMovementTimeline.play();
        }

        //move hero timeline
        public void moveHero(int dx) {
            xMovementTimeline = new Timeline(new KeyFrame(Duration.millis(9), e -> {

                imageView.setX(imageView.getX() + 10);

            }
            ));
            xMovementTimeline.setCycleCount(6);
            xMovementTimeline.play();

        }

}
