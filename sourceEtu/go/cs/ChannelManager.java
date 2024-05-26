package go.cs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChannelManager extends Remote{

    Channel getChannel (String name) throws RemoteException;
    void createChannel (String name) throws RemoteException;

    
}
