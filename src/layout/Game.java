package layout;

import connection.Client;
import constants.GameConstants;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    public Game() {
        initUI();
    }

    public void initUI() {
        this.add(new Client("", "prova"));
        this.setTitle("PacMan - Multiplayer");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setLocationRelativeTo(null);
    }

    public void run() {
        this.setVisible(true);
    }
}
