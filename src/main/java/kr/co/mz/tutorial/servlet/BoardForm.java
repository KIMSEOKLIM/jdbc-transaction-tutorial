package kr.co.mz.tutorial.servlet;

import kr.co.mz.tutorial.jdbc.dao.BoardDao;
import kr.co.mz.tutorial.jdbc.model.Board;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardForm extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Board> boardData;

        var datasource = (DataSource) getServletContext().getAttribute("dataSource");
        try {
            BoardDao boardDao = new BoardDao(datasource);
            boardData = boardDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setCharacterEncoding("UTF-8");
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

        for (Board board : boardData) {
            out.println("<tr>");
            out.println("<td>" + board.getCustomerId() + "</td>");
            out.println("<td><a href=\"/posts/?id=" + board.getSeq() + "\">" + board.getTitle() + "</a></td>");
            out.println("<td>" + board.getEvaluationTotal() + "</td>");
            out.println("<td>" + board.getCreatedTime() + "</td>");
            out.println("<td>" + board.getModifiedTime() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<br>");
        out.println("<a href=\"/posts/write\" class=\"button\">글쓰기</a>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        int customerSeq = (int) getServletContext().getAttribute("seq");
        String title = requset.getParameter("title");
        String content = requset.getParameter("content");

        ArrayList<Board> boardData;
        var dataSource = (DataSource) getServletContext().getAttribute("dataSource");

        BoardDao boardDao = null;
        try {
            boardDao = new BoardDao(dataSource);
            boardDao.insert(title, content, customerSeq);
            boardData = boardDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setCharacterEncoding("UTF-8");
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

        for (Board board : boardData) {
            out.println("<tr>");
            out.println("<td>" + board.getCustomerId() + "</td>");
            out.println("<td><a href=\"/posts/?id=" + board.getSeq() + "\">" + board.getTitle() + "</a></td>");
            out.println("<td>" + board.getEvaluationTotal() + "</td>");
            out.println("<td>" + board.getCreatedTime() + "</td>");
            out.println("<td>" + board.getModifiedTime() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<br>");
        out.println("<a href=\"/posts/write\" class=\"button\">글쓰기</a>");
        out.println("</body>");
        out.println("</html>");
    }
}

