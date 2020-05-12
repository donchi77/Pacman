package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author farin
 */
public class User implements Runnable{
    
    Socket socket;
    Server server;
    
    //stream output
    OutputStream outputStream; 
    ObjectOutputStream objectOutputStream;
    //stream input
    InputStream inputStream ;
    ObjectInputStream objectInputStream;
    
    //variabile che indica il giocatore
    int player;
    //variabile utlizzata per il continuo invio e ricezione delle coordinate
    boolean check = true;
    //variabili che indicano al giocatore 1 l'inizio della partita
    boolean gameStart = false;
    boolean gameStart2 = false;
    
    public User(Socket socket, int player, Server server){
        //setta l'oggetto server
        this.server = server;
        
        //setta se è il giocatore 1 o 2
        this.player = player;
        
        try {
            //setta la scoket
            this.socket = socket;
            
            //output
            this.outputStream = this.socket.getOutputStream();
            this.objectOutputStream = new ObjectOutputStream(this.outputStream);
            //input
            this.inputStream = this.socket.getInputStream();
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            
            //indica al client se è il giocatore 1 o 2
            objectOutputStream.writeObject(player);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new Thread(this).start();
    }

    @Override
    public void run() {
         try {
             
            //controllo aggiuntivo per il giocatore 1
            if (player == 0){
                int k = 10;
                System.out.println("pog");
                //ciclo inifinito fino a quando il giocatore 2 non si connette
                while(gameStart == false){
                    //variabile utilizzata solo per indicare al player 1 l'inizio della partita
                    System.out.println("monkas");
                    if(gameStart == true){
                        objectOutputStream.writeObject(k);
                        System.out.println("kekw");
                        gameStart2 = true;
                    }
                    System.out.println("azz");
                }
                if(gameStart2 == false){
                    objectOutputStream.writeObject(k);
                }
            }
            else{
                int k = 10;
                objectOutputStream.writeObject(k);
            }
            //inizializzazione dell'ggetto coordinates
            Coordinates coordinates;
            
            //ciclo infinito per l'invio delle coordinate da un client all'altro
            while(check){
                  coordinates = (Coordinates) objectInputStream.readObject();
                  server.send(coordinates,player);
            }
            
            socket.close();
        } catch(IOException e){
            //System.out.println(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send (Coordinates coordinates) throws IOException{
        objectOutputStream.writeObject(coordinates);
    }

    //funzione utilizzata per settare una variabile booleana che verrà utilizzata per informare il giocatore 1 dell'inizio della partita
    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }
    
}
