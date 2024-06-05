package go.sock;

import go.Direction;
import go.Observer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChannelSlave<T> implements go.Channel<T> {

String address;
int port;    
String name;

    public ChannelSlave(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }

    @Override
    public void out(T v) {
        try (Socket socket = new Socket(address, port)) {
           ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Data<T>("out", v));
            out.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public T in() {
        System.out.println(address + " " + port);
        try (Socket socket = new Socket(address, port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Data<T>("in", null));
            out.flush();
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
