package go.sock;

import go.Direction;
import go.Observer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChannelMaster<T> implements go.Channel<T> {

    go.shm.Channel<T> channel;

    public ChannelMaster(String name) {
        channel = new go.shm.Channel<>(name);
        bind();
    }

    public void bind() {
        try (ServerSocket serveurSocket = new ServerSocket(1234)) {
            System.out.println("Serveur en attente de connexion...");
            Socket socket = serveurSocket.accept();
            System.out.println("Connexion établie avec le client");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                new Thread(() -> {
                    try {
                        Data<T> data = (Data<T>) in.readObject();
                        if (data.command.equals("in")) {
                            out.writeObject(channel.in());
                        } else {
                            channel.out(data.value);
                        }
                    } catch (ClassNotFoundException |IOException ex) {}
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void out(T v) {
        channel.out(v);
    }

    @Override
    public T in() {
        return channel.in();
    }

    @Override
    public String getName() {
        return channel.getName();
    }

    @Override
    public void observe(Direction direction, Observer observer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'observe'");
    }


}
