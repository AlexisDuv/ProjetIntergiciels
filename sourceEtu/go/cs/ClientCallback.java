package go.cs;

import java.rmi.RemoteException;
import go.Observer;

public class ClientCallback implements Callback {

    Observer observer;
    
    public ClientCallback(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void wakeUp() throws RemoteException {
        observer.update();
    }

}
