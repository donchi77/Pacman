package Connection;

import Layout.Game;
import Players.Player;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends JPanel {
    private Player himself;

    private Socket clientSocket;
    private PrintWriter stringToServer;
    private BufferedReader stringFromServer;
    private ObjectOutputStream objectToServer;
    private ObjectInputStream objectFromServer;

     public void setHimself(String username) {
         this.himself = new Player(username);
     }

    public void connectToServer(InetAddress IPv4Server, int port) {
        try {
            clientSocket = new Socket(IPv4Server, port);
            stringToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            stringFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            objectToServer = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            objectFromServer = new ObjectInputStream((new BufferedInputStream(clientSocket.getInputStream())));

            objectToServer.writeObject(himself);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectToServer() {
        try {
            stringToServer.close();
            stringFromServer.close();
            objectToServer.close();
            objectFromServer.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receivePlayerInformation() {

    }
}
