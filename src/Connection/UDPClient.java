package Connection;

import java.io.IOException;
import java.net.*;

public class UDPClient {
    private DatagramSocket datagram;
    private InetAddress serverAddress;
    private int port;

    public UDPClient(String destination, int port) {
        try {
            this.serverAddress = InetAddress.getByName(destination);
            this.port = port;
            this.datagram = new DatagramSocket(this.port);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendPosition(int x, int y) {
        String posX = String.valueOf(x);
        String posY = String.valueOf(y);
        try {
            datagram.send(new DatagramPacket(posX.getBytes(), posX.getBytes().length, serverAddress, port));
            datagram.send(new DatagramPacket(posY.getBytes(), posY.getBytes().length, serverAddress, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPositions() {

    }
}
