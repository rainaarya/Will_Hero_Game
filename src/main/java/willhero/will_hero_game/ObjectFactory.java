package willhero.will_hero_game;

public class ObjectFactory {
    public GameObjects createObject(int obsno, float x, float y) {
        GameObjects obs1 = null;
        if (obsno == 1) {
            obs1 = new Island(x, y);
            obs1.setObjectType("Island");

        }
        if (obsno == 2) {
            obs1 = new Hero(x, y);
            obs1.setObjectType("Hero");

        }

        if (obsno == 3) {
            obs1 = new Orc(x, y);
            obs1.setObjectType("Orc");
        }

        return obs1;


    }
}
