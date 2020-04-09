package threads;

import entity.BookingRequest;
import localStorage.RequestData;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class ProducerRunnable implements Runnable {
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        long threadId = currentThread.getId();

        synchronized (this) {
            System.out.println(String.format("Producer %d is started!", threadId));
        }

        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            String ad = "The best hotel";
            Calendar calendar = new GregorianCalendar(random.nextInt(31) + 1, Calendar.JULY, 2020);
            String hotel = "Hotel " + (threadId * 10 + i);
            BookingRequest request = new BookingRequest(ad, calendar.getTime(), hotel);

            synchronized (this) {
                RequestData.addRequest(request);
                System.out.println(String.format("Producer %d is add new request: %s", threadId, request.toString()));
            }
        }

        synchronized (this) {
            System.out.println(String.format("Producer %d is dead!", threadId));
        }
    }
}
