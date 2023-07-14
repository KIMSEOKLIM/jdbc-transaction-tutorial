package kr.co.mz.tutorial.jdbc.dao;

import kr.co.mz.tutorial.jdbc.model.BoardComment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardCommentDao {
    //    private static final String COMMENT_QUERY = "SELECT b.customer_seq, b.content, c.customer_id FROM board_comment b JOIN customer c ON b.customer_seq = c.seq WHERE board_seq = ?";
    private static final String COMMENT_QUERY = "SELECT b.seq, b.customer_seq, b.content, b.board_comment_seq, c.customer_id, b.board_seq FROM board_comment b JOIN customer c ON b.customer_seq = c.seq WHERE board_seq = ?";
    private static final String COMMENT_INSERT_QUERY = "insert into board_comment (content, board_comment_seq, customer_seq, board_seq) values (?, ?, ?, ?)";
    private static final String COMMENT_REPLY_QUERY = "SELECT c.customer_id, b.content FROM board_comment b JOIN customer c ON b.customer_seq = c.seq WHERE board_seq = ? AND board_comment_seq = ?";

    private final Connection connection;

    public BoardCommentDao(Connection connection) throws SQLException {
        this.connection = connection;
    }


    public List<BoardComment> view(int boardSeq) throws SQLException {
        List<BoardComment> commentList = new ArrayList<>();

        var preparedStatementComment = connection.prepareStatement(COMMENT_QUERY);
        preparedStatementComment.setInt(1, boardSeq);
        var resultSetComment = preparedStatementComment.executeQuery();
        while (resultSetComment.next()) {
            BoardComment boardComment = new BoardComment();
            boardComment.setSeq(resultSetComment.getInt(1));
            boardComment.setCustomerSeq(resultSetComment.getInt(2));
            boardComment.setContent(resultSetComment.getString(3));
            boardComment.setBoardCommentSeq(resultSetComment.getInt(4));
            boardComment.setCustomerId(resultSetComment.getString(5));
            boardComment.setBoardSeq(resultSetComment.getInt(6));
            commentList.add(boardComment);
        }
        return commentList;
    }

    public void insert(String content, int commentSeq, int customerSeq, int boardSeq) throws SQLException {
        var preparedStatement = connection.prepareStatement(COMMENT_INSERT_QUERY);
        preparedStatement.setString(1, content);
        preparedStatement.setInt(2, commentSeq);
        preparedStatement.setInt(3, customerSeq);
        preparedStatement.setInt(4, boardSeq);
        preparedStatement.execute();
    }


    public List<BoardComment> viewReply(String customerId, int commentSeq, String content, int boardCommentSeq, int customerSeq, int boardSeq) throws SQLException {
        List<BoardComment> replyList = new ArrayList<>();
        var preparedStatement = connection.prepareStatement(COMMENT_REPLY_QUERY);
        preparedStatement.setInt(1, boardSeq);
        preparedStatement.setInt(2, boardCommentSeq);
        var resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            BoardComment boardComment = new BoardComment();
            boardComment.setCustomerId(resultSet.getString(1));
            boardComment.setContent(resultSet.getString(2));
            replyList.add(boardComment);
        }
        return replyList;
    }
}
