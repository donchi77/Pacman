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
        
        //send coordinate
        connectionM.writeCoordinates(9, 9);
        System.out.println("9 - 9");
        
        //receive coordinates
        connectionM.readCoordinates();
        
    }
}
