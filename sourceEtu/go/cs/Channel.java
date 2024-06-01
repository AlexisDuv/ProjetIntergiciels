package go.cs;

import go.Direction;
import go.Observer;
import java.rmi.Naming;


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
        ClientCallback cb;
        try {
            cb = new ClientCallback(observer);
        

            Api api;
       
            api = (Api) Naming.lookup("rmi://localhost:1099/api");

         
            api.wakeMeUp(this.name, direction, cb);
  
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}