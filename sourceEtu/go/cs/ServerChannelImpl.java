package go.cs;
import go.Direction;
import go.Observer;
import go.shm.Channel;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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

    @Override
    public void observe(Direction dir, Observer obs) throws RemoteException {
        shmChannel.observe(dir,obs);
    }


}
