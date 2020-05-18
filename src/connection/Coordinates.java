package connection;

import java.io.Serializable;

public class Coordinates implements Serializable{
    static final long serialVersionUID = 1L;
    
    //coordinate
    private final int x;
    private final int y;

    // Posizione
    private final int pos;

    //Immagine
    private final String imageFile;
    
    public Coordinates(int x, int y, int pos, String imageFile) {
        this.x = x;
        this.y = y;
        this.pos = pos;
        this.imageFile = imageFile;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getPos() { return pos; }
    public String getImageFile() { return imageFile; }
}
