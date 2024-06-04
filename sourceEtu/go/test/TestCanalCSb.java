package go.test;

import go.Channel;
import go.Factory;

/** Un unique in/out, ici in */
public class TestCanalCSb {

    private static void quit(String msg) {
        System.out.println("TestCS20b: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.cs.Factory();
        Channel<Channel<Integer>> c = factory.newChannel("c");

        new Thread(() -> {
                try { Thread.sleep(5000);  } catch (InterruptedException e) { }
                quit("KO (deadlock)");
        }).start();
        
        Channel<Integer> v = c.in();
        quit(v.getName().equals("c2") ? "ok" : "KO");
    }
}
