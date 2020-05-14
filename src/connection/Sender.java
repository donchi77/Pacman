package connection;

public class Sender implements Runnable {
    private final Coordinates coordinates;
    //private final ConnectionManager connectionManager;

    public Sender(Coordinates coordinates) {
        this.coordinates = coordinates;
        //this.connectionManager = new ConnectionManager();
    }

    @Override
    public void run() {
       // connectionManager.writeCoordinates(coordinates);
    }
}
