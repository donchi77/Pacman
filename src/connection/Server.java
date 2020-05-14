package connection;

import connection.User;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author farin
 */
public class Server {
    
    //Inizializzazione dell'oggetto server
    static Server server;
    
    //socket server
    ServerSocket serverSocket;
    
    //stream output
    OutputStream outputStream; 
    ObjectOutputStream objectOutputStream;
    //stream input
    InputStream inputStream ;
    ObjectInputStream objectInputStream;
    
    //Arraylist dei due giocatori
    ArrayList<User> users = new ArrayList<>();
    
    //Inizializzazione dell'oggetto coordinates
    Coordinates[] coordinates;
    
    boolean check = true;
    
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

            while(true){
                if(check == true){
                    for(int i = 0; i< 2; i++){
                        //accept connection
                        Socket socket = serverSocket.accept();
                        System.out.println("Connection from " + socket + "!");

                        User user = new User(socket, i, this);
                        users.add(user);
                    }
                    System.out.println("All the players are connected");
                    System.out.println("The game start");

                    //setta la variabile gamStart a true, per indicare al player 1 che il giocatore 2 si è connesso e che la partita comincia
                    users.get(0).setGameStart(true);
                }
                check = false;
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error");
            
            System.exit(1);
        }
    } 
    
    public void send(Coordinates coordinates, int p) {
        //salva il numero del giocatore
        int player = p+1;
        System.out.println("Ricezione dal giocatore " + player + " : x -" + coordinates.getX() + " e y - " + coordinates.getY());
        System.out.println("Nome Immagine : " + coordinates.getImage());
        
        //inizializzazione dell'oggetto user
        User u;
        
        try {
            //seetting in base a quale socket ha scritto, dell'oggeto user contenente la socket su cui andare a scrivere
            if(p == 0)
                u = users.get(1);
            else
                u = users.get(0);
            
            //invio delle coordinate all'atra socket
            u.send(coordinates);
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
