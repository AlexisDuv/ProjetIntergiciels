package go.test;

import go.*;

/* select in */
public class TestSelectorCS2 {

    private static void quit(String msg) {
        System.out.println("TestShm11: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.cs.Factory();
        Channel<Integer> c4 = factory.newChannel("c4");
        Channel<Integer> c5 = factory.newChannel("c5");
        Channel<Integer> c6 = factory.newChannel("c6");

        Channel<Integer> c1 = factory.newChannel("c1");

        Selector s = factory.newSelector(java.util.Set.of(c4, c5, c6), Direction.Out); 
        new Thread(() -> {
                try { Thread.sleep(2000);  } catch (InterruptedException e) { }
                quit("KO (deadlock)");
        }).start();
        
        new Thread(() -> {
            int v = c1.in();
            if (v != 4) quit("KO");
        }).start();

        new Thread(() -> {
                try { Thread.sleep(100);  } catch (InterruptedException e) { }
                @SuppressWarnings("unchecked")
                Channel<Integer> c = s.select();
                int v = c.in();
                if (v != 4) quit("KO");

                quit("ok");
        }).start();
        
                   
    }
}
