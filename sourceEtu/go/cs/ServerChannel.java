package go.cs;

import go.Direction;
import go.Observer;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerChannel<T> extends Remote{

    public T in() throws RemoteException;

    public void out(T v) throws RemoteException;

    public void observe(Direction dir, Observer obs) throws RemoteException;

}
