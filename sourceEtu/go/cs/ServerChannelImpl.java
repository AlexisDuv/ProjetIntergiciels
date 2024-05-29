package go.cs;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import go.shm.Channel;

public class ServerChannelImpl extends UnicastRemoteObject implements ServerChannel{


    Channel shmChannel;


    public ServerChannelImpl(String name) throws RemoteException{
        this.shmChannel = new Channel(name);
    }

    public Object in() throws RemoteException{
        return shmChannel.in();
    }

    public void out(Object v)throws RemoteException{
        shmChannel.out(v);  
    }


}
