package go.test;

import go.*;

/* select mixte */
public class TestShm14 {

    private static void quit(String msg) {
        System.out.println("TestShm13: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.shm.Factory();
        Channel<Integer> c1 = factory.newChannel("c1");
        Channel<Integer> c2 = factory.newChannel("c2");
        Channel<Integer> c3 = factory.newChannel("c3");

        Selector s = factory.newSelector(java.util.Map.of(c1, Direction.Out,
                                                          c2, Direction.Out,
                                                          c3, Direction.In));
        
        Selector s2 = factory.newSelector(java.util.Map.of(c1, Direction.Out,
                                                          c2, Direction.Out,
                                                          c3, Direction.In));

        // new Thread(() -> {
        //         try { Thread.sleep(2000);  } catch (InterruptedException e) { }
        //         quit("KO (deadlock)");
        // }).start();
        
        new Thread(() -> {
                int v = c1.in();
                if (v != 4) quit("KO");
        }).start();

        new Thread(() -> {
            try { Thread.sleep(100);  } catch (InterruptedException e) { }
            @SuppressWarnings("unchecked")
            Channel<Integer> c = s.select();
            c.out(4);
        }).start();

        new Thread(() -> {
            try { Thread.sleep(100);  } catch (InterruptedException e) { }
            @SuppressWarnings("unchecked")
            Channel<Integer> c = s2.select();
            c.out(4);
        }).start();
        
                   
    }
}
