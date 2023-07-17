package kr.co.mz.tutorial.jdbc.servlet;

import kr.co.mz.tutorial.DatabaseAccessException;
import kr.co.mz.tutorial.jdbc.model.Customer;
import kr.co.mz.tutorial.jdbc.service.BoardEvaluationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kr.co.mz.tutorial.Constants.CUSTOMER_IN_SESSION;


public class EvaluationServlet extends DataSourceServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        var customer = (Customer) request.getSession().getAttribute(CUSTOMER_IN_SESSION);
        var customerSeq = customer.getSeq();
        int boardSeq = Integer.parseInt(request.getParameter("id"));

        try (var connection = dataSource.getConnection()) {
            new BoardEvaluationService(connection).evaluate(customerSeq, boardSeq);
        } catch (Exception sqlE) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqlE.getMessage(), sqlE);
        }

        sendRedirect(response, "/posts?id=" + boardSeq);
    }

}
