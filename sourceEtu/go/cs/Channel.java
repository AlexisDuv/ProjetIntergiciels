package go.cs;

import go.Direction;
import go.Observer;

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

    public String getName() {
        return this.name;
    }

    public void observe(Direction direction, Observer observer) {
        // TODO
    }
}