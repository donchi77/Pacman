package connection;

import connection.Coordinates;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author farin
 */
public class Server {
    
    static Server server;
    
    //socket server
    ServerSocket serverSocket;
    
    //stream output
    OutputStream outputStream; 
    ObjectOutputStream objectOutputStream;
    //stream input
    InputStream inputStream ;
    ObjectInputStream objectInputStream;
    
    public static void main (String[] args) { 
        
        //Server start
        server = new Server(); 
        server.start(); 
    } 
    
    public void start(){ 
        try {
            //creazione server socket
            serverSocket = new ServerSocket(8080); 
            System.out.println("ServerSocket awaiting connections...");
            
            //accept connection
            Socket socket = serverSocket.accept();
            System.out.println("Connection from " + socket + "!");
            
            //input
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            
            //output
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            
            //receive coordinates
            readCoordinates();
            
            //write coordinates 
            writeCoordinates(1, 1);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error");
            
            System.exit(1);
        }
    } 
    
    void readCoordinates() throws IOException, ClassNotFoundException {
        //creazione e ricezione oggetto coordinate 
        Coordinates coordinates = (Coordinates) objectInputStream.readObject();
        System.out.println("" + coordinates.getX() + " - " + coordinates.getY());
    }
    
    public void writeCoordinates(int x, int y) throws IOException{
        //creazione e invio oggetto coordinate 
        Coordinates coordinates = new Coordinates(x, y);
        objectOutputStream.writeObject(coordinates);
    }
}
