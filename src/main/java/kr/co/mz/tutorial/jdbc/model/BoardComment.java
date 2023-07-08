package kr.co.mz.tutorial.jdbc.model;

public class BoardComment extends AbstractModel {
    private int seq;
    private String content;
    private int customerSeq;
    private int board_seq;

    public BoardComment() {
    }

    public BoardComment(String content, int customerSeq, int board_seq) {
        this.content = content;
        this.customerSeq = customerSeq;
        this.board_seq = board_seq;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCustomerSeq() {
        return customerSeq;
    }

    public void setCustomerSeq(int customerSeq) {
        this.customerSeq = customerSeq;
    }

    public int getBoard_seq() {
        return board_seq;
    }

    public void setBoard_seq(int board_seq) {
        this.board_seq = board_seq;
    }
}
