package connection;

import java.io.Serializable;

public class Packet implements Serializable{
    static final long serialVersionUID = 1L;
    
    //coordinate
    private final int x;
    private final int y;

    // Posizione
    private final int pos;

    //Immagine
    private final String imageFile;

    //Fine Gioco
    private boolean isEnded = false;
    
    public Packet(int x, int y, int pos, String imageFile, boolean isEnded) {
        this.x = x;
        this.y = y;
        this.pos = pos;
        this.imageFile = imageFile;
        this.isEnded = isEnded;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getPos() { return pos; }
    public String getImageFile() { return imageFile; }
    public boolean getisEnded() { return isEnded; }
}
