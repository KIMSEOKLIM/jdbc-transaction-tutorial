package kr.co.mz.tutorial.jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class BoardEvaluationDao {
    private static String SELECT_QUERY = "select count(*) from board_evaluation where customer_seq = ? AND board_seq = ?";
    private static String INSERT_QUERY = "insert into board_evaluation (customer_seq, board_seq) values(?, ?)";
    private static String UPDATE_EVALUATION_QUERY = "update board set evaluation_total = evaluation_total + 1 where seq = ?";

    private final Connection connection;

    public BoardEvaluationDao(Connection connection) {
        this.connection = connection;
    }

    public int duplicationCheck(int customerSeq, int boardSeq) throws SQLException {
        var preparedStatement = connection.prepareStatement(SELECT_QUERY);
        preparedStatement.setInt(1, customerSeq);
        preparedStatement.setInt(2, boardSeq);
        var resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;

    }

    public void insert(int customerSeq, int boardSeq) throws SQLException {
        var preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setInt(1, customerSeq);
        preparedStatement.setInt(2, boardSeq);
        preparedStatement.execute();

    }

    public int insertWhenNotExists(int customerSeq, int boardSeq) throws SQLException {
        var preparedStatement = connection.prepareStatement("""
                                INSERT INTO board_evaluation (customer_seq, board_seq)
                                SELECT * FROM (SELECT ?, ?) AS tmp
                                WHERE NOT EXISTS (
                                    SELECT 1 FROM board_evaluation
                                    WHERE customer_seq = ? AND board_seq = ?
                                )
                """);
        preparedStatement.setInt(1, customerSeq);
        preparedStatement.setInt(2, boardSeq);
        preparedStatement.setInt(3, customerSeq);
        preparedStatement.setInt(4, boardSeq);
        return preparedStatement.executeUpdate();
    }

    public void update(int boardSeq) throws SQLException {
        var preparedStatementEva = connection.prepareStatement(UPDATE_EVALUATION_QUERY);
        preparedStatementEva.setInt(1, boardSeq);
        preparedStatementEva.execute();
    }
}
