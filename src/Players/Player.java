package Players;

public class Player implements Runnable {
    private String username;
    private boolean isPacMan;
    private int score;

    private Position position;

    private Players enemyPlayers;

    public Player(String username) {
        this.username = username;
        this.enemyPlayers = new Players();
        this.position = new Position(0, 0);
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
