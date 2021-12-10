package willhero.will_hero_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Island extends GameObjects {
    private transient String imagepath;
    transient ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }


    Island(float x, float y) {
        super(x, y);
        imageView = new ImageView(new Image(getClass().getResourceAsStream("T_islands_01.png")));
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        setObjectType("Island");
    }

    @Override
    public boolean onCollide(GameObjects collider) {
        AnchorPane anch = (AnchorPane) imageView.getParent();
        if (anch != null) {
            if (collider instanceof Hero) {
                if (((Hero) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    ((Hero) collider).dy = 1;
                    return false;
                }
            } else if (collider instanceof Orc) {
                if (((Orc) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    ((Orc) collider).dy = 1;
                    return false;
                }
            }

        }
        return false;
    }

    @Override
    public void display(AnchorPane gamePane) {
        gamePane.getChildren().add(imageView);


    }

}
