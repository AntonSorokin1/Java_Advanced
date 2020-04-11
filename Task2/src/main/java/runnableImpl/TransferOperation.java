package runnableImpl;

import entity.Customer;
import services.AccountService;

import java.util.concurrent.Callable;

public class TransferOperation implements Callable {
    private AccountService service;
    private Customer from, to;
    private Long value;

    public TransferOperation(AccountService service, Customer from, Customer to, Long value) {
        this.service = service;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    public Object call() throws Exception {
        service.transferMoney(from, to, value);
        return null;
    }
}
