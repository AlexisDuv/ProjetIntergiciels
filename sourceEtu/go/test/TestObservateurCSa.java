package go.test;

import go.Channel;
import go.Direction;
import go.Factory;
import go.Observer;


/** (out;out) | in | in */
public class TestObservateurCSa {
    

    private static void quit(String msg) {
        System.out.println("TestObservateurCS: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.cs.Factory();
        Channel<Integer> c = factory.newChannel("c");

        
        class TestObservation implements Observer{
            public void update(){
                System.out.println("update successfull");
            }
        }

    
        new Thread(() -> {
            try { Thread.sleep(5000);  } catch (InterruptedException e) { }
            quit("KO (deadlock)");
    }).start();

    c.observe(Direction.Out, new TestObservation());
    int v = c.in();
    quit(v == 4 ? "ok" : "KO");

                   
    }
}
