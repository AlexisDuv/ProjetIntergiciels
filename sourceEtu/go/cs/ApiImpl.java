package go.cs;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ApiImpl extends java.rmi.server.UnicastRemoteObject implements Api{

    Map<String, ServerChannel> channels;

    protected ApiImpl() throws RemoteException {
        super();
        this.channels = new HashMap<String, ServerChannel>();
    }

    @Override
    public ServerChannel newChannel(String name) {

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

}
