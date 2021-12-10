package willhero.will_hero_game;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;


public abstract class GameObjects {
    private float [] position;
    private String objectType;
    private int opacity;

    public GameObjects(float x, float y) {
        position = new float[2];
        position[0] = x;
        position[1] = y;

    }

    public GameObjects(float [] positionArr) {
        this.position = positionArr;
    }

    public abstract boolean onCollide(GameObjects collider);
    public abstract void display(AnchorPane gameAnchor);
    public abstract ImageView getImageView();

    public float[] getPosition() {return position;}
    public float getPositionX() {return position[0];}
    public float getPositionY() {return position[1];}
    public String getObjectType() {return objectType;}
    public void setPosition(float[] position) {this.position = position;}
    public void setObjectType(String p_type) {this.objectType = p_type;}
    public void setPosition(float x,float y) {
        this.position[0] = x;
        this.position[1] = y;
    }

}
