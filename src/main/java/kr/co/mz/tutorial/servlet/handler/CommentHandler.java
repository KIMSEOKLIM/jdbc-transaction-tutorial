package kr.co.mz.tutorial.servlet.handler;

import kr.co.mz.tutorial.jdbc.dao.BoardCommentDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class CommentHandler extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        int boardSeq = Integer.parseInt(request.getParameter("boardSeq"));
        int customerSeq = (int) getServletContext().getAttribute("seq");
        String commentContent = request.getParameter("commentContent");
        var datasource = (DataSource) getServletContext().getAttribute("dataSource");

        BoardCommentDao boardCommentDao = null;
        try {
            boardCommentDao = new BoardCommentDao(datasource);
            boardCommentDao.insert(commentContent, customerSeq, boardSeq);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("/posts/?id=" + boardSeq);

    }
}
