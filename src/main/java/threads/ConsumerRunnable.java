package threads;

import entity.BookingRequest;
import localStorage.RequestData;

public class ConsumerRunnable implements Runnable {
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        long threadId = currentThread.getId();

        synchronized (this) {
            System.out.println(String.format("Consumer %d is started!", threadId));
        }

        while (true) {
            try {
                synchronized (this) {
                    BookingRequest request = RequestData.getRequest();
                    if (request != null)
                        System.out.println(String.format("Consumer %d is get a request: %s", threadId, request.toString()));
                    else
                        break;
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(String.format("Consumer %d is dead!", threadId));
            }
        }

        synchronized (this) {
            System.out.println(String.format("Consumer %d is dead!", threadId));
        }
    }
}
