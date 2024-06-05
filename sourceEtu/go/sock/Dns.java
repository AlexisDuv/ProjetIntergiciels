package go.sock;

import java.rmi.Remote;

public interface Dns extends Remote {

    public boolean exists(String name) throws java.rmi.RemoteException;

    public InfosConnection get(String name) throws java.rmi.RemoteException;

    public void add(String name, InfosConnection infos) throws java.rmi.RemoteException;
    
}
