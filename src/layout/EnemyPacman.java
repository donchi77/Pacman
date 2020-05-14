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
    public void run() {
        while(true) {
            Coordinates coordinates = connectionManager.readCoordinates();

            synchronized (map) {
                synchronized (g2d) {
                    g2d.drawImage(new ImageIcon(coordinates.getImageFile()).getImage(),
                            coordinates.getX(), coordinates.getY(), map);
                }
            }
        }
    }
}
