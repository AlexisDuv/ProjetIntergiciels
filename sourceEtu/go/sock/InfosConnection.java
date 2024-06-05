package go.sock;

public class InfosConnection implements java.io.Serializable{

    public InfosConnection(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public String address;
    public int port;
}
