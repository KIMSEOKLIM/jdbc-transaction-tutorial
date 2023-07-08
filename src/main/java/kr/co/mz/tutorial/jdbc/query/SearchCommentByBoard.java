package kr.co.mz.tutorial.jdbc.query;

import kr.co.mz.tutorial.jdbc.db.HikariPoolFactory;
import kr.co.mz.tutorial.jdbc.model.BoardComment;

import java.io.IOException;
import java.sql.SQLException;

public class SearchCommentByBoard {

    private static final String QUERY = """
            select c.content from board b inner join board_comment c on b.seq = c.board_seq
                                   """;

    public static void main(String[] args) throws IOException, SQLException {
        var datasource = HikariPoolFactory.createHikariDataSource();
        var connection = datasource.getConnection();
        var preparedStatement = connection.prepareStatement(QUERY);
        var resultSet = preparedStatement.executeQuery();
        BoardComment boardComment = new BoardComment();
        while (resultSet.next()) {
            boardComment.setContent(resultSet.getString(1));
            System.out.println(boardComment.getContent());
        }


    }
}
