package willhero.will_hero_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class WeaponChest extends Chest {

    private boolean collided = false;

    private int generateRandomWeapon() {
        //generate random number from 1 to 2
        return (int) (Math.random() * 2) + 1;
    }

    public WeaponChest(float x, float y) {
        super(x, y, "weaponchest.png");
    }


    @Override
    public boolean onCollide(GameObjects collider) {
        if (collider instanceof Hero) {
            if (((Hero) collider).getImageView().getBoundsInParent().intersects(getImageView().getBoundsInParent())) {
                if (!collided) {
                    int weapon = generateRandomWeapon();
                    System.out.println("WeaponChest Weapon: " + weapon);
                    ((Hero) collider).addWeapon(weapon);
                    collided = true;
                    getImageView().setImage(new Image(getClass().getResourceAsStream("weaponchest_open.png")));
                    getImageView().setFitWidth(70);

                }
            }

        }
        return false;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
        if (collided) {
            getImageView().setImage(new Image(getClass().getResourceAsStream("weaponchest_open.png")));
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
