package go.test;

import go.Channel;
import go.sock.Factory;

public class TestSocketa {

    private static void quit(String msg) {
        System.out.println("TestSocketa: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.sock.Factory();
        Channel<Integer> c = factory.newChannel("c");

        new Thread(() -> {
                try { Thread.sleep(5000);  } catch (InterruptedException e) { }
                quit("KO (deadlock)");
        }).start();
        
        c.out(4);
        quit("ok");
    }
    
}
