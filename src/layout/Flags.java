package layout;

import java.util.ArrayList;

public class Flags extends ArrayList<Flag> {
    private final ArrayList<Flag> enemyFlags;

    public Flags(boolean isPlayerOne) {
        enemyFlags = new ArrayList<>(4);
        setFlags(isPlayerOne);
    }

    private void setFlags(boolean isPlayerOne) {
        if (isPlayerOne) {
            enemyFlags.add(new Flag(208));
            enemyFlags.add(new Flag(196));
            enemyFlags.add(new Flag(146));
            enemyFlags.add(new Flag(126));
        } else {
            enemyFlags.add(new Flag(16));
            enemyFlags.add(new Flag(28));
            enemyFlags.add(new Flag(53));
            enemyFlags.add(new Flag(80));
        }
    }

    public ArrayList<Flag> getEnemyFlags() { return this.enemyFlags; }
}
