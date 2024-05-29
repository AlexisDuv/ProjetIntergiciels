package go.cs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Api extends Remote{

    ServerChannel newChannel(String name) throws RemoteException;

}
