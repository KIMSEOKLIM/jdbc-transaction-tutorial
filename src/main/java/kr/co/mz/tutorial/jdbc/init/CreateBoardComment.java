package kr.co.mz.tutorial.jdbc.init;

import kr.co.mz.tutorial.jdbc.db.HikariPoolFactory;
import kr.co.mz.tutorial.jdbc.model.BoardComment;

import java.io.IOException;
import java.sql.SQLException;

public class CreateBoardComment {
    private static final String INSERT_BOARD_COMMENT = "insert into board_comment (content, customer_seq, board_seq) values (?, ?, ?)";

    public static void main(String[] args) throws IOException, SQLException {

        BoardComment boardComment = new BoardComment("댓글의 내용입니다.1", 4, 5);
        insertBoardComment(boardComment);

    }

    private static void insertBoardComment(BoardComment boardComment) throws SQLException, IOException {
        var datasource = HikariPoolFactory.createHikariDataSource();
        var connection = datasource.getConnection();
        connection.setAutoCommit(false);

        try (
                var prepareStatement = connection.prepareStatement(INSERT_BOARD_COMMENT);
        ) {
            prepareStatement.setString(1, boardComment.getContent());
            prepareStatement.setInt(2, boardComment.getCustomerSeq());
            prepareStatement.setInt(3, boardComment.getBoardSeq());
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
