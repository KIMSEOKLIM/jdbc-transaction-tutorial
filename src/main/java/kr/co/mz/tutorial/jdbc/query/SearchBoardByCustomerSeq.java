package kr.co.mz.tutorial.jdbc.query;

import kr.co.mz.tutorial.jdbc.db.HikariPoolFactory;
import kr.co.mz.tutorial.jdbc.model.Board;
import kr.co.mz.tutorial.jdbc.model.Customer;

import java.io.IOException;
import java.sql.SQLException;

public class SearchBoardByCustomerSeq {
    private static final String QUERY = """
            SELECT c.customer_id, c.name, b.title, b.content, b.created_time, b.modified_time FROM customer c
                                   right JOIN
                               board b ON c.seq = b.customer_seq
                       """;

    public static void main(String[] args) throws IOException, SQLException {
        var datasource = HikariPoolFactory.createHikariDataSource();
        var connection = datasource.getConnection();
        var preparedStatement = connection.prepareStatement(QUERY);
        var resultSet = preparedStatement.executeQuery();
        Customer customer = new Customer();
        Board board = new Board();
        while (resultSet.next()) {
            customer.setCustomerId(resultSet.getString(1));
            customer.setName(resultSet.getString(2));
            board.setTitle(resultSet.getString(3));
            board.setContent(resultSet.getString(4));
            board.setCreatedTime(resultSet.getTimestamp(5));
            board.setModifiedTime(resultSet.getTimestamp(6));
        }


    }
}
