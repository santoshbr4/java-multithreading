import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String []args) {
        Queue<Integer> q = new LinkedList<Integer>();

        Producer producer = new Producer(q, 10);
        Consumer consumer = new Consumer(q, 10);

        producer.start();
        consumer.start();
    }
}
