package go.test;

import go.Channel;
import go.Factory;

/** Un unique in/out, ici out */
public class TestCanalCSa {

    private static void quit(String msg) {
        System.out.println("TestCS20a: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.cs.Factory();
        Channel<Channel<Integer>> c = factory.newChannel("c");

        new Thread(() -> {
                try { Thread.sleep(5000);  } catch (InterruptedException e) { }
                quit("KO (deadlock)");
        }).start();
        
        c.out(factory.newChannel("c2"));
        quit("ok");
    }
}
