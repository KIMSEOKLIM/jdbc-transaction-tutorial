package kr.co.mz.tutorial.servlet;

import kr.co.mz.tutorial.jdbc.model.Board;
import kr.co.mz.tutorial.jdbc.model.BoardComment;
import kr.co.mz.tutorial.servletListener.HikariCPInitializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContentForm extends HttpServlet {

    private static final String CONTENT_QUERY = "SELECT c.customer_id, b.title, b.content FROM board b JOIN customer c ON b.customer_seq = c.seq WHERE b.seq = ?";
    private static final String COMMENT_QUERY = "SELECT b.customer_seq, b.content, c.customer_id FROM board_comment b JOIN customer c ON b.customer_seq = c.seq WHERE board_seq = ?";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int boardSeq = Integer.parseInt(request.getParameter("boardSeq"));
        int customerSeq = Integer.parseInt(request.getParameter("customerSeq"));
        Board board = new Board();
        List<BoardComment> commentList = new ArrayList<>();

        try (Connection connection = HikariCPInitializer.getConnection()) {
            var preparedStatement_content = connection.prepareStatement(CONTENT_QUERY);
            preparedStatement_content.setInt(1, boardSeq);
            var resultSet = preparedStatement_content.executeQuery();
            while (resultSet.next()) {
                board.setCustomerId(resultSet.getString(1));
                board.setTitle(resultSet.getString(2));
                board.setContent(resultSet.getString(3));
            }

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>글 상세 페이지</title>");
        out.println("<style>");
        out.println("body {");
        out.println("    font-family: Arial, sans-serif;");
        out.println("    background-color: #f2f2f2;");
        out.println("    padding: 20px;");
        out.println("}");
        out.println("h1, h2, h3 {");
        out.println("    color: #333333;");
        out.println("}");
        out.println("h1 {");
        out.println("    font-size: 24px;");
        out.println("}");
        out.println("h2 {");
        out.println("    font-size: 20px;");
        out.println("}");
        out.println("h3 {");
        out.println("    font-size: 18px;");
        out.println("}");
        out.println("p {");
        out.println("    color: #666666;");
        out.println("    margin-bottom: 10px;");
        out.println("}");
        out.println(".comment {");
        out.println("    margin-bottom: 10px;");
        out.println("    padding: 10px;");
        out.println("    border: 1px solid #cccccc;");
        out.println("    background-color: #ffffff;");
        out.println("}");
        out.println(".add-comment-form {");
        out.println("    margin-top: 20px;");
        out.println("}");
        out.println(".add-comment-form input[type=text],");
        out.println(".add-comment-form textarea {");
        out.println("    width: 100%;");
        out.println("    padding: 8px;");
        out.println("    margin-bottom: 10px;");
        out.println("    border: 1px solid #cccccc;");
        out.println("    border-radius: 4px;");
        out.println("}");
        out.println(".add-comment-form button {");
        out.println("    background-color: #4caf50;");
        out.println("    color: white;");
        out.println("    padding: 8px 12px;");
        out.println("    border: none;");
        out.println("    border-radius: 4px;");
        out.println("    cursor: pointer;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>글 상세 정보</h1>");
        out.println("<h2>글쓴이: " + board.getCustomerId() + "</h2>");
        out.println("<p>제목: " + board.getTitle() + "</p>");
        out.println("<p>내용: " + board.getContent() + "</p>");
        out.println("<h3>댓글</h3>");
        for (BoardComment comment : commentList) {
            out.println("<div class=\"comment\">");
            out.println("<p><strong>작성자:</strong> " + comment.getCustomerId() + "</p>");
            out.println("<p><strong>내용:</strong> " + comment.getContent() + "</p>");
            out.println("</div>");
            out.println("<hr>");
        }
        out.println("<div class=\"add-comment-form\">");
        out.println("<h3>댓글 추가하기</h3>");
        out.println("<form action=\"/addComment\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"boardSeq\" value=\"" + boardSeq + "\">");
        out.println("<input type=\"hidden\" name=\"customerSeq\" value=\"" + customerSeq + "\">");
        out.println("<textarea name=\"commentContent\" placeholder=\"댓글 내용\" required></textarea>");
        out.println("<button type=\"submit\">댓글 추가</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
