package kr.co.mz.tutorial.jdbc.init;

import kr.co.mz.tutorial.jdbc.db.HikariPoolFactory;
import kr.co.mz.tutorial.jdbc.model.Customer;

import java.io.IOException;
import java.sql.SQLException;

public class CreateCustomer {
    private static final String CUSTOMER_INSERT_QUERY = "INSERT INTO customer (customer_id, password, name, address, email) VALUES (?,?,?,?,?)";

    public static void main(String[] args) throws SQLException, IOException {

        Customer customer = new Customer("seoklim3", "rlatjrf3", "seokli3", "Gangnam2", "seoklim1@naver.com");
        insertCustomer(customer);
    }

    private static void insertCustomer(Customer customer) throws IOException, SQLException {
        var datasource = HikariPoolFactory.createHikariDataSource();
        var connection = datasource.getConnection();
        connection.setAutoCommit(false);
        try (
                var preparedStatement = connection.prepareStatement(CUSTOMER_INSERT_QUERY);
        ) {
            preparedStatement.setString(1, customer.getCustomerId());
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getName());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getEmail());

            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }


}
