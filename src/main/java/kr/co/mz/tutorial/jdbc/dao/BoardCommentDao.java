package kr.co.mz.tutorial.jdbc.dao;

import kr.co.mz.tutorial.jdbc.model.BoardComment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardCommentDao {
    private static final String COMMENT_QUERY = "SELECT b.customer_seq, b.content, c.customer_id FROM board_comment b JOIN customer c ON b.customer_seq = c.seq WHERE board_seq = ?";
    private static final String COMMENT_INSERT_QUERY = "insert into board_comment (content, customer_seq, board_seq) values (?, ?, ?)";

    private final Connection connection;

    public BoardCommentDao(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }


    public List<BoardComment> view(int boardSeq) throws SQLException {
        List<BoardComment> commentList = new ArrayList<>();

        var preparedStatementComment = connection.prepareStatement(COMMENT_QUERY);
        preparedStatementComment.setInt(1, boardSeq);
        var resultSetComment = preparedStatementComment.executeQuery();
        while (resultSetComment.next()) {
            BoardComment boardComment = new BoardComment();
            boardComment.setCustomerSeq(resultSetComment.getInt(1));
            boardComment.setContent(resultSetComment.getString(2));
            boardComment.setCustomerId(resultSetComment.getString(3));
            commentList.add(boardComment);
        }
        return commentList;
    }

    public void insert(String content, int customerSeq, int boardSeq) throws SQLException {
        var preparedStatement = connection.prepareStatement(COMMENT_INSERT_QUERY);
        preparedStatement.setString(1, content);
        preparedStatement.setInt(2, customerSeq);
        preparedStatement.setInt(3, boardSeq);
        preparedStatement.execute();
    }
}
