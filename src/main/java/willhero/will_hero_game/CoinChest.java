package willhero.will_hero_game;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class CoinChest extends Chest {

    private boolean collided = false;

    public CoinChest(float x, float y) {
        super(x, y, "coinchest.png");
        setObjectType("CoinChest");
    }


    @Override
    public boolean onCollide(GameObjects collider) {
        if (collider instanceof Hero) {
            if (((Hero) collider).getImageView().getBoundsInParent().intersects(getImageView().getBoundsInParent())) {
                if (!collided) {

                    System.out.println("You found 15 coins!");
                    Game.setCoins(15);
                    collided = true;
                    getImageView().setImage(new Image(getClass().getResourceAsStream("coinchest_opened.png")));
                    getImageView().setFitWidth(70);

                }
            }

        }
        return false;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
        if (collided) {
            getImageView().setImage(new Image(getClass().getResourceAsStream("coinchest_opened.png")));
        }
    }

    public boolean getCollided() {
        return collided;
    }

    @Override
    public void cleanup(AnchorPane anchorPane) {
        if(getTimeline() != null) {
            getTimeline().stop();
        }
        anchorPane.getChildren().remove(getImageView());
    }


}
