package entity;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
    private Long id;
    private String name;
    private Long balance;
    private boolean isBanned;

    public Customer() { }

    public Customer(Long id, String name, Long balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        isBanned = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getBalance() { return balance; }
    public void setBalance(Long balance) { this.balance = balance; }

    public boolean isBanned() { return isBanned; }
    public void setBanned(boolean banned) { isBanned = banned; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Customer " + this.id;
    }
}
