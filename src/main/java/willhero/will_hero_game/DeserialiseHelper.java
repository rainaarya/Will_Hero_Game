package willhero.will_hero_game;

import javafx.scene.Group;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DeserialiseHelper {
    private ArrayList<GameObjects> myList;
    private Hero hero;
    private Integer moves;
    private Integer coins;
    private Integer heroCollision;
    private Integer timesRevived;
    private ArrayList<GameObjects> finalList = new ArrayList<>();


    public void deserialize(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        myList = new ArrayList<>();
        try {
            in = new ObjectInputStream(new FileInputStream(fileName));
            moves = (Integer) in.readObject();
            coins = (Integer) in.readObject();
            heroCollision = (Integer) in.readObject();
            timesRevived = (Integer) in.readObject();

            while (true) {
                try {
                    GameObjects tmp = (GameObjects) in.readObject();
                    if (tmp instanceof Hero) {
                        hero = new Hero(tmp.getLayoutX(), tmp.getLayoutY());
                        hero.getImageView().setX(tmp.getCoordinatesX());
                        hero.getImageView().setY(tmp.getCoordinatesY());
                        hero.setcurrentWeapon(((Hero) tmp).getcurrentWeapon());
                        hero.setweapon1Level(((Hero) tmp).getweapon1Level());
                        hero.setweapon2Level(((Hero) tmp).getweapon2Level());
                        finalList.add(hero);
                    }
                    myList.add(tmp);
                } catch (EOFException e) {
                    break;
                } catch (ClassCastException e) {
                    System.out.println("Invalid Class Cast Exception");
                }
            }
        } finally {
            assert in != null;
            in.close();
        }
    }

    public ArrayList<Integer> getGameInfo() throws IOException, ClassNotFoundException {
        ArrayList<Integer> gameInfo = new ArrayList<>();
        gameInfo.add(moves);
        gameInfo.add(coins);
        gameInfo.add(heroCollision);
        gameInfo.add(timesRevived);
        return gameInfo;

    }

    public ArrayList<GameObjects> regenerateGameObjects() throws IOException, ClassNotFoundException {


        for (int i = 0; i < myList.size(); i++) {
            GameObjects obj = myList.get(i);
            System.out.println(obj.getLayoutX() + "   " + obj.getLayoutY() + "   " + obj.getCoordinatesX() + "   " + obj.getCoordinatesY() + "   " + obj.getClass().getName());
//            if(obj.getObjectType().equals("Hero")) {
//                Hero hero = new Hero(obj.getLayoutX(), obj.getLayoutY());
//                hero.getImageView().setX(obj.getCoordinatesX());
//                hero.getImageView().setY(obj.getCoordinatesY());
//                finalList.add(hero);
//            }
            if (obj instanceof Cloud) {
                Cloud cloud = new Cloud(obj.getLayoutX(), obj.getLayoutY());
                cloud.getImageView().setX(obj.getCoordinatesX());
                cloud.getImageView().setY(obj.getCoordinatesY());
                finalList.add(cloud);
            } else if (obj instanceof CoinChest) {
                CoinChest coinChest = new CoinChest(obj.getLayoutX(), obj.getLayoutY());
                coinChest.getImageView().setX(obj.getCoordinatesX());
                coinChest.getImageView().setY(obj.getCoordinatesY());
                coinChest.setCollided(((CoinChest) obj).getCollided());
                finalList.add(coinChest);
            } else if (obj instanceof WeaponChest) {
                WeaponChest weaponChest = new WeaponChest(obj.getLayoutX(), obj.getLayoutY());
                weaponChest.getImageView().setX(obj.getCoordinatesX());
                weaponChest.getImageView().setY(obj.getCoordinatesY());
                weaponChest.setCollided(((WeaponChest) obj).getCollided());
                finalList.add(weaponChest);
            } else if (obj instanceof Island) {
                Island island = new Island(obj.getLayoutX(), obj.getLayoutY());
                island.getImageView().setX(obj.getCoordinatesX());
                island.getImageView().setY(obj.getCoordinatesY());
                finalList.add(island);
            } else if (obj instanceof Orc) {
                if (obj instanceof Boss) {
                    Boss orc = new Boss(obj.getLayoutX(), obj.getLayoutY());
                    orc.getImageView().setX(obj.getCoordinatesX());
                    orc.getImageView().setY(obj.getCoordinatesY());
                    orc.setHealth(-orc.getHealth());
                    orc.setHealth(((Boss) obj).getHealth());
                    finalList.add(orc);
                } else {
                    Orc orc = new Orc(obj.getLayoutX(), obj.getLayoutY());
                    orc.getImageView().setX(obj.getCoordinatesX());
                    orc.getImageView().setY(obj.getCoordinatesY());
                    finalList.add(orc);
                }

            } else if (obj instanceof ThrowingKnife) {
                ThrowingKnife throwingKnife = new ThrowingKnife(obj.getLayoutX(), obj.getLayoutY(), hero);
                throwingKnife.getImageView().setX(obj.getCoordinatesX());
                throwingKnife.getImageView().setY(obj.getCoordinatesY());
                finalList.add(throwingKnife);
            } else if (obj instanceof Shuriken) {
                Shuriken shuriken = new Shuriken(obj.getLayoutX(), obj.getLayoutY(), hero);
                shuriken.getImageView().setX(obj.getCoordinatesX());
                shuriken.getImageView().setY(obj.getCoordinatesY());
                finalList.add(shuriken);
            } else if (obj instanceof Trees) {
                Trees trees = new Trees(obj.getLayoutX(), obj.getLayoutY());
                trees.getImageView().setX(obj.getCoordinatesX());
                trees.getImageView().setY(obj.getCoordinatesY());
                finalList.add(trees);
            } else if (obj instanceof TNT) {
                TNT tnt = new TNT(obj.getLayoutX(), obj.getLayoutY());
                tnt.getImageView().setX(obj.getCoordinatesX());
                tnt.getImageView().setY(obj.getCoordinatesY());
                tnt.makeCircle(((TNT) obj).getCircleLayoutX(), ((TNT) obj).getCircleLayoutY(), ((TNT) obj).getRadius());
                System.out.println(((TNT) obj).getCircleLayoutX() + "   " + ((TNT) obj).getCircleLayoutY() + "   " + ((TNT) obj).getRadius());
                tnt.setCollided(((TNT) obj).getCollided());
                tnt.setisExploded(((TNT) obj).getisExploded());
                finalList.add(tnt);

            }


        }
        return finalList;
    }
}