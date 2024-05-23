package go.shm;

import go.Direction;
import go.Observer;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;


public class Channel<T> implements go.Channel<T> {

    String name;
    T content;
    Set <Observer> InObservers;
    Set <Observer> OutObservers;

    private final Semaphore semIn = new Semaphore(0);
    private final Semaphore semOut = new Semaphore(0);
    private final Semaphore semEcr = new Semaphore(0);
    private final Semaphore semObsIn = new Semaphore(1);
    private final Semaphore semObsOut = new Semaphore(1);

    public Channel(String name) {
        this.name = name;
        this.content = null;
        this.InObservers = new HashSet<Observer>();
        this.OutObservers = new HashSet<Observer>();
    }
    
    @Override
    public void out(T v) {
        try {
        System.out.println("out");
        semObsOut.acquire();
        for (Observer observer : OutObservers){
            observer.update();
        }
        OutObservers.removeAll(OutObservers);
        semObsOut.release();
        semIn.release();
        semOut.acquire();
        content = v;
        semEcr.release();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       
    
     @Override
     synchronized public T in() {
        try {
        System.out.println("in");
        semObsIn.acquire();
        for (Observer observer : InObservers){
            observer.update();
        }
        InObservers.removeAll(InObservers);
        semObsIn.release();
            semOut.release();
            semIn.acquire();
            semEcr.acquire();
            T v = content;
            return v;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void observe(Direction dir, Observer observer) {
        // Regarde si la semaphore in est prise
        if (semOut.availablePermits() > 0 && dir == Direction.In){
            observer.update();
        }
        // Regarde si la semaphore out est prise
        else if (semIn.availablePermits() > 0 && dir == Direction.Out){
            observer.update();
        }
        // Ajoute l'observer
        else {
            if (dir == Direction.In){
                InObservers.add(observer);
        }
            else{
                OutObservers.add(observer);
        } 
        }

       
}
        
}