package kr.co.mz.tutorial.jdbc.servlet;

import kr.co.mz.tutorial.DatabaseAccessException;
import kr.co.mz.tutorial.InputValidationException;
import kr.co.mz.tutorial.NoSuchCustomerFoundException;
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
import java.sql.SQLException;
import java.util.Optional;

import static kr.co.mz.tutorial.Constants.CUSTOMER_IN_SESSION;
import static kr.co.mz.tutorial.Constants.DATASOURCE_CONTEXT_KEY;


public class LoginServlet extends HttpServlet { // MyServlet 클래스는 HttpServlet 클래스를 상속합니다. 이는 Java Servlet API의 일부이며, 서블릿을 작성하기 위해 필요한 기능을 제공합니다. //HttpServlet이 없으면 web.xml에서 url 인식 못하나?


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) { // Http Get 요청을 처리하기위해 오버라이드된것. 이 메서드는 HttpServletRequest 및 HttpServletResponse 매개변수를 받습니다. 요청 객체는 클라이언트로부터의 요청에 대한 정보를 제공하며, 응답 객체는 서버에서 클라이언트로의 응답을 작성하고 전송하는 데 사용됩니다. // Tomcat에서는 Request, Response 구현객체가 전달됨.

        response.setContentType("text/html;charset=UTF-8"); //응답의 컨텐츠 타입을 HTML로 설정하는 코드입니다. 이는 클라이언트에게 서버가 HTML 문서를 반환한다는 것을 알려줍니다.
        PrintWriter out = null; // PrintWriter 객체를 가져옵니다. 이를 통해 응답 스트림에 데이터를 쓸 수 있습니다.
        try {
            out = response.getWriter();
        } catch (IOException ioe) {
            throw new RuntimeException();
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login Form</title>");
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
        out.println(".signup-link {");
        out.println("    text-align: center;");
        out.println("    margin-top: 15px;");
        out.println("}");
        out.println(".signup-link a {");
        out.println("    color: #0099ff;");
        out.println("}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container\">");
        out.println("<form action=\"/\" method=\"post\">");
        out.println("<h2>Login Form</h2>");
        out.println("<div class=\"input-group\">");
        out.println("<label for=\"username\">Username</label>");
        out.println("<input type=\"text\" id=\"username\" name=\"username\" required>");
        out.println("</div>");
        out.println("<div class=\"input-group\">");
        out.println("<label for=\"password\">Password</label>");
        out.println("<input type=\"password\" id=\"password\" name=\"password\" required>");
        out.println("</div>");
        out.println("<div class=\"input-group\">");
        out.println("<button type=\"submit\" class=\"btn\">Login</button>");
        out.println("</div>");
        out.println("</form>");
        out.println("<div class=\"signup-link\">");
        out.println("Don't have an account? <a href=\"join\">Sign up</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    private static void validateInputParameter(String username, String password) {
        if (username == null || username.length() < 4) {
            throw new InputValidationException("사용자명은 네 글자 이상이어야 합니다.");
        }
        if (password == null || password.length() < 4) {
            throw new InputValidationException("비밀번호은 네 글자 이상이어야 합니다.");
        }
    }

    private Customer findCustomer(String username, String password) {
        var datasource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
        try (var connection = datasource.getConnection();) {
            var customerDao = new CustomerDao(connection);
            Optional<Customer> optionalCustomer = customerDao.findByUsername(username);
            return optionalCustomer.filter(customer2 -> password.equals(customer2.getPassword())).orElseThrow(() -> new NoSuchCustomerFoundException(username));
        } catch (SQLException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        validateInputParameter(username, password);

        var customer = findCustomer(username, password);

        request.getSession().setAttribute(CUSTOMER_IN_SESSION, customer);
        request.setAttribute("message", "성공적으로 로그인 되었습니다.");
        request.setAttribute("redirectUrl", "http://localhost:8036/posts");
        try {
            request.getRequestDispatcher("/WEB-INF/jsp/redirect.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ResourceAndWriteException("전달된 경로의 리소스 문제 또는 리소스를 출력하는 도중에 오류가 발생했습니다." + e.getMessage(), e);
        }


    }
}
