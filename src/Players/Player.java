package Players;

import java.net.Inet4Address;

public class Player {
    private String username;
    private Inet4Address IPv4Host;
    private boolean isPacMan;
    private int posX;
    private int posY;

    public Player(String username, Inet4Address IPv4Host) {
        this.username = username;
        this.IPv4Host = IPv4Host;
    }
}
