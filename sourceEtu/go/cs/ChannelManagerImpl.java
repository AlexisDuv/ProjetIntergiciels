package go.cs;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelManagerImpl extends UnicastRemoteObject implements ChannelManager  {

    private static final long serialVersionUID = 1L;
    private final ConcurrentHashMap<String, Channel> channels;

    protected ChannelManagerImpl() throws RemoteException {
        super();
        channels = new ConcurrentHashMap<>();
    }

    @Override
    public Channel getChannel(String name) throws RemoteException {
        Channel canal = channels.get(name);
        if (canal == null) {
            throw new RemoteException("Canal not found");
        }
        return canal;
    }

    @Override
    public void createChannel(String name) throws RemoteException {
        if (!channels.containsKey(name)) {
            channels.put(name, new Channel(name));
        } else {
            throw new RemoteException("le channel existe déjà");
        }
    }
    
}
