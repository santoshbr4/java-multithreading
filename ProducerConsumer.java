import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;

public class ProducerConsumer {

    private static final Integer POISON = 100; //Poison object value

    //Class declared static because non static private class cannot be accessed from main.
    private static class Producer implements Runnable {

        BlockingQueue q;

        Producer(BlockingQueue pq) {
            q = pq;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("Producer produces " + i);
                    q.put(i);
                }
                catch (InterruptedException e) {
                    System.out.println(Producer.class.getName() + " errored with exception " + e);
                }
            }

            try {
                q.put(POISON); //Puts the poison object to terminate the Consumer
                System.out.println("Producer exits!! ");
            }
            catch (InterruptedException e) {
                System.out.println(Producer.class.getName() + " errored with exception " + e);
            }
        }
    }

    private static class Consumer implements Runnable {

        BlockingQueue q;

        Consumer(BlockingQueue pq) {
            q = pq;
        }

        @Override
        public void run() {
            Integer element = 100; //100 will be our poison object to terminate the thread.
            while(true) {
                try {
                    element = (Integer) q.take();
                    if(element == POISON) {
                        System.out.println("Consumer exits!! ");
                        exit(0);
                    }
                    System.out.println("Consumer consumes " + q.take());
                } catch (InterruptedException e) {
                    System.out.println(Producer.class.getName() + " errored with exception " + e);
                }
            }
        }
    }

    public static void main(String []args) {
        BlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        Thread t1 = new Thread(new Producer(q));
        Thread t2 = new Thread(new Consumer(q));

        t1.start();
        t2.start();

        try {
            Thread.sleep(1000000); //Dummy sleep to check results. But this isn't working. Need to check.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
