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
            this.inputStream = this.socket.getInputStream();
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            this.outputStream = socket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(this.outputStream);
            
        } catch (UnknownHostException e){
            System.err.println("Unreacheable host"); 
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Error");
            
            System.exit(1);
        }
    }

    //Invio pacchetto
    public void writeCoordinates(Packet packet) {
        try {
            objectOutputStream.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Lettura pacchetto
    public Packet readCoordinates() {
        //read coordinates
        try {
            return (Packet) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Lettura player 1 o 2
    public boolean readPlayer() throws IOException, ClassNotFoundException {
        //Legge se è il giocatore 1 o 2
        int p = (int) objectInputStream.readObject();
        System.out.println("Sono il giocatore : " + p);

        return p == 0;
    }
    
    public void playersConnected(boolean isPlayerOne) throws IOException, ClassNotFoundException{
        //ulteriore controllo per il gicatore 1
        if(isPlayerOne){
            System.out.println("In attesa di un'altro giocatore...");
            
            //variabile d'appoggo, per bloccare il player 1 fino a quando non si è connesso il player 2
            int gamestart = (int) objectInputStream.readObject();
            
            System.out.println("Il secondo giocatore si è connesso, la partita inizia!");
        }
        else{
            System.out.println("Il giocatore 1 è gia collegato");

            //Variabile per sincronizzare il player 2
            int gamestart = (int) objectInputStream.readObject();
        }
    }
    
    public void closeConnection() throws IOException{ this.socket.close(); }
}

