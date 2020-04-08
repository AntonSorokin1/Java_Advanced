package localStorage;

import entity.BookingRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestData {
    public static volatile List<BookingRequest> bookingRequests = new ArrayList<>();
    public static boolean end = false;

    private RequestData() { }

    public static synchronized void addRequest(BookingRequest request) {
        bookingRequests.add(request);
        RequestData.class.notifyAll();
    }

    public static synchronized BookingRequest getRequest() {
        try {
            while (bookingRequests.isEmpty())
                if (!end) RequestData.class.wait();
                else return null;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bookingRequests.remove(0);
    }

    public static synchronized boolean isRequestEmpty() {
        return bookingRequests.isEmpty();
    }

    public static synchronized void setEnd() {
        end = true;
    }
}
