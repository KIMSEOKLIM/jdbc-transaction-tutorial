package kr.co.mz.tutorial.servlet;

import kr.co.mz.tutorial.DatabaseAccessException;
import kr.co.mz.tutorial.InputValidationException;
import kr.co.mz.tutorial.NetworkAndResponseException;
import kr.co.mz.tutorial.ResourceAndWriteException;
import kr.co.mz.tutorial.jdbc.dao.BoardCommentDao;
import kr.co.mz.tutorial.jdbc.dao.BoardDao;
import kr.co.mz.tutorial.jdbc.model.Board;
import kr.co.mz.tutorial.jdbc.model.BoardComment;
import kr.co.mz.tutorial.jdbc.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static kr.co.mz.tutorial.Constants.CUSTOMER_IN_SESSION;
import static kr.co.mz.tutorial.Constants.DATASOURCE_CONTEXT_KEY;


public class ContentForm extends HttpServlet {


    private List<BoardComment> viewAll(int boardSeq) {
        var dataSource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
        try (var connection = dataSource.getConnection();) {
            BoardCommentDao boardCommentDao = new BoardCommentDao(connection);
            return boardCommentDao.view(boardSeq);
        } catch (SQLException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);

        }
    }

    private List<BoardComment> viewReplyAll(String customerId, int commentSeq, String content, int boardCommentSeq, int customerSeq, int boardSeq) {
        if (boardCommentSeq != 0) {
            var dataSource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
            try (var connection = dataSource.getConnection();) {
                BoardCommentDao boardCommentDao = new BoardCommentDao(connection);
                return boardCommentDao.viewReply(customerId, commentSeq, content, boardCommentSeq, customerSeq, boardSeq);
            } catch (SQLException sqle) {
                throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);
            }
        } else
            return null;
    }

    private Board findOne(int boardSeq) {
        var dataSource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
        try (var connection = dataSource.getConnection();) {
            BoardDao boardDao = new BoardDao(connection);
            return boardDao.findOne(boardSeq);
        } catch (SQLException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        int boardSeq = Integer.parseInt(request.getParameter("id"));
        var customer = (Customer) request.getSession().getAttribute(CUSTOMER_IN_SESSION);
        int customerSeq = customer.getSeq();

        Board board = findOne(boardSeq);
        List<BoardComment> commentList = viewAll(boardSeq);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException ioe) {
            throw new NetworkAndResponseException("1. 클라이언트와의 네트워크 연결이 끊김, 2. 응답이 이미 커밋됨, 3. 응답 버퍼가 이미 비워짐" + ioe.getMessage(), ioe);
        }
        out.println("<!DOCTYPE html>");
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
        out.println(".comment-wrapper {");
        out.println("    margin-bottom: 10px;");
        out.println("    padding: 10px;");
        out.println("    border: 1px solid #cccccc;");
        out.println("    background-color: #ffffff;");
        out.println("}");
        out.println(".comment {");
        out.println("    margin-bottom: 10px;");
        out.println("    padding: 10px;");
        out.println("    border: 1px solid #cccccc;");
        out.println("    background-color: #ffffff;");
        out.println("}");
        out.println(".reply {");
        out.println("    margin-bottom: 5px;");
        out.println("    padding: 10px;");
        out.println("    border: 1px solid #cccccc;");
        out.println("    background-color: #f9f9f9;");
        out.println("}");
        out.println(".add-reply-form {");
        out.println("    margin-top: 20px;");
        out.println("}");
        out.println(".add-reply-form textarea {");
        out.println("    width: 100%;");
        out.println("    padding: 8px;");
        out.println("    margin-bottom: 10px;");
        out.println("    border: 1px solid #cccccc;");
        out.println("    border-radius: 4px;");
        out.println("}");
        out.println(".add-reply-form button {");
        out.println("    background-color: #4caf50;");
        out.println("    color: white;");
        out.println("    padding: 8px 12px;");
        out.println("    border: none;");
        out.println("    border-radius: 4px;");
        out.println("    cursor: pointer;");
        out.println("}");
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
        out.println("<td><a href=\"/posts\">뒤로가기</a></td>");
        out.println("<h2>글쓴이: " + board.getCustomerId() + "</h2>");
        out.println("<p>제목: " + board.getTitle() + "</p>");
        out.println("<p>내용: " + board.getContent() + "</p>");
        out.println("<td><a href=\"/evaluation?id=" + boardSeq + "\">글추천</a></td>");
        out.println("<h3>댓글</h3>");
        for (BoardComment comment : commentList) {
            out.println("<div class=\"comment-wrapper\">");
            out.println("<div class=\"comment\">");
            out.println("<p><strong>작성자:</strong> " + comment.getCustomerId() + "</p>");
            out.println("<p><strong>내용:</strong> " + comment.getContent() + "</p>");

            List<BoardComment> replyList = viewReplyAll(comment.getCustomerId(), comment.getSeq(), comment.getContent(), comment.getBoardCommentSeq(), comment.getCustomerSeq(), comment.getBoardSeq());
            if (replyList != null) {
                for (BoardComment reply : replyList) {
                    out.println("<div class=\"reply\">");
                    out.println("<p><strong>작성자:</strong> " + reply.getCustomerId() + "</p>");
                    out.println("<p><strong>내용:</strong> " + reply.getContent() + "</p>");
                    out.println("</div>");
                }
            }

            out.println("<div class=\"add-reply-form\">");
            out.println("<h4>대댓글 달기</h4>");
            out.println("<form action=\"/posts/?id=" + board.getSeq() + "\" method=\"post\">");
            out.println("<input type=\"hidden\" name=\"boardSeq\" value=\"" + boardSeq + "\">");
            out.println("<input type=\"hidden\" name=\"customerSeq\" value=\"" + customerSeq + "\">");
            out.println("<input type=\"hidden\" name=\"commentSeq\" value=\"" + comment.getSeq() + "\">");
            out.println("<textarea name=\"commentContent\" placeholder=\"대댓글 내용\"></textarea>");
            out.println("<button type=\"submit\">대댓글 추가</button>");
            out.println("</form>");
            out.println("</div>");

            out.println("</div>");
            out.println("<hr>");
            out.println("</div>");
        }
        out.println("<div class=\"add-comment-form\">");
        out.println("<h3>댓글 추가하기</h3>");
        out.println("<form action=\"/posts/?id=" + board.getSeq() + "\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"boardSeq\" value=\"" + boardSeq + "\">");
        out.println("<input type=\"hidden\" name=\"customerSeq\" value=\"" + customerSeq + "\">");
        out.println("<input type=\"hidden\" name=\"commentSeq\" value=\"" + 0 + "\">");
        out.println("<textarea name=\"commentContent\" placeholder=\"댓글 내용\"></textarea>");
        out.println("<button type=\"submit\">댓글 추가</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }

    private void insertComment(String commentContent, int customerSeq, int boardSeq, int commentSeq) {
        var datasource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
        try (Connection connection = datasource.getConnection();) {
            BoardCommentDao boardCommentDao = new BoardCommentDao(connection);
            boardCommentDao.insert(commentContent, commentSeq, customerSeq, boardSeq);
        } catch (SQLException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        int commentSeq = Integer.parseInt(request.getParameter("commentSeq"));
        int boardSeq = Integer.parseInt(request.getParameter("boardSeq"));
        var customer = (Customer) request.getSession().getAttribute(CUSTOMER_IN_SESSION);
        int customerSeq = customer.getSeq();
        String commentContent = request.getParameter("commentContent");
//  여기 만약 댓글이 달렸는데 대댓글이다?? --> contentformseq에 그 pk값 seq를 같이 insert
        validateInputParameter(commentContent);

        insertComment(commentContent, customerSeq, boardSeq, commentSeq);
        request.setAttribute("message", "댓글이 성공적으로 등록되었습니다.");
        request.setAttribute("redirectUrl", "http://localhost:8036/posts/?id=" + boardSeq);
        try {
            request.getRequestDispatcher("/WEB-INF/jsp/redirect.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ResourceAndWriteException("전달된 경로의 리소스 문제 또는 리소스를 출력하는 도중에 오류가 발생했습니다." + e.getMessage(), e);
        }


    }

    private static void validateInputParameter(String commentContent) {
        if (commentContent == null || commentContent.equals("")) {
            throw new InputValidationException("댓글을 입력해주세요!");
        }
    }
}
