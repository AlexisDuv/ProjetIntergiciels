package go.test;

import go.Channel;
import go.Direction;
import go.Factory;
import go.Observer;

/* Test observateur 3 in observers 2 out observers */
public class TestShm05 {
    

    private static void quit(String msg) {
        System.out.println("TestShm05: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.shm.Factory();
        Channel<Integer> c = factory.newChannel("c");

        
        class TestInObservation implements Observer{
             public void update(){
                System.out.println("in update successfull");
            }
        }

        class TestOutObservation implements Observer{
            public void update(){
               System.out.println("out update successfull");
           }
       }

    
        new Thread(() -> {
            TestInObservation obs = new TestInObservation();
            TestInObservation obs2 = new TestInObservation();
            TestInObservation obs3 = new TestInObservation();

            TestOutObservation obs4 = new TestOutObservation();
            TestOutObservation obs5 = new TestOutObservation();

            c.observe(Direction.In, obs);
            c.observe(Direction.In, obs3);
            c.observe(Direction.In, obs2);

            c.observe(Direction.Out, obs4);
            c.observe(Direction.Out, obs5);

            try { Thread.sleep(2000);  } catch (InterruptedException e) { }
            quit("ok");
        }).start();

        
         new Thread(() -> {
            try { Thread.sleep(100);  } catch (InterruptedException e) { }
                 int v = c.in();
                if (v != 4) quit("KO");
     }).start();

        new Thread(() -> {
            try { Thread.sleep(100);  } catch (InterruptedException e) { }
            c.out(4);
        }).start();

                   
    }
}
