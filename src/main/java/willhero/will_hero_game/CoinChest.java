package willhero.will_hero_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
                    Test.setCoins(15);
                    collided = true;
                    getImageView().setImage(new Image(getClass().getResourceAsStream("coinchest_opened.png")));
                    getImageView().setFitWidth(70);

                }
            }

        }
        return false;
    }


}
