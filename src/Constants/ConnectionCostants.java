package Constants;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionCostants {
    public static int PORT = 8080;
    public static InetAddress IP_SERVER;

    static {
        try {
            IP_SERVER = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
