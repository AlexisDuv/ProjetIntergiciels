package go.test;

import go.Channel;
import go.Direction;
import go.Factory;
import go.Observer;


/*test observateur 1 */
public class TestShm04 {
    

    private static void quit(String msg) {
        System.out.println("TestShm04: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.shm.Factory();
        Channel<Integer> c = factory.newChannel("c");

        
        class TestObservation implements Observer{
            public void update(){
                System.out.println("update successfull");
            }
        }

    
        new Thread(() -> {
            TestObservation obs = new TestObservation();
            c.observe(Direction.In, obs);
            try { Thread.sleep(2000);  } catch (InterruptedException e) { }
            quit("ok");
        }).start();


        new Thread(() -> {
                try { Thread.sleep(100);  } catch (InterruptedException e) { }
                int v = c.in();
                if (v != 4) quit("KO");
        }).start();

                   
    }
}
