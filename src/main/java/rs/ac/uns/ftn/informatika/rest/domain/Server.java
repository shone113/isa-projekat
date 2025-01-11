package rs.ac.uns.ftn.informatika.rest.domain;

public class Server {
    private String url;
    private int activeConnections; // Broj aktivnih konekcija

    public Server(String url) {
        this.url = url;
        this.activeConnections = 0;
    }

    public String getUrl() {
        return url;
    }

    public int getActiveConnections() {
        return activeConnections;
    }

    public void incrementConnections() {
        activeConnections++;
    }

    public void decrementConnections() {
        activeConnections--;
    }
}
