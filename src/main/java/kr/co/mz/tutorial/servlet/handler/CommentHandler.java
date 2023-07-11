package kr.co.mz.tutorial.servlet.handler;

import kr.co.mz.tutorial.servletListener.HikariCPInitializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CommentHandler extends HttpServlet {

    private static final String QUERY = "select 1, seq from customer where customer_id = ? and password = ?";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection connection = null; // Connection 변수를 선언하고 초기화합니다.

        try {
            connection = HikariCPInitializer.getConnection();

            var preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            int result = 0;
            int seq = 0;
            if (resultSet.next()) {
                result = resultSet.getInt(1);
                seq = resultSet.getInt(2);
            }

            if (result == 1) {
                response.sendRedirect("/board?seq=" + seq);
            } else {
                response.sendRedirect("/");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // finally 블록에서 Connection 객체를 닫습니다.
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Connection을 닫는 중 오류가 발생했습니다: " + e.getMessage());
                }
            }
        }
    }
}
