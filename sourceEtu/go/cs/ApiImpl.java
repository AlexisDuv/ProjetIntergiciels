package go.cs;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import go.Direction;

public class ApiImpl extends java.rmi.server.UnicastRemoteObject implements Api{

    Map<String, ServerChannel> channels;

    protected ApiImpl() throws RemoteException {
        super();
        this.channels = new HashMap<String, ServerChannel>();
    }

    @Override
    public ServerChannel newChannel(String name) {

        if (channels.containsKey(name)) {
            return channels.get(name);
        }

        try {
            ServerChannel newChannel = new ServerChannelImpl(name);
            channels.put(name, newChannel);
            return newChannel;

        } catch (RemoteException e) {
            System.out.println("Error creating channel");
            e.printStackTrace();
             return null;
        }
       

    }

    @Override
    public void wakeMeUp(String name, Direction direction, Callback cb) throws RemoteException {
        ServerChannel channel = channels.get(name);
        channel.observe(direction,() -> {
            try {
                cb.wakeUp();
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

}
