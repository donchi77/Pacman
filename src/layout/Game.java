package layout;

import connection.ConnectionManager;
import constants.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Game extends JFrame {
    private ConnectionManager connectionManager;
    
    public Game() {
        initUI();
    }

    public void initUI() {
        startConnection();
        try {
            this.add(new Map(connectionManager.readPlayer(), connectionManager));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.setTitle("PacMan - Multiplayer");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setLocationRelativeTo(null);
    }

    public void run() {
        this.setVisible(true);
    }

    private void startConnection() {
        connectionManager = new ConnectionManager();
    }
}
