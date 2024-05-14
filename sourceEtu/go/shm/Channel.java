package go.shm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import go.Direction;
import go.Observer;

public class Channel<T> implements go.Channel<T> {

    String name;
    Direction dir;
    ArrayList<T> content;
    Set <Observer> InObservers;
    Set <Observer> OutObservers;


    public Channel(String name) {
        // TODO
        this.name = name;
        this.dir = null;
        this.content = new ArrayList<T>();
        this.InObservers = new HashSet<Observer>();
        this.OutObservers = new HashSet<Observer>();
    }
    
    public void out(T v) {

        System.out.println("out");
        // TODO

        for (Observer observer : OutObservers){
            observer.update();
        }
        OutObservers.removeAll(OutObservers);
            //wait(); 
             while (dir == Direction.Out){
                // Supprimer tous les observateurs présents
                for (Observer observer : OutObservers){
                    observer.update();
                }
                OutObservers.removeAll(OutObservers);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            content.add(v);
            this.dir = Direction.Out;
        }
       
    
     synchronized public T in() {
        System.out.println("in");
        // TODO
        if (dir == null) {
            dir = Direction.In;
            
        }

        for (Observer observer : InObservers){
            observer.update();
        }
        InObservers.removeAll(InObservers);

        while (dir != Direction.Out)
            {
                // Supprimer tous les observateurs présents
                for (Observer observer : InObservers){
                    observer.update();
                }
                InObservers.removeAll(InObservers);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        T v = content.remove(0);
        this.dir = Direction.inverse(dir);
        return v;
        
    }

    public String getName() {
        // TODO
        return this.name;
    }

    public void observe(Direction dir, Observer observer) {
        // TODO
        if (this.dir == dir){
            observer.update();
        }
        else if (dir == Direction.In){
            InObservers.add(observer);
        }
        else{
            OutObservers.add(observer);
        }
}
        
}