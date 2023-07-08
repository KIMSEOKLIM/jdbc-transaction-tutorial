package kr.co.mz.tutorial.jdbc.query;

import kr.co.mz.tutorial.jdbc.model.BoardEvaluation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SearchEvaluationByCustomerSeq {

    private static final String QUERY_UPDATE = """
            UPDATE board_evaluation SET evaluation = evaluation + 1 WHERE board_seq = ? AND customer_seq NOT IN (SELECT customer_seq FROM board_evaluation)
                                   """;


    private static final String QUERY_SELECT = """
            select evaluation from board_evaluation where board_seq = ?
                                   """;

    public static BoardEvaluation evaluated(Connection connection, BoardEvaluation boardEvaluation) throws IOException, SQLException {
        if (SelectOneInBoardEvaluation.exist(connection, boardEvaluation) == 1) {
            System.out.println("---------------------11");
            return boardEvaluation;
        } else {
            System.out.println("----------------222");
            var updatePreparedStatement = connection.prepareStatement(QUERY_UPDATE);
            updatePreparedStatement.setInt(1, boardEvaluation.getBoardSeq());
            updatePreparedStatement.execute();


            return boardEvaluation;
        }
    }
}
