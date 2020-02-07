package Players;

public class Player implements Runnable {
    private String username;
    private boolean isPacMan;
    private String pathImages;
    private int posX;
    private int posY;

    private Players enemyPlayers;

    public Player(String username) {
        this.username = username;
        this.enemyPlayers = new Players();
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public boolean isPacMan() {
        return isPacMan;
    }

    public void setPacMan(boolean pacMan) {
        isPacMan = pacMan;
    }

    @Override
    public void run() {

    }
}
