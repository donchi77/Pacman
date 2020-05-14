package main;

import layout.Game;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.run();
        });
    }
}
