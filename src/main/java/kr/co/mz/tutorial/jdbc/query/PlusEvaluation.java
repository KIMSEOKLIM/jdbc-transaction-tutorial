package kr.co.mz.tutorial.jdbc.query;

import kr.co.mz.tutorial.jdbc.db.HikariPoolFactory;
import kr.co.mz.tutorial.jdbc.model.BoardEvaluation;
import kr.co.mz.tutorial.jdbc.model.Customer;

import java.io.IOException;
import java.sql.SQLException;

public class PlusEvaluation {
    private static final String QUERY = """
            select c.name, ev.evaluation from customer c inner Join board_evaluation ev on c.seq = ev.customer_seq
                                   """;

    public static void main(String[] args) throws IOException, SQLException {
        var datasource = HikariPoolFactory.createHikariDataSource();
        var connection = datasource.getConnection();
        var preparedStatement = connection.prepareStatement(QUERY);
        var resultSet = preparedStatement.executeQuery();
        Customer customer = new Customer();
        BoardEvaluation boardEvaluation = new BoardEvaluation();
        while (resultSet.next()) {
            customer.setName(resultSet.getString(1));
            boardEvaluation.setEvaluation(resultSet.getInt(2));
            System.out.println(customer.getName() + boardEvaluation.getEvaluation());
        }


    }
}
