package go.cs;

import go.Direction;
import go.Observer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Channel<T> implements go.Channel<T> {

    ServerChannel sc;
    String name;

    public Channel(String name, ServerChannel sc) {

        this.name = name;

        this.sc = sc;
        
    }

    public void out(T v) {
        try {
            sc.out(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public T in() {
        try {
            return (T) sc.in();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void observe(Direction direction, Observer observer) {
        ClientCallback cb = new ClientCallback(observer);
        Api api;
        try {
            api = (Api) Naming.lookup("rmi://localhost:1099/api");
            try {
                api.wakeMeUp(name, direction, cb);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}