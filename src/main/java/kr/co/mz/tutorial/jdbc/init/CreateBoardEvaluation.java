package kr.co.mz.tutorial.jdbc.init;

import kr.co.mz.tutorial.jdbc.db.HikariPoolFactory;
import kr.co.mz.tutorial.jdbc.model.BoardEvaluation;
import kr.co.mz.tutorial.jdbc.query.SearchEvaluationByCustomerSeq;
import kr.co.mz.tutorial.jdbc.query.SelectOneInBoardEvaluation;
import kr.co.mz.tutorial.jdbc.transaction.UpdateBoardEvaluation;

import java.io.IOException;
import java.sql.SQLException;

public class CreateBoardEvaluation {


    public static void main(String[] args) throws IOException, SQLException {


        BoardEvaluation boardEvaluation = new BoardEvaluation(3, 3);
        setInsertBoardEvaluation(boardEvaluation);
    }

    private static void setInsertBoardEvaluation(BoardEvaluation boardEvaluation) throws SQLException, IOException {
//        final String select_count = "SELECT COUNT(*) FROM board_evaluation WHERE uk_customer_board = ? - ?";
        //        final String insert_board_evaluation = "INSERT INTO board_evaluation (customer_seq, board_seq) VALUES (?, ?) ON DUPLICATE KEY UPDATE evaluation = evaluation + 1";
//        final String select_count = "SELECT COUNT(*) FROM board_evaluation WHERE customer_seq = ? AND board_seq = ?";

        var datasource = HikariPoolFactory.createHikariDataSource();
        var connection = datasource.getConnection();

        connection.setAutoCommit(false);

        int existInt = SelectOneInBoardEvaluation.exist(connection, boardEvaluation);


        final String insert_board_evaluation = "INSERT IGNORE INTO board_evaluation (customer_seq, board_seq) VALUES (?, ?)"; //똑같은 값들어오면 에러 -> 에러안나고 값 안오르게 하기 (이것은 성공했지만, board_seq가 같은 값인 evaluation은 모두 1증가해야함...)


        try (
                var prepareStatement = connection.prepareStatement(insert_board_evaluation);
        ) {
            if (existInt != 1) {
                prepareStatement.setInt(1, boardEvaluation.getCustomerSeq());
                prepareStatement.setInt(2, boardEvaluation.getBoardSeq());
                prepareStatement.execute();
            }
            boardEvaluation = SearchEvaluationByCustomerSeq.evaluated(connection, boardEvaluation);
//            System.out.println(boardEvaluation.getEvaluation());
//            System.out.println(boardEvaluation.getCustomerSeq());
//            System.out.println(boardEvaluation.getCustomerSeq());
            UpdateBoardEvaluation.updateBoardEvaluation(connection, boardEvaluation);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }


}
