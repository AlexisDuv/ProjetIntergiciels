package go.test;

import go.Channel;
import go.Direction;
import go.Factory;
import go.Observer;

/* test observers 3 in observers 2 out obsevers  */
public class TestShm06 {
    

    private static void quit(String msg) {
        System.out.println("TestShm06: " + msg);
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
            c.observe(Direction.Out, obs4);
            try { Thread.sleep(200);  } catch (InterruptedException e) { }
            c.observe(Direction.In, obs3);
            try { Thread.sleep(200);  } catch (InterruptedException e) { }
            c.observe(Direction.In, obs2);

            
            try { Thread.sleep(200);  } catch (InterruptedException e) { }
            c.observe(Direction.Out, obs5);

            try { Thread.sleep(2000);  } catch (InterruptedException e) { }
            quit("ok");
        }).start();

        
         new Thread(() -> {
            try { Thread.sleep(100);  } catch (InterruptedException e) { }
                 int v = c.in();
                 try { Thread.sleep(400);  } catch (InterruptedException e) { }
                 int v2 = c.in();
                if (v != 4) quit("KO");
     }).start();

        new Thread(() -> {
            try { Thread.sleep(150);  } catch (InterruptedException e) { }
            c.out(4);
            try { Thread.sleep(500);  } catch (InterruptedException e) { }
            c.out(6);
        }).start();

                   
    }
}
