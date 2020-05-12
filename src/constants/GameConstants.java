package constants;

import java.awt.*;

public class GameConstants {
    public static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final Color MAZE_COLOR = Color.BLUE;

    public static final int SCREEN_WIDTH = 380;
    public static final int SCREEN_HEIGHT = 430;
    public static final int BLOCK_SIZE = 24;
    public static final int N_BLOCKS = 15;
    public static final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    public static final int PAC_ANIM_DELAY = 2;

    public static final short[] MAP_DESIGN = {
            3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 6,
            1, 19, 10, 2, 10, 2, 10, 10, 10, 2, 10, 10, 10, 6, 4,
            1, 5, 0, 5, 0, 5, 0, 0, 0, 5, 0, 0, 0, 5, 4,
            1, 1, 10, 0, 10, 0, 2, 2, 10, 0, 6, 0, 3, 4, 4,
            1, 5, 0, 5, 0, 1, 0, 4, 0, 1, 12, 0, 9, 4, 4,
            1, 1, 10, 0, 10, 0, 0, 12, 0, 5, 0, 0, 0, 5, 4,
            1, 5, 0, 5, 0, 1, 4, 0, 0, 1, 10, 2, 10, 4, 4,
            1, 5, 0, 5, 0, 1, 0, 10, 10, 4, 0, 5, 0, 5, 4,
            1, 1, 10, 8, 10, 0, 4, 0, 0, 5, 0, 5, 0, 5, 4,
            1, 5, 0, 0, 0, 1, 4, 0, 3, 0, 10, 0, 10, 4, 4,
            1, 1, 6, 0, 3, 0, 4, 0, 1, 4, 0, 5, 0, 5, 4,
            1, 1, 12, 0, 9, 0, 8, 10, 8, 0, 10, 0, 10, 4, 4,
            1, 5, 0, 0, 0, 5, 0, 0, 0, 5, 0, 5, 0, 5, 4,
            1, 9, 10, 10, 10, 8, 10, 10, 10, 8, 10, 8, 10, 28, 4,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 12
    };
}
