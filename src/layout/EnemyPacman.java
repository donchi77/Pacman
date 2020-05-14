package layout;

import connection.ConnectionManager;
import connection.Coordinates;
import javax.swing.*;
import java.awt.*;

public class EnemyPacman implements Runnable {
    private final ConnectionManager connectionManager;
    private final JPanel map;
    private final Graphics2D g2d;
    
    public EnemyPacman(JPanel map, Graphics2D g2d, ConnectionManager connectionM) {
        this.connectionManager = connectionM;
        this.map = map;
        this.g2d = g2d;
        
        new Thread(this).start();
    }

    @Override
    synchronized public void run() {
        while(true) {
            Coordinates coordinates = connectionManager.readCoordinates();
            System.out.println("x : " + coordinates.getX() + " - y : " + coordinates.getY() + " - file : " + coordinates.getImageFile());
            g2d.drawImage(new ImageIcon("images/" + coordinates.getImageFile() + ".png").getImage(),
                    coordinates.getX(), coordinates.getY(), map);
        }
    }
}
