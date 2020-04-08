package tasks;

import localStorage.RequestData;
import threads.ConsumerRunnable;
import threads.ProducerRunnable;

import java.util.ArrayList;
import java.util.List;

public class Task1 {
    public void start() {
        List<Thread> producerThreads = new ArrayList<>();
        List<Thread> consumerThreads = new ArrayList<>();

        Runnable producer = new ProducerRunnable();
        for (int i = 0; i < 3; i++) {
            producerThreads.add(new Thread(producer){{start();}});
        }

        Runnable consumer = new ConsumerRunnable();
        for (int i = 0; i < 6; i++) {
            consumerThreads.add(new Thread(consumer){{start();}});
        }

        try {
            for (Thread producerThread : producerThreads) {
                producerThread.join();
            }
            RequestData.setEnd();
            for (Thread consumerThread : consumerThreads) {
                consumerThread.join();
            }
        }
        catch (InterruptedException ignored) { }
    }
}
