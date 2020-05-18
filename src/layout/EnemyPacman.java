package layout;

import connection.ConnectionManager;
import connection.Coordinates;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EnemyPacman implements Runnable {
    private final ConnectionManager connectionManager;
    
    public EnemyPacman(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        new Thread(this).start();
    }

    @Override
    synchronized public void run() {
        /*while(true) {
            Coordinates coordinates = connectionManager.readCoordinates();
            System.out.println("x : " + coordinates.getX() + " - y : " + coordinates.getY() + " - file : " + coordinates.getImageFile());
            g2d.drawImage(new ImageIcon("images/" + coordinates.getImageFile() + ".png").getImage(),
                    coordinates.getX(), coordinates.getY(), map);
        }*/
    }

    synchronized public void drawEnemy(Graphics2D g2d, JPanel map, Flags flags, short[] screenData){
        Coordinates coordinates = connectionManager.readCoordinates();
        System.out.println("x : " + coordinates.getX() + " - y : " + coordinates.getY() +
                " - file : " + coordinates.getImageFile() + " - pos : " + coordinates.getPos());
        g2d.drawImage(new ImageIcon("images/" + coordinates.getImageFile() + ".png").getImage(),
                coordinates.getX(), coordinates.getY(), map);

        int pos = coordinates.getPos();
        short ch = screenData[pos];
        if ((ch & 16) != 0) {
            boolean isMine = true;
            for (Flag flag : flags.getEnemyFlags()) {
                if (flag.getPosition() == pos) {
                    isMine = false;
                    break;
                }
            }

            if (isMine)
                screenData[pos] = (short) (ch & 15);
        }
    }
}
