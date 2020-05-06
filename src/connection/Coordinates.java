/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.Serializable;

/**
 *
 * @author farin
 */
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
