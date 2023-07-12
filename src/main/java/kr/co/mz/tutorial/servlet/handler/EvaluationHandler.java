package kr.co.mz.tutorial.servlet.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EvaluationHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
        int customerSeq = (int) getServletContext().getAttribute("seq");
        int boardSeq = Integer.parseInt(requset.getParameter("id"));


    }
}
