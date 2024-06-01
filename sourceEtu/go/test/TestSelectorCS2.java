package go.test;

import go.*;

/* select in */
public class TestSelectorCS2 {

    private static void quit(String msg) {
        System.out.println("TestSelectorCS2: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.cs.Factory();
        Channel<Integer> c4 = factory.newChannel("c4");
        Channel<Integer> c5 = factory.newChannel("c5");
        Channel<Integer> c6 = factory.newChannel("c6");

        Channel<Integer> c1 = factory.newChannel("c1");

        Selector s = factory.newSelector(java.util.Set.of(c4, c5, c6), Direction.Out); 
        // new Thread(() -> {
        //         try { Thread.sleep(2000);  } catch (InterruptedException e) { }
        //         quit("KO (deadlock)");
        // }).start();
        
        new Thread(() -> {
            try { Thread.sleep(600);  } catch (InterruptedException e) {}
            c1.out(4);
        }).start();

        new Thread(() -> {
                @SuppressWarnings("unchecked")
                Channel<Integer> c = s.select();
                c.out(6);
                quit("ok");
        }).start();
        
                   
    }
}
