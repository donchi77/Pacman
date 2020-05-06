package player;

import java.util.ArrayList;

public class Players {
    ArrayList<Player> players;

    public Players() {
        this.players = new ArrayList<Player>();
    }

    public void add(Player player) {
        this.players.add(player);
    }

    public Player get(int i) {
        return this.players.get(i);
    }
}
