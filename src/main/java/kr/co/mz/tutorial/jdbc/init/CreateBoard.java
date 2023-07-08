package kr.co.mz.tutorial.jdbc.init;


import kr.co.mz.tutorial.jdbc.db.HikariPoolFactory;
import kr.co.mz.tutorial.jdbc.model.Board;

import java.io.IOException;
import java.sql.SQLException;

public class CreateBoard {

    private static final String INSERT_BOARD = "insert into board (title, content, customer_seq) values (?, ?, ?)";

    public static void main(String[] args) throws IOException, SQLException {

        Board board = new Board("제목3", "내용3", 2);
        insertBoard(board);

    }

    private static void insertBoard(Board board) throws SQLException, IOException {
        var datasource = HikariPoolFactory.createHikariDataSource();
        var connection = datasource.getConnection();
        connection.setAutoCommit(false);

        try (
                var prepareStatement = connection.prepareStatement(INSERT_BOARD);
        ) {
            prepareStatement.setString(1, board.getTitle());
            prepareStatement.setString(2, board.getContent());
            prepareStatement.setInt(3, board.getCustomerSeq());
            prepareStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
    }
}
