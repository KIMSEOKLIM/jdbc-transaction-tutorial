package kr.co.mz.tutorial.jdbc.dao;

import kr.co.mz.tutorial.jdbc.model.Customer;

import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CustomerDao extends HttpServlet { //Dao 객체 생성할때 생성자로 connection 바로 초기화??
    private static final String INSERT_QUERY = "insert into customer (customer_id, password, name, address, email) values (?, ?, ?, ?, ?)";
    private static final String COMPARE_QUERY = "select 1, seq from customer where customer_id = ? and password = ?";

    private final Connection connection;
    private final Customer customer;

    public CustomerDao(Customer customer, DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
        this.customer = customer;
    }


    public void customerInsert() throws SQLException {
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
}
