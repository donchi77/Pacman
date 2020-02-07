package Connection;

import Players.Player;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    private Player himself;

    private Socket clientSocket;
    private PrintWriter stringToServer;
    private BufferedReader stringFromServer;
    private ObjectOutputStream objectToServer;
    private ObjectInputStream objectFromServer;

    public TCPClient(String username) {
        himself = new Player(username);
    }

    public void connectToServer(String IPv4Server, int port) {
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
