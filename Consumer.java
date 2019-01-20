import java.util.Queue;

public class Consumer extends Thread{
    Queue<Integer> q;
    Integer size;

    Consumer(Queue<Integer> q, Integer size) {
        this.q = q;
        this.size = size;
    }

    public void run() {
            while(true) {
                synchronized (q) {
                    while(q.size() == 0) {
                        try {
                            q.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Consumed " + q.poll());
                    q.notify();
                }
            }
    }
}
