package layout;

import connection.ConnectionManager;
import connection.Packet;
import javax.swing.*;
import java.awt.*;
import static constants.GameConstants.*;

public class Pacman {
    private final boolean isPlayerOne;
    private final Flags flags;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;

    private final char playerColor;
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;
    private int tempPos;
    
    private final ConnectionManager connectionManager;

    public Pacman(boolean isPlayerOne, ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;

        this.isPlayerOne = isPlayerOne;
        flags = new Flags(isPlayerOne);

        playerColor = isPlayerOne ? 'Y' : 'R';
        loadImages();
    }

    public void setAttributes() {
        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = isPlayerOne ? BLOCK_SIZE : 13 * BLOCK_SIZE;
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

    public void movePacman(short[] screenData) {
        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
            copyValues();
        }

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            int pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (pacman_y / BLOCK_SIZE);
            tempPos = pos;
            short ch = screenData[pos];

            if ((ch & 16) != 0) {
                for (Flag flag : flags.getEnemyFlags()) {
                    if (pos == flag.getPosition()) {
                        screenData[pos] = (short) (ch & 15);
                        flags.getEnemyFlags().remove(flag);
                        break;
                    }
                }
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!isaCorrectPosition(ch, req_dx, req_dy)) {
                    copyValues();
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
        int enemyFlags = getFlags().getEnemyFlags().size();
        boolean isEnded = false;
        
        if(enemyFlags != 0)
            isEnded = false;
        else 
            isEnded = true;
        
        if (view_dx == -1) {
            connectionManager.writeCoordinates(
                    new Packet(pacman_x + 1, pacman_y + 1, tempPos, getImageFileName(MOVING_LEFT), isEnded));
            drawPacmanLeft(g2d, map);
        } else if (view_dx == 1) {
            connectionManager.writeCoordinates(
                    new Packet(pacman_x + 1, pacman_y + 1, tempPos, getImageFileName(MOVING_RIGHT), isEnded));
            drawPacmanRight(g2d, map);
        } else if (view_dy == -1) {
            connectionManager.writeCoordinates(
                    new Packet(pacman_x + 1, pacman_y + 1, tempPos, getImageFileName(MOVING_UP), isEnded));
            drawPacmanUp(g2d, map);
        } else {
            connectionManager.writeCoordinates(
                    new Packet(pacman_x + 1, pacman_y + 1, tempPos, getImageFileName(MOVING_DOWN), isEnded));
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

    private String getImageFileName(String action) {
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

    public Flags getFlags() { return this.flags; }
    public void setReq_dx(int req_dx) { this.req_dx = req_dx; }
    public void setReq_dy(int req_dy) { this.req_dy = req_dy; }
}
