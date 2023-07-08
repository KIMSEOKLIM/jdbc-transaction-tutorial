package kr.co.mz.tutorial.jdbc.query;

import kr.co.mz.tutorial.jdbc.db.HikariPoolFactory;
import kr.co.mz.tutorial.jdbc.model.BoardComment;
import kr.co.mz.tutorial.jdbc.model.Customer;

import java.io.IOException;
import java.sql.SQLException;

public class SearchCommentByCustomerSeq {
    private static final String QUERY = """
            select c.customer_id, c.name, bc.content, bc.created_time, bc.modified_time from customer c 
            inner Join board_comment bc 
            on c.seq = bc.customer_seq
                                   """;

    public static void main(String[] args) throws IOException, SQLException {
        var datasource = HikariPoolFactory.createHikariDataSource();
        var connection = datasource.getConnection();
        var preparedStatement = connection.prepareStatement(QUERY);
        var resultSet = preparedStatement.executeQuery();
        Customer customer = new Customer();
        BoardComment boardComment = new BoardComment();
        while (resultSet.next()) {
            customer.setCustomerId(resultSet.getString(1));
            customer.setName(resultSet.getString(2));
            boardComment.setContent(resultSet.getString(3));
            boardComment.setCreatedTime(resultSet.getTimestamp(4));
            boardComment.setModifiedTime(resultSet.getTimestamp(5));

            System.out.println(customer.getCustomerId() + customer.getName() + boardComment.getContent() + boardComment.getCreatedTime() + boardComment.getModifiedTime());
        }


    }
}
