package kr.co.mz.tutorial.jdbc.model;

public class BoardEvaluation extends AbstractModel {
    private int seq;
    private int evaluation;
    private int customerSeq;
    private int boardSeq;


    public BoardEvaluation() {
    }

    public BoardEvaluation(int customerSeq, int boardSeq) {
        this.customerSeq = customerSeq;
        this.boardSeq = boardSeq;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public int getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(int customerSeq) {
        this.customerSeq = customerSeq;
    }

    public int getBoardSeq() {
        return boardSeq;
    }

    public void setBoardSeq(int boardSeq) {
        this.boardSeq = boardSeq;
    }
}
