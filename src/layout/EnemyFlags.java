package layout;

import java.util.ArrayList;

public class EnemyFlags extends ArrayList<Flag> {
    private ArrayList<Flag> enemyFlags;

    public EnemyFlags(boolean isPlayerOne) {
        enemyFlags = new ArrayList<>(4);
        enemyFlags.addAll(setFlags(isPlayerOne));
    }

    private ArrayList<Flag> setFlags(boolean isPlayerOne) {
        ArrayList<Flag> temp = new ArrayList<>(4);

        if (isPlayerOne) {
            temp.add(new Flag(208, false));
            temp.add(new Flag(196, false));
            temp.add(new Flag(160, false));
            temp.add(new Flag(140, false));
        } else {
            temp.add(new Flag(16, false));
            temp.add(new Flag(28, false));
            temp.add(new Flag(53, false));
            temp.add(new Flag(80, false));
        }

        return temp;
    }

    public Flag get(int i) {
        return enemyFlags.get(i);
    }

    public int getPosition(int i) {
        return enemyFlags.get(i).getPosition();
    }
}
