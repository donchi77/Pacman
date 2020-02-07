package Connection;

import Players.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {
    private Player himself;

    private Socket clientSocket;
    private PrintWriter toServer;
    private BufferedReader fromServer;

    public TCPClient(String username) {
        himself = new Player(username);
    }

    public void connectToServer(String IPv4Server, int port) {
        try {
            clientSocket = new Socket(IPv4Server, port);
            toServer = new PrintWriter(clientSocket.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectToServer() {
        try {
            toServer.close();
            fromServer.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receivePlayerInformation() {

    }
}
