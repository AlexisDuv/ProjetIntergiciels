package go.test;

import go.Channel;
import go.Factory;

/*6 outs */
public class testShm02 {
    private static void quit(String msg) {
        System.out.println("TestShm02: " + msg);
        System.exit(msg.equals("ok") ? 0 : 1);
    }

    public static void main(String[] a) {
        Factory factory = new go.shm.Factory();
        Channel<Integer> c = factory.newChannel("c");
        Channel<String> c2 = factory.newChannel("c2");

        new Thread(() -> {
                try { Thread.sleep(2000);  } catch (InterruptedException e) { }
                quit("KO (deadlock)");
        }).start();
        
        new Thread(() -> {
                try { Thread.sleep(100);  } catch (InterruptedException e) { }
                
                c.out(4);
                c.out(17);
                c.out(666);

                c2.out("hello");
                c2.out("world");
                
    }).start();

    new Thread(() -> {
        try { Thread.sleep(300);  } catch (InterruptedException e) { }
        c.out(766);
    }).start();

    
        new Thread(() -> {
                int v = c.in();
                System.out.println("1 " + v);

                if (v != 4 && v != 17 && v !=666 && v!= 766) quit("KO");
    }).start();

        new Thread(() -> {
            int v = c.in();
            System.out.println("2 " + v);
            if (v != 4 && v != 17 && v !=666 && v!= 766) quit("KO");
    }).start();
    
        new Thread(() -> {
            int v = c.in();
            System.out.println("3 " + v);
            if (v != 4 && v != 17 && v !=666 && v!= 766) quit("KO");
    }).start();

    new Thread(() -> {
        int v = c.in();
        System.out.println("4 " + v);
        if (v != 4 && v != 17 && v !=666 && v!= 766) quit("KO");
}).start();

        new Thread(() -> {
            String v = c2.in();
            if (v != "hello") quit("KO");
     }).start();

        new Thread(() -> {
            String v = c2.in();
            quit(v == "world" ? "ok" : "KO");
    }).start();

        
                   
    }
}  

