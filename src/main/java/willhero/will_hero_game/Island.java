package willhero.will_hero_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Random;

public class Island extends GameObjects {
    transient ImageView imageView;
    private transient String path;

    public ImageView getImageView() {
        return imageView;
    }

    private void setPathName() {
        Random ran = new Random();
        int x = ran.nextInt(4) + 1;
        path = "T_Islands_0" + x + ".png";
    }


    Island(float x, float y) {
        super(x, y);
        setPathName();
        imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
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
                    //System.out.println(((Hero) collider).getImageView().getBoundsInParent().getMaxY() + " " + imageView.getBoundsInParent().getMinY());

                    if (((Hero) collider).getImageView().getBoundsInParent().getMaxY()-3 <= imageView.getBoundsInParent().getMinY()) {
                        //System.out.println("on island");
                        ((Hero) collider).dy = 1;
                        return false;
                    } else {
                        return true;
                    }

                }
            } else if (collider instanceof Orc) {
                if (((Orc) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    if (((Orc) collider).getImageView().getBoundsInParent().getMaxY()-3 <= imageView.getBoundsInParent().getMinY()) {
                        //System.out.println("on island");
                        ((Orc) collider).dy = 1;
                        return false;
                    }
//
//                    ((Orc) collider).dy = 1;
//                    return false;
                }
            }
            else if (collider instanceof Chest) {
                if (((Chest) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    ((Chest) collider).setDy(1);
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
