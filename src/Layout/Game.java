package Layout;

import Connection.Client;
import Constants.GameCostants;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private MapDesign map;

    public Game() {
        initUI();
    }

    public void initUI() {
        this.add(new Client());
        this.setTitle("PacMan - Multiplayer");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(GameCostants.SCREEN_WIDTH, GameCostants.SCREEN_HEIGHT));
        this.setLocationRelativeTo(null);
    }

    public void run() {
        this.setVisible(true);
    }
}
