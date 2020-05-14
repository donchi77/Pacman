package connection;

import java.io.Serializable;

public class Coordinates implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    //coordinate
    int x;
    int y;

    //Immagine
    String image;
    
    public Coordinates(int x, int y, String image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getImage() {
        return image;
    }
    
}
