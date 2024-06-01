package go.cs;

import go.Direction;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Api extends Remote{

    ServerChannel newChannel(String name) throws RemoteException;

    void wakeMeUp(String name, Direction direction, Callback cb) throws RemoteException;

}
