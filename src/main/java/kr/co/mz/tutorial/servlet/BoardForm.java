package kr.co.mz.tutorial.servlet;

import kr.co.mz.tutorial.DatabaseAccessException;
import kr.co.mz.tutorial.InputValidationException;
import kr.co.mz.tutorial.NetworkAndResponseException;
import kr.co.mz.tutorial.jdbc.dao.BoardDao;
import kr.co.mz.tutorial.jdbc.model.Board;
import kr.co.mz.tutorial.jdbc.model.Customer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static kr.co.mz.tutorial.Constants.CUSTOMER_IN_SESSION;
import static kr.co.mz.tutorial.Constants.DATASOURCE_CONTEXT_KEY;


public class BoardForm extends HttpServlet {


    private ArrayList<Board> viewAll() {
        var datasource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
        try (Connection connection = datasource.getConnection();) {
            BoardDao boardDao = new BoardDao(connection);
            return boardDao.findAll();
        } catch (SQLException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);
        }
    }

    @Override
    protected void doGet(HttpServletRequest requset, HttpServletResponse response) {
        ArrayList<Board> boardData = viewAll();
        Customer customer = (Customer) requset.getSession().getAttribute(CUSTOMER_IN_SESSION);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException ioe) {
            throw new NetworkAndResponseException("1. 클라이언트와의 네트워크 연결이 끊김, 2. 응답이 이미 커밋됨, 3. 응답 버퍼가 이미 비워짐" + ioe.getMessage(), ioe);
        }
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>게시판 목록" + "</title>");
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
        out.println("<h2>게시판 목록 [접속자 : " + customer.getCustomerId() + "]" + "[포인트 : " + customer.getPoint() + "]</h2>");
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


    private static void validateInputParameter(String title, String content) {
        if (title == null || title.length() < 5) {
            throw new InputValidationException("제목은 다섯 글자 이상이어야 합니다.");
        }
        if (content == null || content.length() < 10) {
            throw new InputValidationException("내용은 열 글자 이상이어야 합니다.");
        }
    }

    private ArrayList<Board> writeAndViewAll(String title, String content, int customerSeq) {
        try {
            var dataSource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
            Connection connection = dataSource.getConnection();
            BoardDao boardDao = new BoardDao(connection);
            boardDao.create(title, content, customerSeq);
            return boardDao.findAll();
        } catch (SQLException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);
        }
    }

    @Override
    protected void doPost(HttpServletRequest requset, HttpServletResponse response) {
        var customer = (Customer) requset.getSession().getAttribute(CUSTOMER_IN_SESSION);
        int customerSeq = customer.getSeq();
        String title = requset.getParameter("title");
        String content = requset.getParameter("content");
        ArrayList<Board> boardData;

        validateInputParameter(title, content);
        boardData = writeAndViewAll(title, content, customerSeq);


        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException ioe) {
            throw new NetworkAndResponseException("1. 클라이언트와의 네트워크 연결이 끊김, 2. 응답이 이미 커밋됨, 3. 응답 버퍼가 이미 비워짐" + ioe.getMessage(), ioe);
        }
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

