package willhero.will_hero_game;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

import static javafx.scene.paint.Color.*;

public class TNT extends GameObjects {
    private transient ImageView imageView;
    private int dy = 1;
    private float[] circlePositionandRadius;
    private transient Circle circle;
    private boolean collided;
    private boolean isExploded;
    private transient Timeline timeline;

    public boolean getisExploded() {
        return isExploded;
    }

    public boolean getCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public void setisExploded(boolean isExploded) {
        this.isExploded = isExploded;
    }

    public void setCircleLayoutXY(float x, float y, float radius) {
        circlePositionandRadius[0] = x;
        circlePositionandRadius[1] = y;
        circlePositionandRadius[2] = radius;

    }

    public float getCircleLayoutX() {
        return circlePositionandRadius[0];
    }

    public float getCircleLayoutY() {
        return circlePositionandRadius[1];
    }

    public float getRadius() {
        return circlePositionandRadius[2];
    }

    public void makeCircle(float x, float y, float radius) {
        circle = new Circle(x, y, radius);
        circlePositionandRadius[0] = x;
        circlePositionandRadius[1] = y;
        circlePositionandRadius[2] = radius;
        System.out.println(x+" "+y+" "+radius);
        circle.setFill(RED);
        circle.setStroke(BLACK);
        circle.setStrokeWidth(1);
        circle.setVisible(false);
    }


    public void setCheckedAllNearbyEntities(boolean checkedAllNearbyEntities) {
        isExploded = false;
        imageView.setVisible(false);
        imageView.setY(260);
        setXY((float) imageView.getX(), (float) imageView.getY());
        FadeTransition ft = new FadeTransition(Duration.millis(3000), circle);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.play();
        ft.setOnFinished(event -> {
            circle.setCenterY(260);
            System.out.println("TNT is gone");
        });


    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void moveAllTNTcomponents(float x) {
        circle.setCenterX(circle.getCenterX() + x);
        setCircleLayoutXY((float) circle.getCenterX(), (float) circle.getCenterY(), (float) circle.getRadius());
    }

    TNT(float x, float y) {
        super(x, y);
        imageView = new ImageView(new Image(getClass().getResourceAsStream("TNT.png")));
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(41);
        imageView.setPreserveRatio(true);
        circlePositionandRadius = new float[3];

        setObjectType("TNT");
    }

    @Override
    public boolean onCollide(GameObjects collider) {

        AnchorPane anch = (AnchorPane) imageView.getParent();
        if (anch != null) {
            if (!collided) {
                if (collider instanceof Hero || collider instanceof Orc) {
                    if (collider.getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                        timeline.play();
                        collided = true;
                    }

                }
                if(collider instanceof ThrowingKnife){
                    if(collider.getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())){
                        timeline.play();
                        collided = true;
                    }

                }
                if(collider instanceof Shuriken){
                    if(collider.getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())){
                        timeline.play();
                        collided = true;
                    }
                }
            }
            if (collided && isExploded) {
                circle.setVisible(true);
                System.out.println("TNT is exploding");
                if (collider instanceof Hero || collider instanceof Orc) {
                    if (collider.getImageView().getBoundsInParent().intersects(circle.getBoundsInParent())) {
                        if (collider instanceof Hero) {
                            return true;
                        }
                        if (collider instanceof Orc) {
                            ((Orc) collider).getImageView().setY(260);
                            ((Orc) collider).setXY((float) ((Orc) collider).getImageView().getX(), (float) ((Orc) collider).getImageView().getY());

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
        imageView.toFront();
        if (circle == null) {
            makeCircle((float) imageView.getBoundsInParent().getCenterX(), (float) imageView.getBoundsInParent().getCenterY(), 80);

        }
        gamePane.getChildren().add(circle);
        circle.toBack();

        if (imageView.getY() < 250) {
            timeline = new Timeline(new KeyFrame(Duration.millis(18), e -> {
                imageView.setY(imageView.getY() - dy);
                //setXY((float) imageView.getX(), (float) imageView.getY());
                if (imageView.getY() == -18) {
                    dy = -1;
                }

                if (imageView.getY() > 250) {
                    System.out.println("TNT burst finished");
                    timeline.stop();
                    imageView.setVisible(false);

                }
            }
            ));
            timeline.setCycleCount(100);
            //on timeline finished
            timeline.setOnFinished(e -> {
                isExploded = true;

            });


        }
        if (collided && !isExploded) {
            if (imageView.getY() < 250) {
                timeline.play();
            }
        }
    }

    @Override
    public void cleanup(AnchorPane gamePane){
        gamePane.getChildren().remove(imageView);
        gamePane.getChildren().remove(circle);
        if(timeline != null){
            timeline.stop();
        }
    }

}
