package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Orc extends GameObjects {
    private String imagepath;
    private transient ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public int dy = 1;
    private transient Timeline timeline2;
    public Timeline getTimeline2() {
        return timeline2;
    }


    Orc(float x, float y) {
        super(x, y);
        imageView = new ImageView(new Image(getClass().getResourceAsStream("big.png")));
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(41);
        imageView.setPreserveRatio(true);
        setObjectType("Orc");
    }

    @Override
    public boolean onCollide(GameObjects collider) {

        AnchorPane anch = (AnchorPane) imageView.getParent();
        if (anch != null) {
            if (collider instanceof Hero) {
                if (((Hero) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    if (Math.abs(((Hero) collider).getImageView().getBoundsInParent().getMinY() - imageView.getBoundsInParent().getMaxY()) <= 3) {
                        //System.out.println("hero is below orc");
                        timeline2.stop();
                        return true;

                    } else if (Math.abs(((Hero) collider).getImageView().getBoundsInParent().getMaxY() - imageView.getBoundsInParent().getMinY()) <= 5) {
                        //System.out.println("hero is above orc");
                        ((Hero) collider).dy = 1;

                    } else {
                        //System.out.println(imageView.getBoundsInParent().getMaxY() + ", " + ((Hero) collider).getImageView().getBoundsInParent().getMinY());
                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), e -> {
                            imageView.setX(imageView.getX() + 2);
                            setXY((float) imageView.getX(), (float) imageView.getY());
                        }
                        ));
                        timeline.setCycleCount(5);
                        timeline.play();
                        //imageView.setX(imageView.getX() + 30);
                        //setXY((float) imageView.getX(), (float) imageView.getY());
                    }

                }

            }
            if (collider instanceof Orc) {
                if ((Orc) collider != this) {
                    if (((Orc) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                        if (((Orc) collider).getImageView().getLayoutX() > imageView.getLayoutX()) {
                            ((Orc) collider).getImageView().setX(((Orc) collider).getImageView().getX() + 20);
                            ((Orc) collider).setXY((float) ((Orc) collider).getImageView().getX(), (float) ((Orc) collider).getImageView().getY());
                            return false;
                        } else {
                            ((Orc) collider).getImageView().setX(imageView.getX() + 20);
                            ((Orc) collider).setXY((float) ((Orc) collider).getImageView().getX(), (float) ((Orc) collider).getImageView().getY());
                            return false;
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
        if (imageView.getY() < 250) {
            timeline2 = new Timeline(new KeyFrame(Duration.millis(8), e -> {
                //System.out.println(hero.getX() + ", " + hero.getY());
                //System.out.println(hero.getLayoutX() + ", " + hero.getLayoutY());
                //System.out.println(imageView.getX() + ", " + imageView.getY());
                imageView.setY(imageView.getY() - dy);
                setXY((float) imageView.getX(), (float) imageView.getY());
                if (imageView.getY() == -100) {

                    dy = -1;

                }
//            if (hero.getY() == 0) {
//                dy.set(1);
//            }

                // check if hero is on island

                if (imageView.getY() > 250) {
                    System.out.println("orc dead");
                    timeline2.stop();
                    imageView.setVisible(false);
                    Test.setCoins(1);


                }
            }
            ));
            timeline2.setCycleCount(Timeline.INDEFINITE);
            timeline2.play();
        }
    }

}
