package kr.co.mz.tutorial.jdbc.model;

public class Customer extends AbstractModel {
    private int seq;
    private String customerId;
    private String password;
    private String name;
    private String address;
    private String email;

    public Customer() {
    }

    public Customer(String customerId, String password, String name, String address, String email) {
        this.customerId = customerId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
