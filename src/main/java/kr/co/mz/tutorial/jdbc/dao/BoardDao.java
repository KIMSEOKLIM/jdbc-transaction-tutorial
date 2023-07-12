package kr.co.mz.tutorial.jdbc.dao;

import kr.co.mz.tutorial.jdbc.model.Board;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDao {

    private static final String SELECT_BOARD_ALL = "select b.seq, c.customer_id, b.title, b.evaluation_total, b.created_time, b.modified_time from board b join customer c on b.customer_seq = c.seq";

    private static final String CONTENT_QUERY = "SELECT c.customer_id, b.title, b.content FROM board b JOIN customer c ON b.customer_seq = c.seq WHERE b.seq = ?";
    private static final String INSERT_CONTENT_QUERY = "insert into board (title, content, customer_seq) values (?, ?, ?)";

    private final Connection connection;

    public BoardDao(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public ArrayList<Board> findAll() throws SQLException {
        ArrayList<Board> boardData = new ArrayList<>();
        var preparedStatement = connection.prepareStatement(SELECT_BOARD_ALL);
        var resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Board board = new Board();

            board.setSeq((resultSet.getInt(1)));
            board.setCustomerId(resultSet.getString(2));
            board.setTitle(resultSet.getString(3));
            board.setEvaluationTotal(resultSet.getInt(4));
            board.setCreatedTime(resultSet.getTimestamp(5));
            board.setModifiedTime(resultSet.getTimestamp(6));

            boardData.add(board);
        }
        return boardData;
    }

    public Board findOne(int boardSeq) throws SQLException {
        Board board = new Board();
        var preparedStatementContent = connection.prepareStatement(CONTENT_QUERY);
        preparedStatementContent.setInt(1, boardSeq);
        var resultSet = preparedStatementContent.executeQuery();
        while (resultSet.next()) {
            board.setCustomerId(resultSet.getString(1));
            board.setTitle(resultSet.getString(2));
            board.setContent(resultSet.getString(3));
        }
        return board;
    }

    public void insert(String title, String content, int customerSeq) throws SQLException {
        var preparedStatementInsert = connection.prepareStatement(INSERT_CONTENT_QUERY);
        preparedStatementInsert.setString(1, title);
        preparedStatementInsert.setString(2, content);
        preparedStatementInsert.setInt(3, customerSeq);
        preparedStatementInsert.execute();
    }
}
