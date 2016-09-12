/**
 * Programa para simular deadlocks.
 * Gustavo Wagner: gugawag@gmail.com
 * Código inicial em: http://www.journaldev.com/1058/deadlock-in-java-example
 */

public class Main {

    public static void main(String[] args) throws Exception {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();

        Thread t1 = new Thread(new SyncThread(obj1, obj2), "t1");
        Thread t2 = new Thread(new SyncThread(obj2, obj3), "t2");
        Thread t3 = new Thread(new SyncThread(obj3, obj1), "t3");

        t1.start();
        Thread.sleep(5000);
        t2.start();
        Thread.sleep(5000);
        t3.start();
    }
}


class SyncThread implements Runnable {
    private Object obj1;
    private Object obj2;

    public SyncThread(Object o1, Object o2) {
        this.obj1 = o1;
        this.obj2 = o2;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " obtendo lock em " + obj1);
        synchronized (obj1) {
            System.out.println(name + " obtendo lock em " + obj1);
            work();
            System.out.println(name + " obtendo lock em " + obj2);
            synchronized (obj2) {
                System.out.println(name + " obtendo lock em " + obj2);
                work();
            }
            System.out.println(name + " liberando lock de " + obj2);
        }
        System.out.println(name + " liberando lock de " + obj1);
        System.out.println(name + " Concluindo execução.");
    }

    private void work() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}