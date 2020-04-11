package services.impl;

import entity.Customer;
import exceptions.NotEnoughBalanceException;
import services.AccountService;
import utilities.AccountUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountServiceImpl implements AccountService {
    private final String FILE_EXTENSION = ".acc";
    private Logger logger = Logger.getLogger(AccountService.class.getName());

    @Override
    public void writeAccount(Customer customer) {
        String fileName = customer.getId() + "_" + customer.getName() + FILE_EXTENSION;

        File file = null;
        try {
            File fileDir = new File(AccountServiceImpl.class.getClassLoader().getResource("").getFile(), "accounts");
            if (!fileDir.exists()) logger.log(Level.INFO, "File directory creating - " + fileDir.mkdir());
            file = new File(fileDir, fileName);
            if (!file.exists()) logger.log(Level.INFO,"File creating - " + file.createNewFile());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(customer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeAllAccounts(List<Customer> customers) {
        customers.forEach(this::writeAccount);
    }

    @Override
    public List<Customer> readAllAccounts() {
        List<Customer> customerList = new ArrayList<>();
        File accountFiles = new File(AccountServiceImpl.class.getClassLoader().getResource("accounts").getFile());
        for (File file : accountFiles.listFiles()) {
            try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
                customerList.add((Customer)stream.readObject());
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return customerList;
    }

    @Override
    public Customer getAccountById(Long id) {
        for (Customer customer : readAllAccounts())
            if (customer.getId().equals(id))
                return customer;
        return null;
    }

    @Override
    public void transferMoney(Customer from, Customer to, Long value) throws NotEnoughBalanceException {
        synchronized (this) {
            logger.log(Level.INFO, String.format("Transfer from %s to %s started!", from, to));
        }
        synchronized (from) {
            if (!AccountUtility.checkBalance(from)) throw new NotEnoughBalanceException();
            AccountUtility.changeAccountBalanceTo(from, value * -1);
            logger.log(Level.INFO, String.format("%d transfer from %s complete!", value, from.toString()));
        }
        synchronized (to) {
            AccountUtility.changeAccountBalanceTo(to, value);
            logger.log(Level.INFO, String.format("%d transfer to %s complete!", value, to.toString()));
        }
        synchronized (this) {
            logger.log(Level.INFO, String.format("Transfer from %s to %s ended!", from, to));
        }
    }
}
