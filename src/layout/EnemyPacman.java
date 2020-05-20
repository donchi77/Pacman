package layout;

import connection.ConnectionManager;
import connection.Packet;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class EnemyPacman implements Runnable {
    private final ConnectionManager connectionManager;
    private final Map maP; 
    
    public EnemyPacman(ConnectionManager connectionManager, Map map) {
        this.connectionManager = connectionManager;
        this.maP = map;
        
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

    synchronized public void drawEnemy(Graphics2D g2d, JPanel map, Flags flags, short[] screenData) throws IOException{
        Packet packet = connectionManager.readCoordinates();
        System.out.println("x : " + packet.getX() + " - y : " + packet.getY() +
                " - file : " + packet.getImageFile() + " - pos : " + packet.getPos());
        g2d.drawImage(new ImageIcon("images/" + packet.getImageFile() + ".png").getImage(),
                packet.getX(), packet.getY(), map);

        int pos = packet.getPos();
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
        
        boolean isEnded = packet.getisEnded();
        
        
        if(isEnded)
            maP.isEnded(false);
    }
}
