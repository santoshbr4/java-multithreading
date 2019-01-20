import java.util.Queue;
import java.util.Random;

public class Producer extends Thread{
    private Queue<Integer> q;
    Integer size;

    Producer(Queue<Integer> q, Integer size) {
        this.q = q;
        this.size = size;
    }

    @Override
    public void run() {
        for(int i=0;i<100;i++) {
            synchronized (q) {
                while(q.size() == size) {
                    try {
                        q.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Integer gen = i+1;
                q.add(gen);
                System.out.println("Produced " + gen);
                q.notify();
            }
        }
    }
}
