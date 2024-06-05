package go.test;

import go.Channel;
import go.sock.Factory;

public class TestSocketb {

    private static void quit(String msg) {
        System.out.println("TestSocketa: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        Factory factory = new go.sock.Factory();
        Channel<Integer> c = factory.newChannel("c");

        new Thread(() -> {
                try { Thread.sleep(5000);  } catch (InterruptedException e) { }
                quit("KO (deadlock)");
        }).start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        
        c.in();
        quit("ok");
    }
    
}
