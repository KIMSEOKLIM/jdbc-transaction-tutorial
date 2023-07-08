package kr.co.mz.tutorial.jdbc.query;

import kr.co.mz.tutorial.jdbc.model.BoardEvaluation;

import java.sql.Connection;
import java.sql.SQLException;

public class SelectOneInBoardEvaluation {

    private static final String SELECT_QUERY = "SELECT 1 FROM board_evaluation WHERE customer_seq = ? AND board_seq = ?";

    public static int exist(Connection connection, BoardEvaluation boardEvaluation) throws SQLException {
        var preparedStatement = connection.prepareStatement(SELECT_QUERY);
        preparedStatement.setInt(1, boardEvaluation.getCustomerSeq());
        preparedStatement.setInt(2, boardEvaluation.getBoardSeq());
        var resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            System.out.println("---------------------11" + resultSet.getInt(1));

            return 1;
        } else
            return 0;
    }
}
