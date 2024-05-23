package go.shm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import go.Direction;
import go.Observer;

public class Channel<T> implements go.Channel<T> {

    String name;
    T content;
    Set <Observer> InObservers;
    Set <Observer> OutObservers;

    private final Semaphore semIn = new Semaphore(0);
    private final Semaphore semOut = new Semaphore(1);


    public Channel(String name) {
        // TODO
        this.name = name;
        this.content = null;
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
            //  while (dir == Direction.Out){
            //     // Supprimer tous les observateurs présents
            //     for (Observer observer : OutObservers){
            //         observer.update();
            //     }
            //     OutObservers.removeAll(OutObservers);

            //     try {
            //         Thread.sleep(100);
            //     } catch (InterruptedException e) {
            //         e.printStackTrace();
            //     }
            // }

            try {
            semOut.acquire();

            content = v;

            semIn.release();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
       
    
     synchronized public T in() {
        System.out.println("in");
        // TODO
   

        for (Observer observer : InObservers){
            observer.update();
        }
        InObservers.removeAll(InObservers);



        // while (dir != Direction.Out)
        //     {
        //         // Supprimer tous les observateurs présents
        //         for (Observer observer : InObservers){
        //             observer.update();
        //         }
        //         InObservers.removeAll(InObservers);

        //         try {
        //             Thread.sleep(100);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }

        try {
            semIn.acquire();
            T v = content;
           // content = null;
            semOut.release();
            return v;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        // TODO
        return this.name;
    }

    public void observe(Direction dir, Observer observer) {
        // TODO

        // Regarde si la semaphore in est prise
        if (semIn.availablePermits() == 0 && dir == Direction.In){
            observer.update();
        }
        // Regarde si la semaphore out est prise
        else if (semOut.availablePermits() == 0 && dir == Direction.Out){
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