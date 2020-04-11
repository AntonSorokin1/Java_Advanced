import entity.Customer;
import services.AccountService;
import services.impl.AccountServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Task2 {
    private final int NUMBER_OF_THREADS = 20;
    private final int NUMBER_OF_OPERATIONS = 1000;

    public void start() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "John", 0L),
                new Customer(2L, "Nick", 100L),
                new Customer(3L, "Bill", 25L),
                new Customer(4L, "Fox", 1000000L),
                new Customer(5L, "Rick", 1L),
                new Customer(6L, "Mike", -1L),
                new Customer(7L, "Mary", -100000L),
                new Customer(8L, "Katy", -25L),
                new Customer(9L, "Ashe", -100L),
                new Customer(10L, "Silly", 0L)
        );

        AccountService service = new AccountServiceImpl();

        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_THREADS / 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < NUMBER_OF_OPERATIONS / 2; j++)
                    service.transferMoney(customers.get(0), customers.get(random.nextInt(8) + 1), random.nextLong());
            }){{start();}};
            new Thread(() -> {
                for (int j = 0; j < NUMBER_OF_OPERATIONS / 2; j++)
                    service.transferMoney(customers.get(random.nextInt(8) + 1), customers.get(0), random.nextLong());
            }){{start();}};
        }
    }
}
