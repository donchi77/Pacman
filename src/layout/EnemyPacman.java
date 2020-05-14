package layout;

import connection.ConnectionManager;
import connection.Coordinates;
import javax.swing.*;
import java.awt.*;

public class EnemyPacman implements Runnable {
    private final ConnectionManager connectionManager;
    private final JPanel map;
    private final Graphics2D g2d;

    public EnemyPacman(JPanel map, Graphics2D g2d) {
        this.connectionManager = new ConnectionManager();
        this.map = map;
        this.g2d = g2d;
    }

    @Override
    synchronized public void run() {
        while(true) {
            Coordinates coordinates = connectionManager.readCoordinates();
            g2d.drawImage(new ImageIcon("images/" + coordinates.getImageFile() + ".png").getImage(),
                    coordinates.getX(), coordinates.getY(), map);
        }
    }
}
