package connection;

import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static constants.GameConstants.*;

public class Client extends JPanel implements ActionListener {
    private String enemyNickname;

    private Dimension dimension;
    private boolean inGame = false;
    private boolean isDying = false;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;

    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;

    private final short[] levelData = {
            3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 6,
            1, 0, 8, 0, 8, 0, 8, 8, 8, 0, 8, 8, 8, 0, 4,
            1, 4, 0, 5, 0, 5, 0, 0, 0, 5, 0, 0, 0, 1, 4,
            1, 0, 10, 0, 10, 0, 2, 2, 10, 0, 6, 0, 3, 0, 4,
            1, 4, 0, 5, 0, 1, 0, 4, 0, 1, 12, 0, 9, 0, 4,
            1, 0, 10, 0, 10, 0, 0, 12, 0, 5, 0, 0, 0, 1, 4,
            1, 4, 0, 5, 0, 1, 4, 0, 0, 1, 10, 2, 10, 0, 4,
            1, 4, 0, 5, 0, 1, 0, 10, 10, 4, 0, 5, 0, 1, 4,
            1, 0, 10, 8, 10, 0, 4, 0, 0, 5, 0, 5, 0, 1, 4,
            1, 4, 0, 0, 0, 1, 4, 0, 3, 0, 10, 0, 10, 0, 4,
            1, 0, 6, 0, 3, 0, 4, 0, 1, 4, 0, 5, 0, 1, 4,
            1, 0, 12, 0, 9, 0, 8, 10, 8, 0, 10, 0, 10, 0, 4,
            1, 4, 0, 0, 0, 5, 0, 0, 0, 5, 0, 5, 0, 1, 4,
            1, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 0, 2, 0, 4,
            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 12
    };

    private short[] screenData;
    private Timer timer;

    public Client(String pacmanColor, String enemyNickname) {
        loadImages(pacmanColor);
        initVariables(enemyNickname);
        initBoard();
    }

    private void initVariables(String enemyNickname) {
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

    private void doAnim() {
        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = PAC_ANIM_DELAY;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            int PACMAN_ANIM_COUNT = 4;
            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }

    private void playGame(Graphics2D g2d) {
        if (isDying) {
            death();
        } else {
            movePacman();
            drawPacman(g2d);
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

    private void movePacman() {
        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            copyValues();
        }

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            int pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (pacman_y / BLOCK_SIZE);
            short ch = screenData[pos];

            if (req_dx != 0 || req_dy != 0) {
                if (!isaCorrectPosition(ch, req_dx, req_dy)) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                    view_dx = pacmand_x;
                    view_dy = pacmand_y;
                }
            }

            if (isaCorrectPosition(screenData[pos], pacmand_x, pacmand_y)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }

        int PACMAN_SPEED = 6;
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
    }

    private void copyValues() {
        pacmand_x = req_dx;
        pacmand_y = req_dy;
        view_dx = pacmand_x;
        view_dy = pacmand_y;
    }

    private boolean isaCorrectPosition(short ch, int req_dx, int req_dy) {
        return (req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0);
    }

    private void drawPacman(Graphics2D g2d) {
        if (view_dx == -1) {
            drawPacnanLeft(g2d);
        } else if (view_dx == 1) {
            drawPacmanRight(g2d);
        } else if (view_dy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void drawPacmanUp(Graphics2D g2d) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4up, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4down, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacnanLeft(Graphics2D g2d) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4left, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4right, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
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
        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 13 * BLOCK_SIZE;
        pacmand_x = 0;
        pacmand_y = 0;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
        isDying = false;
    }

    private void loadImages(String playerColor) {
        pacman1 = new ImageIcon("images/pacman" + playerColor + ".png").getImage();
        pacman2up = new ImageIcon("images/up1" + playerColor + ".png").getImage();
        pacman3up = new ImageIcon("images/up2" + playerColor + ".png").getImage();
        pacman4up = new ImageIcon("images/up3" + playerColor + ".png").getImage();
        pacman2down = new ImageIcon("images/down1" + playerColor + ".png").getImage();
        pacman3down = new ImageIcon("images/down2" + playerColor + ".png").getImage();
        pacman4down = new ImageIcon("images/down3" + playerColor + ".png").getImage();
        pacman2left = new ImageIcon("images/left1" + playerColor + ".png").getImage();
        pacman3left = new ImageIcon("images/left2" + playerColor + ".png").getImage();
        pacman4left = new ImageIcon("images/left3" + playerColor + ".png").getImage();
        pacman2right = new ImageIcon("images/right1" + playerColor + ".png").getImage();
        pacman3right = new ImageIcon("images/right2" + playerColor + ".png").getImage();
        pacman4right = new ImageIcon("images/right3" + playerColor + ".png").getImage();
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

        drawMaze(g2d);
        drawEnemyNickname(g2d);
        doAnim();

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
        g2d.drawString(onevone, 5, SCREEN_SIZE + 16);
    }

    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(@NotNull KeyEvent e) {
            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
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
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
