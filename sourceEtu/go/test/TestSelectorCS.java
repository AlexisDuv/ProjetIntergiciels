package go.test;

import go.*;

/* select in */
public class TestSelectorCS {

    private static void quit(String msg) {
        System.out.println("TestSelectorCS: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.cs.Factory();
        Channel<Integer> c1 = factory.newChannel("c1");
        Channel<Integer> c2 = factory.newChannel("c2");
        Channel<Integer> c3 = factory.newChannel("c3");
        Channel<Integer> c4 = factory.newChannel("c4");

        Selector s = factory.newSelector(java.util.Set.of(c1, c2, c3), Direction.In); 
        //  new Thread(() -> {
        //          try { Thread.sleep(2000);  } catch (InterruptedException e) { }
        //          quit("KO (deadlock)");
        //  }).start();
        
        new Thread(() -> {
            try { Thread.sleep(200);} catch (InterruptedException e) {}
            int v = c4.in();
            if (v != 6) quit("KO1");
            System.out.println("v = " + v);
        }).start();

        new Thread(() -> {
            try { Thread.sleep(6000);  } catch (InterruptedException e) {}
            System.out.println("enter");
            @SuppressWarnings("unchecked")
                Channel<Integer> c = s.select();
                System.out.println("c = " + c);
                int v = c.in();
                System.out.println("2v = " + v);
                if (v != 4) quit("KO2");
                try { Thread.sleep(9000);} catch (InterruptedException e) {}
                quit("ok");
        }).start();
        
                   
    }
}
