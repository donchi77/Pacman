package connection;

import java.io.Serializable;

public class Coordinates implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    //coordinate
    int x;
    int y;
    
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
