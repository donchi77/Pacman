package Connection;

import Players.Player;
import Players.Players;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    //Variabili globali
    Socket[] socketD = new Socket[4];
    //Player player;
    Players players;
    ObjectInputStream objInput;

    public TCPServer(){
    }

    public static void main(String[] args) {
        try{
            System.out.println("-------------");
            System.out.println("Server PacMan");
            System.out.println("-------------");

            //creazione della connection socket sulla porta 8080
            System.out.println("Creazione della socket...");

            ServerSocket socketC = new ServerSocket(8080);
            TCPServer server = new TCPServer();
            server.acc(socketC);
            server.game();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void game() {
        System.out.println("Tutti i giocatori necessari connessi");


    }

    public void acc(ServerSocket socketC){
        //aspettando la accept
        System.out.println("Aspettando la connessione di tutti i player...");
        for(int i = 0; i < 4; i++) {
            try {
                socketD[i] = socketC.accept();
                objInput = new ObjectInputStream(new BufferedInputStream(socketD[i].getInputStream()));
                // TODO player objects
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Player " + i + " connesso");
        }
    }
}
