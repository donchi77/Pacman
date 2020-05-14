package layout;

import connection.Coordinates;
import connection.Sender;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import static constants.GameConstants.*;

public class Pacman {
    private final String username;
    private final boolean isPlayerOne;
    private boolean isCannibal;

    private Flag flag;
    private boolean isEnemyFlagTaken;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;

    private final char playerColor;
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;

    public Pacman(String username, boolean isPlayerOne) {
        this.username = username;

        this.isPlayerOne = isPlayerOne;
        if (isPlayerOne) flag = new Flag(208, false);
        if (isPlayerOne) playerColor = 'Y'; else playerColor = 'R';

        isCannibal = false;
        isEnemyFlagTaken = false;

        loadImages();
    }

    public void setAttributes() {
        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 13 * BLOCK_SIZE;
        pacmand_x = 0;
        pacmand_y = 0;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
    }

    private void loadImages() {
        pacman1 = new ImageIcon("images/pacman" + playerColor + ".png").getImage();
        pacman2up = new ImageIcon("images/up1" + playerColor + ".png").getImage();
        pacman3up = new ImageIcon("images/up2" + playerColor + ".png").getImage();
        pacman2down = new ImageIcon("images/down1" + playerColor + ".png").getImage();
        pacman3down = new ImageIcon("images/down2" + playerColor + ".png").getImage();
        pacman2left = new ImageIcon("images/left1" + playerColor + ".png").getImage();
        pacman3left = new ImageIcon("images/left2" + playerColor + ".png").getImage();
        pacman2right = new ImageIcon("images/right1" + playerColor + ".png").getImage();
        pacman3right = new ImageIcon("images/right2" + playerColor + ".png").getImage();
    }

    public void movePacman(Graphics2D g2d, short[] screenData) {
        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            copyValues();
        }

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            int pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (pacman_y / BLOCK_SIZE);
            short ch = screenData[pos];

            if ((ch & 16) != 0) {
                if (pos != flag.getPosition()) {
                    this.isEnemyFlagTaken = true;
                    screenData[pos] = (short) (ch & 15);
                    // TODO: SEND AHAH TI HO PRESO LA BANDIERA
                } else {
                    if (this.isEnemyFlagTaken) {
                        drawScoreString(g2d);
                        this.isEnemyFlagTaken = false;
                        screenData[16] += 16;
                    }
                    // TODO: SEND AHAH HO SEGNATO UN PUNTO
                }
            }

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

    public void doAnim() {
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

    public void drawPacman(Graphics2D g2d, JPanel map) {
        if (view_dx == -1) {
            //new Sender(new Coordinates(pacman_x + 1, pacman_y + 1, getImageFileName(MOVING_LEFT))).run();
            drawPacmanLeft(g2d, map);
        } else if (view_dx == 1) {
            //new Sender(new Coordinates(pacman_x + 1, pacman_y + 1, getImageFileName(MOVING_RIGHT))).run();
            drawPacmanRight(g2d, map);
        } else if (view_dy == -1) {
            //new Sender(new Coordinates(pacman_x + 1, pacman_y + 1, getImageFileName(MOVING_UP))).run();
            drawPacmanUp(g2d, map);
        } else {
            //new Sender(new Coordinates(pacman_x + 1, pacman_y + 1, getImageFileName(MOVING_DOWN))).run();
            drawPacmanDown(g2d, map);
        }
    }

    private void drawPacmanUp(Graphics2D g2d, JPanel map) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2up, pacman_x + 1, pacman_y + 1, map);
                break;
            case 2:
                g2d.drawImage(pacman3up, pacman_x + 1, pacman_y + 1, map);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, map);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d, JPanel map) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2down, pacman_x + 1, pacman_y + 1, map);
                break;
            case 2:
                g2d.drawImage(pacman3down, pacman_x + 1, pacman_y + 1, map);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, map);
                break;
        }
    }

    private void drawPacmanLeft(Graphics2D g2d, JPanel map) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2left, pacman_x + 1, pacman_y + 1, map);
                break;
            case 2:
                g2d.drawImage(pacman3left, pacman_x + 1, pacman_y + 1, map);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, map);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d, JPanel map) {
        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2right, pacman_x + 1, pacman_y + 1, map);
                break;
            case 2:
                g2d.drawImage(pacman3right, pacman_x + 1, pacman_y + 1, map);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, map);
                break;
        }
    }

    private String getImageFileName(@NotNull String action) {
        switch (action) {
            case MOVING_UP:
                switch (pacmanAnimPos) {
                    case 1:
                        return "up1" + playerColor;
                    case 2:
                        return "up2" + playerColor;
                    default:
                        return "pacman" + playerColor;
                }
            case MOVING_DOWN:
                switch (pacmanAnimPos) {
                    case 1:
                        return "down1" + playerColor;
                    case 2:
                        return "down2" + playerColor;
                    default:
                        return "pacman" + playerColor;
                }
            case MOVING_LEFT:
                switch (pacmanAnimPos) {
                    case 1:
                        return "left1" + playerColor;
                    case 2:
                        return "left2" + playerColor;
                    default:
                        return "pacman" + playerColor;
                }
            case MOVING_RIGHT:
                switch (pacmanAnimPos) {
                    case 1:
                        return "right1" + playerColor;
                    case 2:
                        return "right2" + playerColor;
                    default:
                        return "pacman" + playerColor;
                }
        }

        return action;
    }

    private void drawScoreString(@NotNull Graphics2D g2d) {
        g2d.setFont(new Font("Helvetica", Font.BOLD, 14));
        g2d.setColor(Color.WHITE);
        String onevone = "Enemy flag has been rescued!";
        g2d.drawString(onevone, 5, SCREEN_SIZE + 16);
    }

    public String getUsername() { return this.username; }
    public boolean getIsPlayerOne() { return this.isPlayerOne; }
    public void setReq_dx(int req_dx) { this.req_dx = req_dx; }
    public void setReq_dy(int req_dy) { this.req_dy = req_dy; }
}
