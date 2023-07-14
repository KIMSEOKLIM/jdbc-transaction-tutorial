package kr.co.mz.tutorial.servlet;

import kr.co.mz.tutorial.DatabaseAccessException;
import kr.co.mz.tutorial.DuplicatedUserNameException;
import kr.co.mz.tutorial.InputValidationException;
import kr.co.mz.tutorial.ResourceAndWriteException;
import kr.co.mz.tutorial.jdbc.dao.CustomerDao;
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

import static kr.co.mz.tutorial.Constants.DATASOURCE_CONTEXT_KEY;

public class JoinServlet extends HttpServlet { // MyServlet 클래스는 HttpServlet 클래스를 상속합니다. 이는 Java Servlet API의 일부이며, 서블릿을 작성하기 위해 필요한 기능을 제공합니다.

    private static void validateInputParameter(String username, String password) {
        if (username == null || username.length() < 4) {
            throw new InputValidationException("사용자명은 네 글자 이상이어야 합니다.");
        }
        if (password == null || password.length() < 4) {
            throw new InputValidationException("비밀번호은 네 글자 이상이어야 합니다.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        var customer = new Customer(request.getParameter("id"), request.getParameter("password"), request.getParameter("name"), request.getParameter("address"), request.getParameter("email"));
        validateInputParameter(request.getParameter("id"), request.getParameter("password"));

        try {
            findCustomer(request.getParameter("id"));
            joinCustomer(customer);

        } catch (SQLException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);
        }

        request.setAttribute("message", "회원가입이 완료되었습니다.");
        request.setAttribute("redirectUrl", "http://localhost:8036");
        try {
            request.getRequestDispatcher("/WEB-INF/jsp/redirect.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ResourceAndWriteException("전달된 경로의 리소스 문제 또는 리소스를 출력하는 도중에 오류가 발생했습니다." + e.getMessage(), e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html"); //응답의 컨텐츠 타입을 HTML로 설정하는 코드입니다. 이는 클라이언트에게 서버가 HTML 문서를 반환한다는 것을 알려줍니다.
        PrintWriter out = null; // PrintWriter 객체를 가져옵니다. 이를 통해 응답 스트림에 데이터를 쓸 수 있습니다.
        try {
            out = response.getWriter();
        } catch (IOException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Join Form</title>");
        out.println("<style>");
        out.println("body {");
        out.println("    font-family: Arial, sans-serif;");
        out.println("    background-color: #f0f0f0;");
        out.println("}");
        out.println(".container {");
        out.println("    max-width: 360px;");
        out.println("    margin: 0 auto;");
        out.println("    padding: 20px;");
        out.println("    background-color: #fff;");
        out.println("    border: 1px solid #ccc;");
        out.println("    border-radius: 4px;");
        out.println("}");
        out.println("h2 {");
        out.println("    text-align: center;");
        out.println("    margin-bottom: 20px;");
        out.println("}");
        out.println(".input-group {");
        out.println("    margin-bottom: 15px;");
        out.println("}");
        out.println(".input-group label {");
        out.println("    display: block;");
        out.println("    font-weight: bold;");
        out.println("    margin-bottom: 5px;");
        out.println("}");
        out.println(".input-group input {");
        out.println("    width: 100%;");
        out.println("    padding: 10px;");
        out.println("    font-size: 16px;");
        out.println("    border-radius: 4px;");
        out.println("    border: 1px solid #ccc;");
        out.println("}");
        out.println(".btn {");
        out.println("    display: inline-block;");
        out.println("    padding: 10px 20px;");
        out.println("    background-color: #4caf50;");
        out.println("    color: #fff;");
        out.println("    font-size: 16px;");
        out.println("    border: none;");
        out.println("    border-radius: 4px;");
        out.println("    cursor: pointer;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container\">");
        out.println("<h2>Join Form</h2>");
        out.println("<form action=\"/join\" method=\"post\">");
        out.println("<div class=\"input-group\">");
        out.println("<label for=\"id\">ID:</label>");
        out.println("<input type=\"text\" id=\"id\" name=\"id\" required>");
        out.println("</div>");
        out.println("<div class=\"input-group\">");
        out.println("<label for=\"password\">Password:</label>");
        out.println("<input type=\"password\" id=\"password\" name=\"password\" required>");
        out.println("</div>");
        out.println("<div class=\"input-group\">");
        out.println("<label for=\"name\">Name:</label>");
        out.println("<input type=\"text\" id=\"name\" name=\"name\" required>");
        out.println("</div>");
        out.println("<div class=\"input-group\">");
        out.println("<label for=\"address\">Address:</label>");
        out.println("<input type=\"text\" id=\"address\" name=\"address\" required>");
        out.println("</div>");
        out.println("<div class=\"input-group\">");
        out.println("<label for=\"email\">Email:</label>");
        out.println("<input type=\"email\" id=\"email\" name=\"email\" required>");
        out.println("</div>");
        out.println("<div class=\"input-group\">");
        out.println("<input type=\"submit\" value=\"Submit\" class=\"btn\">");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }

    private void joinCustomer(Customer customer) throws SQLException {
        var dataSource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
        Connection connection = null;

        connection = dataSource.getConnection();
        CustomerDao customerDao = new CustomerDao(connection, customer);
        customerDao.join();

    }

    private void findCustomer(String username) throws SQLException {
        var dataSource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
        int check;
        Connection connection = dataSource.getConnection();
        CustomerDao customerDao = new CustomerDao(connection);
        check = customerDao.duplicateCheck(username);

        if (check == 1) {
            throw new DuplicatedUserNameException("이미 존재하는 사용자 입니다.");
        }

    }
}
