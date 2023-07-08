package kr.co.mz.tutorial.servlet.loginHandler;

import kr.co.mz.tutorial.servletListener.HikariCPInitializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginHandler extends HttpServlet {

    private static final String QUERY = "select 1 from customer where customer_id = ? and password = ?";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection connection = HikariCPInitializer.getConnection();
            var preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            int result = 0;
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }

            if (result == 1) {
                response.sendRedirect("/board");
            } else {
                response.sendRedirect("/");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
