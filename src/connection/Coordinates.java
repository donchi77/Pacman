package connection;

import java.io.Serializable;

public class Coordinates implements Serializable{
    
    static final long serialVersionUID = 1L;
    
    //coordinate
    int x;
    int y;

    //Immagine
    String imageFile;
    
    public Coordinates(int x, int y, String imageFile) {
        this.x = x;
        this.y = y;
        this.imageFile = imageFile;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getImageFile() {
        return imageFile;
    }
    
}
