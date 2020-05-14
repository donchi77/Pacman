package layout;

import connection.ConnectionManager;
import constants.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Game extends JFrame {
    
    ConnectionManager connectionM;
    
    public Game() throws IOException, ClassNotFoundException {
        initUI();
    }

    public void initUI() throws IOException, ClassNotFoundException {
        
        startConnection();
        
        this.add(new Map("prova", "prova", connectionM.readPlayer(), connectionM));
        this.setTitle("PacMan - Multiplayer");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setLocationRelativeTo(null);
    }

    public void run() {
        this.setVisible(true);
    }

    private void startConnection() {
        connectionM = new ConnectionManager();
    }
}
