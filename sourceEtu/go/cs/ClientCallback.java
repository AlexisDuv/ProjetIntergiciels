package go.cs;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import go.Observer;

public class ClientCallback extends UnicastRemoteObject implements Callback {

    Observer observer;

    public ClientCallback() throws RemoteException {
}
    
    public ClientCallback(Observer observer) throws RemoteException{
        this.observer = observer;
    }

    @Override
    public void wakeUp() throws RemoteException {
        observer.update();
    }

}
