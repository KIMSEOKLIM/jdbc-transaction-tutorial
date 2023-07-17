package kr.co.mz.tutorial.jdbc.service;

import kr.co.mz.tutorial.DatabaseAccessException;
import kr.co.mz.tutorial.EvaluationDuplicationException;
import kr.co.mz.tutorial.jdbc.dao.BoardEvaluationDao;
import kr.co.mz.tutorial.jdbc.dao.CustomerDao;

import java.sql.Connection;
import java.sql.SQLException;

public class BoardEvaluationService {
    private final Connection connection;
    private final BoardEvaluationDao boardEvaluationDao;
    private final CustomerDao customerDao;

    public BoardEvaluationService(Connection connection) {
        this.connection = connection;
        this.boardEvaluationDao = new BoardEvaluationDao(connection);
        this.customerDao = new CustomerDao(connection);
    }

    public void evaluate(int customerSeq, int boardSeq) {
        try {
            connection.setAutoCommit(false);

            var result = boardEvaluationDao.insertWhenNotExists(customerSeq, boardSeq);
            if (result == 0) {
                throw new EvaluationDuplicationException("이미 회원님은 이글에 추천을 한번 하셨습니다.");
            }

            boardEvaluationDao.update(boardSeq);
            customerDao.plusPoint(customerSeq);

            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DatabaseAccessException(e.getMessage(), ex);
            }
            throw new DatabaseAccessException(e.getMessage(), e);
        }
    }
}
