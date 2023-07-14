package kr.co.mz.tutorial.servlet;

import kr.co.mz.tutorial.NetworkAndResponseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteBoardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
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
        out.println("<style>");
        // CSS 스타일 코드 추가
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"container\">");
        out.println("<form action=\"/posts\" method=\"POST\">");
        out.println("<div class=\"form-group\">");
        out.println("<label for=\"title\">제목:</label>");
        out.println("<input type=\"text\" id=\"title\" name=\"title\">");
        out.println("</div>");
        out.println("<div class=\"form-group\">");
        out.println("<label for=\"content\">내용:</label>");
        out.println("<textarea id=\"content\" name=\"content\"></textarea>");
        out.println("</div>");
        out.println("<div class=\"form-group\">");
        out.println("<input type=\"submit\" value=\"등록\">");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
