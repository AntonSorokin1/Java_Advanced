package services.impl;

import entity.Customer;
import services.AccountService;
import utilities.AccountUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final String FILE_EXTENSION = ".acc";

    @Override
    public void writeAccount(Customer customer) {
        String fileName = customer.getId() + "_" + customer.getName() + FILE_EXTENSION;

        File file = null;
        try {
            File fileDir = new File(AccountServiceImpl.class.getClassLoader().getResource("").getFile(), "accounts");
            if (!fileDir.exists()) System.out.println("File directory creating - " + fileDir.mkdir());
            file = new File(fileDir, fileName);
            if (!file.exists()) System.out.println("File creating - " + file.createNewFile());
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
    public void transferMoney(Customer from, Customer to, Long value) {
        synchronized (this) {
            System.out.println(String.format("Transfer from %s to %s started!", from, to));
        }
        synchronized (from) {
            AccountUtility.changeAccountBalanceTo(from, value * -1);
            System.out.println(String.format("%d transfer from %s complete!", value, from.toString()));
        }
        synchronized (to) {
            AccountUtility.changeAccountBalanceTo(to, value);
            System.out.println(String.format("%d transfer to %s complete!", value, to.toString()));
        }
        synchronized (this) {
            System.out.println(String.format("Transfer from %s to %s ended!", from, to));
        }
    }
}
