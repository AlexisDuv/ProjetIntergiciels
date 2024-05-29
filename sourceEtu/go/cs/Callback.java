package go.cs;

import java.rmi.Remote;

public interface Callback extends Remote {
    void wakeUp() throws java.rmi.RemoteException;
}
