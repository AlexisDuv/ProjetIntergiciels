package go.sock;

import go.Direction;
import go.Observer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChannelSlave<T> implements go.Channel<T> {

    ObjectInputStream in;
    ObjectOutputStream out;
    String name;

    public ChannelSlave(String name, String address, int port) {
        this.name = name;
        connect(address, port);
    }

    private void connect(String address, int port) {
        try (Socket socket = new Socket(address, port)) {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    @Override
    public void out(T v) {
        try {
            out.writeObject(new Data<T>("out", v));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public T in() {
        try {
            out.writeObject(new Data<T>("in", null));
            return (T) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void observe(Direction direction, Observer observer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'observe'");
    }

}
