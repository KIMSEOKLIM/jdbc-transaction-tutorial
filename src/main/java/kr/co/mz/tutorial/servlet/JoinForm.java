package kr.co.mz.tutorial.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JoinForm extends HttpServlet { // MyServlet 클래스는 HttpServlet 클래스를 상속합니다. 이는 Java Servlet API의 일부이며, 서블릿을 작성하기 위해 필요한 기능을 제공합니다.


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html"); //응답의 컨텐츠 타입을 HTML로 설정하는 코드입니다. 이는 클라이언트에게 서버가 HTML 문서를 반환한다는 것을 알려줍니다.
        PrintWriter out = response.getWriter(); // PrintWriter 객체를 가져옵니다. 이를 통해 응답 스트림에 데이터를 쓸 수 있습니다.

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
        out.println("<form action=\"/\" method=\"post\">");
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
}
