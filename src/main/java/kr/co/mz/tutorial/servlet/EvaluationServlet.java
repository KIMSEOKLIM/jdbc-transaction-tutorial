package kr.co.mz.tutorial.servlet;

import kr.co.mz.tutorial.DatabaseAccessException;
import kr.co.mz.tutorial.EvaluationDuplicationException;
import kr.co.mz.tutorial.NetworkAndResponseException;
import kr.co.mz.tutorial.jdbc.dao.BoardEvaluationDao;
import kr.co.mz.tutorial.jdbc.dao.CustomerDao;
import kr.co.mz.tutorial.jdbc.model.Customer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static kr.co.mz.tutorial.Constants.CUSTOMER_IN_SESSION;
import static kr.co.mz.tutorial.Constants.DATASOURCE_CONTEXT_KEY;


public class EvaluationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest requset, HttpServletResponse response) {
        var customer = (Customer) requset.getSession().getAttribute(CUSTOMER_IN_SESSION);
        var customerSeq = customer.getSeq();
        int boardSeq = Integer.parseInt(requset.getParameter("id"));
        var dataSource = (DataSource) getServletContext().getAttribute(DATASOURCE_CONTEXT_KEY);
        try (var connection = dataSource.getConnection()) {
            evaluateAndPlusPoint(connection, customerSeq, boardSeq);
        } catch (SQLException sqle) {
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);
        }


        try {
            response.sendRedirect("/posts/?id=" + boardSeq);
        } catch (IOException ioe) {
            throw new NetworkAndResponseException("1. 클라이언트와의 네트워크 연결이 끊김, 2. 응답이 이미 커밋됨, 3. 응답 버퍼가 이미 비워짐" + ioe.getMessage(), ioe);
        }

    }

    private void evaluateAndPlusPoint(Connection connection, int customerSeq, int boardSeq) {
        BoardEvaluationDao boardEvaluationDao;
        CustomerDao customerDao;
        int result;
        try {
            connection.setAutoCommit(false);
            boardEvaluationDao = new BoardEvaluationDao(connection);
            result = boardEvaluationDao.duplicationCheck(customerSeq, boardSeq);

            if (result > 0) {
                throw new EvaluationDuplicationException("이미 회원님은 이글에 추천을 한번 하셨습니다.");
            }

            if (result == 0) {

                boardEvaluationDao.insert(customerSeq, boardSeq);
                boardEvaluationDao.update(boardSeq);
                customerDao = new CustomerDao(connection);
                customerDao.plusPoint(customerSeq);
                //추천하고 posts 로 가면 포인트가 올라간게 적용되서 보여져야함.
            }
            connection.commit();
        } catch (SQLException sqle) {
            try {
                connection.rollback();
            } catch (SQLException sqle2) {
                throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle2);
            }
            throw new DatabaseAccessException("데이터베이스 관련 처리에 오류가 발생하였습니다:" + sqle.getMessage(), sqle);
        }
    }
}
