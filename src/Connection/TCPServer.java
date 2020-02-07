package Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    //Variabili globali
    static Socket[] socketD = new Socket[4];
    static TCPServer server;

    public TCPServer(){
        server = new TCPServer();
    }

    public static void main(){
        try{
            System.out.println("-------------");
            System.out.println("Server PacMan");
            System.out.println("-------------");

            //creazione della connection socket sulla porta 8080
            System.out.println("Creazione della socket...");
            ServerSocket socketC = new ServerSocket(8080);

            server.acc(socketC);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void acc(ServerSocket socketC){
        //aspettando la accept
        System.out.println("Aspettando la connessione di tutti i player...");
        for(int i = 0; i < 4; i++){
            try {
                socketD[i] = socketC.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Player " + i + " connesso");
        }
    }
}
