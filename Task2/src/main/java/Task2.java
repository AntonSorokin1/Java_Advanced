import entity.Customer;
import runnableImpl.TransferOperation;
import services.AccountService;
import services.impl.AccountServiceImpl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Task2 {
    private final int NUMBER_OF_THREADS = 20;
    private final int NUMBER_OPERATIONS = 1000;

    public void start() {

        AccountService accountService = new AccountServiceImpl();
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        Random random = new Random();

        List<Customer> customers = accountService.readAllAccounts();

        for (int i = 0; i < NUMBER_OPERATIONS / 2; i++) {
            executorService.submit(
                new TransferOperation(accountService, customers.get(0), customers.get(random.nextInt(8) + 1), random.nextLong())
            );
            executorService.submit(
                new TransferOperation(accountService, customers.get(random.nextInt(8) + 1), customers.get(0), random.nextLong())
            );
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
            accountService.writeAllAccounts(customers);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
