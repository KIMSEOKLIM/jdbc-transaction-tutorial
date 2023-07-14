package kr.co.mz.tutorial.jdbc.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer extends AbstractModel {
    private int seq;
    private String customerId;
    private String password;
    private String name;
    private String address;
    private String email;

    private int point;


    public Customer() {

    }


    public Customer(String customerId, String password, String name, String address, String email) {
        this.customerId = customerId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
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

    public static Customer fromResultSet(ResultSet resultSet) throws SQLException {
        var customer = new Customer(
                resultSet.getString("customer_id"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("address"),
                resultSet.getString("email")
        );
        customer.setSeq(resultSet.getInt("seq"));
        customer.setPoint(resultSet.getInt("point"));
        return customer;
    }
}
