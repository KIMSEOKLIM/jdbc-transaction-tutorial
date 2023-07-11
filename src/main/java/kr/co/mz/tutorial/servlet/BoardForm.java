package kr.co.mz.tutorial.servlet;

import kr.co.mz.tutorial.jdbc.model.Board;
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

public class BoardForm extends HttpServlet {

    private static final String SELECT_BOARD_ALL = "select b.seq, c.customer_id, b.title, b.evaluation_total, b.created_time, b.modified_time from board b join customer c on b.customer_seq = c.seq";

    @Override
    protected void doGet(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {

        int customerSeq = Integer.parseInt(requset.getParameter("seq"));
        ArrayList<Board> boardData = new ArrayList<>();


        try (Connection connection = HikariCPInitializer.getConnection()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setCharacterEncoding("UTF-8");
        // 응답 데이터 생성
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>게시판 목록</title>");
        out.println("<style>");
        out.println("table {");
        out.println("    width: 100%;");
        out.println("    border-collapse: collapse;");
        out.println("}");
        out.println("th, td {");
        out.println("    padding: 8px;");
        out.println("    text-align: left;");
        out.println("    border-bottom: 1px solid #ddd;");
        out.println("}");
        out.println("th {");
        out.println("    background-color: #f2f2f2;");
        out.println("}");
        out.println(".button {");
        out.println("    display: inline-block;");
        out.println("    padding: 8px 12px;");
        out.println("    background-color: #4caf50;");
        out.println("    color: white;");
        out.println("    text-decoration: none;");
        out.println("    border: none;");
        out.println("    border-radius: 4px;");
        out.println("    cursor: pointer;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>게시판 목록</h2>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>글쓴이</th>");
        out.println("<th>제목</th>");
        out.println("<th>추천수</th>");
        out.println("<th>생성시간</th>");
        out.println("<th>수정시간</th>");
        out.println("</tr>");

        // 게시글 데이터를 테이블로 출력
        for (Board board : boardData) {
            out.println("<tr>");
            out.println("<td>" + board.getCustomerId() + "</td>");
            out.println("<td><a href=\"/content?boardSeq=" + board.getSeq() + "&customerSeq=" + customerSeq + "\">" + board.getTitle() + "</a></td>");
            out.println("<td>" + board.getEvaluationTotal() + "</td>");
            out.println("<td>" + board.getCreatedTime() + "</td>");
            out.println("<td>" + board.getModifiedTime() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<br>");
        out.println("<a href=\"/write\" class=\"button\">글쓰기</a>");
        out.println("</body>");
        out.println("</html>");
    }
}

