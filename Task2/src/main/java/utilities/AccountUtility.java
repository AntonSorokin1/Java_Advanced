package utilities;

import entity.Customer;

public class AccountUtility {
    public static void changeAccountBalanceTo(Customer customer, Long increment) {
        customer.setBalance(customer.getBalance() + increment);
    }

    public static boolean checkTransfer(Customer customer, Long value) { return customer.getBalance() - value > 0; }

    public static void banAccount(Customer customer) {
        customer.setBanned(true);
    }

    public static void unbadAccount(Customer customer) {
        customer.setBanned(false);
    }
}