package willhero.will_hero_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Random;

public class Island extends GameObjects {
    private transient ImageView imageView;
    private String path;
    private transient MediaPlayer mediaPlayer;

    public ImageView getImageView() {
        return imageView;
    }

    public void setPathName() {
        Random ran = new Random();
        int x = ran.nextInt(4) + 1;
        path = "T_Islands_0" + x + ".png";
    }
    public String getPathName(){
        return path;
    }



    Island(float x, float y) {
        super(x, y);
        if(path == null) {
            setPathName();
        }
        imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        setObjectType("Island");
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource("jump.mp3").toString()));
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.stop();
                //mediaPlayer.seek(mediaPlayer.getStartTime());
            }
        }
        );

    }

    @Override
    public boolean onCollide(GameObjects collider) {
        AnchorPane anch = (AnchorPane) imageView.getParent();
        if (anch != null) {
            if (collider instanceof Hero) {
                if (((Hero) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    //System.out.println(((Hero) collider).getImageView().getBoundsInParent().getMaxY() + " " + imageView.getBoundsInParent().getMinY());

                    if (((Hero) collider).getImageView().getBoundsInParent().getMaxY()-20 <= imageView.getBoundsInParent().getMinY()) {
                        //System.out.println("on island");
                        ((Hero) collider).setDy(1);
                        //check if media player is currently playing
                        if (!mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                            mediaPlayer.play();
                        }
                        //System.out.println("on island");
                        return false;
                    } else {
                        return true;
                    }

                }
            } else if (collider instanceof Orc) {
                if (((Orc) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    if (((Orc) collider).getImageView().getBoundsInParent().getMaxY()-40 <= imageView.getBoundsInParent().getMinY()) {
                        //System.out.println("on island");
                        ((Orc) collider).setDy(1);
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
            else if (collider instanceof TNT) {
                if (((TNT) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    ((TNT) collider).setDy(1);
                }
            }
            else if(collider instanceof Princess) {
                if (((Princess) collider).getImageView().getBoundsInParent().intersects(imageView.getBoundsInParent())) {
                    ((Princess) collider).setDy(1);
                }
            }

        }
        return false;
    }

    @Override
    public void display(AnchorPane gamePane) {
        gamePane.getChildren().add(imageView);
    }

    @Override
    public void cleanup(AnchorPane gamePane) {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }
        gamePane.getChildren().remove(imageView);
    }

}
