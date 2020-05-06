package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author farin
 */
public class ConnectionManager {
    
    //server
    String serverIP = "127.0.0.1";                  
    int serverPort = 8080;
    
    Socket socket;      
    // stream output
    OutputStream outputStream; 
    ObjectOutputStream objectOutputStream;
    // stream input
    InputStream inputStream;
    ObjectInputStream objectInputStream;

    public ConnectionManager() {
        try { 
            //creation socket  
            System.out.println("Socket creation...");
            this.socket = new Socket(serverIP,serverPort);
            
            //creation input e output stream
            System.out.println("Creation input and output stream...");
            this.outputStream = socket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(this.outputStream);
            this.inputStream = this.socket.getInputStream();
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            
        } catch (UnknownHostException e){
            System.err.println("Unreacheable host"); 
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Error");
            
            System.exit(1);
        }
    }
    
    public void writeCoordinates(int x, int y) throws IOException{
        //send coordinates
        Coordinates coordinates = new Coordinates(x, y);
        objectOutputStream.writeObject(coordinates);
    }
    
    public void readCoordinates() throws IOException, ClassNotFoundException {
        //read coordinates
        Coordinates coordinates = (Coordinates) objectInputStream.readObject();
        System.out.println("" + coordinates.getX() + " - " + coordinates.getY());
    }
}

