package kr.co.mz.tutorial.jdbc.transaction;

import kr.co.mz.tutorial.jdbc.model.BoardEvaluation;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateBoardEvaluation {

    private static final String QUERY = "update board set evaluation_total = ?";

    public static void updateBoardEvaluation(Connection connection, BoardEvaluation boardEvaluation) throws SQLException {
        var preparedStatement = connection.prepareStatement(QUERY);
        System.out.println(boardEvaluation.getEvaluation() + "-----------------" + boardEvaluation.getCustomerSeq());
        preparedStatement.setInt(1, boardEvaluation.getEvaluation());
        preparedStatement.execute();
    }
}
