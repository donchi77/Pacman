package layout;

public class Flag {
    private int position;
    private boolean isTaken;

    public Flag(int position, boolean isTaken) {
        this.position = position;
        this.isTaken = isTaken;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
