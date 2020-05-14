package connection;

import java.io.IOException;

/**
 *
 * @author farin
 */
public class Client1 {
    
    //Gestire la connessione
    
    static ConnectionManager connectionM;
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        //creation socket
        connectionM = new ConnectionManager();
        
        //read player 1 or 2
        connectionM.readPlayer();
        
        //send and receive coordinates
        while(true){
            connectionM.writeCoordinates(5, 9, "prova");

            Coordinates coordinates = connectionM.readCoordinates();
            System.out.println("" + coordinates.getX() + " - " + coordinates.getY() + " - Immagine : " + coordinates.getImage());
        }
        
    }
}
