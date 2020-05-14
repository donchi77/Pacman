package layout;

import com.sun.istack.internal.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static constants.GameConstants.*;

public class Map extends JPanel implements ActionListener {
    Pacman pacman;
    private boolean isEnemyInitialized;
    private String enemyNickname;

    private Dimension dimension;
    private boolean inGame = false;
    private boolean isDying = false;

    private Image flagY;
    private Image flagR;

    private final short[] levelData = MAP_DESIGN;
    private short[] screenData;
    private Timer timer;

    public Map(String nickname, String enemyNickname, boolean isPlayerOne) {
        loadImages();
        initVariables(nickname, enemyNickname, isPlayerOne);
        initBoard();
    }

    private void loadImages() {
        flagY = new ImageIcon("images/flagY.png").getImage();
        flagR = new ImageIcon("images/flagR.png").getImage();
    }

    private void initVariables(String nickname, String enemyNickname, boolean isPlayerOne) {
        pacman = new Pacman(nickname, isPlayerOne);
        isEnemyInitialized = false;
        this.enemyNickname = enemyNickname;
        screenData = new short[N_BLOCKS * N_BLOCKS];
        dimension = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);

        timer = new Timer(40, this);
        timer.start();
    }

    private void initBoard() {
        setDoubleBuffered(true);
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(BACKGROUND_COLOR);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        initGame();
    }

    private void playGame(Graphics2D g2d) {
        if (isDying) {
            death();
        } else {
            pacman.movePacman(g2d, screenData);
            pacman.drawPacman(g2d, this);
        }
    }

    private void showIntroScreen(@NotNull Graphics2D g2d) {
        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String start = "PRESS S TO START";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(start, (SCREEN_SIZE - metr.stringWidth(start)) / 2, SCREEN_SIZE / 2);
    }

    private void death() {

    }

    private void drawMaze(Graphics2D g2d) {
        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(MAZE_COLOR);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    boolean temp = pacman.getIsPlayerOne();
                    g2d.drawImage(temp ? flagY : flagR,
                            temp ? x - 3 : x + 3,
                            temp ? y - 5 : y,
                            this);
                }

                i++;
            }
        }
    }

    private void initGame() {
        initLevel();
    }

    private void initLevel() {
        System.arraycopy(levelData, 0, screenData, 0, N_BLOCKS * N_BLOCKS);
        continueLevel();
    }

    private void continueLevel() {
        pacman.setAttributes();
        isDying = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, dimension.width, dimension.height);

        if (!isEnemyInitialized) {
            new EnemyPacman(this, g2d).run();
            isEnemyInitialized = true;
        }

        drawMaze(g2d);
        drawEnemyNickname(g2d);
        pacman.doAnim();

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    private void drawEnemyNickname(@NotNull Graphics2D g2d) {
        g2d.setFont(new Font("Helvetica", Font.BOLD, 14));
        g2d.setColor(Color.WHITE);
        String onevone = "You are aganist: " + enemyNickname;
        g2d.drawString(onevone, 5, 16);
    }

    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(@NotNull KeyEvent e) {
            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    pacman.setReq_dx(-1);
                    pacman.setReq_dy(0);
                } else if (key == KeyEvent.VK_RIGHT) {
                    pacman.setReq_dx(1);
                    pacman.setReq_dy(0);
                } else if (key == KeyEvent.VK_UP) {
                    pacman.setReq_dx(0);
                    pacman.setReq_dy(-1);
                } else if (key == KeyEvent.VK_DOWN) {
                    pacman.setReq_dx(0);
                    pacman.setReq_dy(1);
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(@NotNull KeyEvent e) {
            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                pacman.setReq_dx(0);
                pacman.setReq_dy(0);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
