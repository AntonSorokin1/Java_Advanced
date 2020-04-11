package services;

import entity.Customer;

import java.util.List;

public interface AccountService {
    void writeAccount(Customer customer);

    void writeAllAccounts(List<Customer> customers);

    List<Customer> readAllAccounts();

    Customer getAccountById(Long id);

    void transferMoney(Customer from, Customer to, Long value);
}
