package go.cs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerChannel<T> extends Remote{

    public T in() throws RemoteException;

    public void out(T v) throws RemoteException;

}
