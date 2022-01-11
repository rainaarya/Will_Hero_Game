package willhero.will_hero_game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Boss extends Orc {

    private int health = 30;
    private transient Timeline timeline0;
    private transient Timeline timeline;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health += health;
    }

    Boss(float x, float y) {
        super(x, y);
        getImageView().setFitWidth(100);
        getImageView().setImage(new Image(getClass().getResourceAsStream("Orc1.png")));

    }

    @Override
    public boolean onCollide(GameObjects collider) {

        AnchorPane anch = (AnchorPane) getImageView().getParent();
        if (anch != null) {
            if (collider instanceof Hero) {
                if (((Hero) collider).getImageView().getBoundsInParent().intersects(getImageView().getBoundsInParent())) {
                    if (Math.abs(((Hero) collider).getImageView().getBoundsInParent().getMinY() - getImageView().getBoundsInParent().getMaxY()) <= 3) {
                        //System.out.println("hero is below orc");
                        getTimeline2().stop();
                        return true;

                    } else if (Math.abs(((Hero) collider).getImageView().getBoundsInParent().getMaxY() - getImageView().getBoundsInParent().getMinY()) <= 5) {
                        //System.out.println("hero is above orc");
                        ((Hero) collider).setDy(1);

                    } else {
                        //System.out.println(getImageView().getBoundsInParent().getMaxY() + ", " + ((Hero) collider).getImageView().getBoundsInParent().getMinY());
                        //((Hero) collider).moveHeroBackX(20);
                        timeline0 = new Timeline(new KeyFrame(Duration.millis(10), e -> {
                            ((Hero) collider).moveHeroBackX(2);
                        }
                        ));
                        timeline0.setCycleCount(2);


                        timeline = new Timeline(new KeyFrame(Duration.millis(5), e -> {
                            getImageView().setX(getImageView().getX() + 1);

                            setXY((float) getImageView().getX(), (float) getImageView().getY());
                        }
                        ));
                        timeline.setCycleCount(3);
                        timeline.play();
                        timeline0.play();
                        //getImageView().setX(getImageView().getX() + 30);
                        //setXY((float) getImageView().getX(), (float) getImageView().getY());
                    }

                }

            }
            if (collider instanceof Orc) {
                if ((Orc) collider != this) {
                    if (((Orc) collider).getImageView().getBoundsInParent().intersects(getImageView().getBoundsInParent())) {
                        if (((Orc) collider).getImageView().getLayoutX() > getImageView().getLayoutX()) {
                            ((Orc) collider).getImageView().setX(((Orc) collider).getImageView().getX() + 20);
                            ((Orc) collider).setXY((float) ((Orc) collider).getImageView().getX(), (float) ((Orc) collider).getImageView().getY());
                            return false;
                        } else {
                            ((Orc) collider).getImageView().setX(getImageView().getX() + 20);
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
    public void cleanup(AnchorPane anchorPane) {
        if(timeline != null)
            timeline.stop();
        if(timeline0 != null)
            timeline0.stop();
        if(getTimeline2() != null)
            getTimeline2().stop();
        anchorPane.getChildren().remove(getImageView());
    }
}
