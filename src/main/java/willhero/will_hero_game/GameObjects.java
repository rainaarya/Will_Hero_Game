package willhero.will_hero_game;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;


public abstract class GameObjects implements Serializable {
    private float [] position;
    private float [] coordinates;
    private String objectType;

    public GameObjects(float x, float y) {
        position = new float[2];
        coordinates = new float[2];
        position[0] = x;
        position[1] = y;
        coordinates[0] = 0;
        coordinates[1] = 0;

    }

    public GameObjects(float [] positionArr) {
        this.position = positionArr;
    }

    public abstract boolean onCollide(GameObjects collider);
    public abstract void display(AnchorPane gameAnchor);
    public abstract ImageView getImageView();

    public float getLayoutX() {return position[0];}
    public float getLayoutY() {return position[1];}
    public float getCoordinatesX() {return coordinates[0];}
    public float getCoordinatesY() {return coordinates[1];}
    public String getObjectType() {return objectType;}
    public void setObjectType(String p_type) {this.objectType = p_type;}
    public void setLayoutXY(float x,float y) {
        this.position[0] = x;
        this.position[1] = y;
    }
    public void setXY(float x,float y) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }

}
