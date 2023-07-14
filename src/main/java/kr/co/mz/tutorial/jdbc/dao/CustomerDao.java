package kr.co.mz.tutorial.jdbc.dao;

import kr.co.mz.tutorial.DatabaseAccessException;
import kr.co.mz.tutorial.jdbc.model.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerDao { //Dao 객체 생성할때 생성자로 connection 바로 초기화??
    private static final String INSERT_QUERY = "insert into customer (customer_id, password, name, address, email) values (?, ?, ?, ?, ?)";
    private static final String COMPARE_QUERY = "select 1, seq from customer where customer_id = ? and password = ?";

    private static final String POINT_QUERY = "update customer set point = point + 50 where seq = ?";


    private final Connection connection;
    private Customer customer;

    public CustomerDao(Connection connection) {
        this.connection = connection;
    }

    public CustomerDao(Connection connection, Customer customer) throws SQLException {
        this.connection = connection;
        this.customer = customer;
    }


    public void join() throws SQLException {
        var preparedStatement = connection.prepareStatement(INSERT_QUERY);

        preparedStatement.setString(1, customer.getCustomerId());
        preparedStatement.setString(2, customer.getPassword());
        preparedStatement.setString(3, customer.getName());
        preparedStatement.setString(4, customer.getAddress());
        preparedStatement.setString(5, customer.getEmail());
        preparedStatement.execute();

    }

    public int[] compareInfo() throws SQLException {
        int[] result = new int[2];
        var preparedStatement = connection.prepareStatement(COMPARE_QUERY);

        preparedStatement.setString(1, customer.getCustomerId());
        preparedStatement.setString(2, customer.getPassword());
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            result[0] = resultSet.getInt(1);
            result[1] = resultSet.getInt(2);
        }
        return result;

    }

    public Optional<Customer> findByUsername(String username) {
        final String query = "select * from customer where customer_id = ?";
        try (var preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, username);
            var resultSet = preparedStatement.executeQuery();
            Customer customer = null;
            if (resultSet.next()) {
                customer = Customer.fromResultSet(resultSet);
            }
            return Optional.ofNullable(customer);
        } catch (SQLException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다.:" + sqle.getMessage(), sqle);
        }
    }

    public int duplicateCheck(String username) throws SQLException {
        final String query = "select 1 from customer where customer_id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        var resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public void plusPoint(int customerSeq) throws SQLException {
        var preparedStatement = connection.prepareStatement(POINT_QUERY);
        preparedStatement.setInt(1, customerSeq);
        preparedStatement.execute();
    }
}
