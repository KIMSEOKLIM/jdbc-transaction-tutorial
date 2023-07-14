package kr.co.mz.tutorial.jdbc.model;

public class BoardComment extends AbstractModel {
    private int seq;
    private String content;

    private int boardCommentSeq;
    private int customerSeq;
    private int boardSeq;

    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BoardComment() {
    }


    public BoardComment(String content, int customerSeq, int board_seq) {
        this.content = content;
        this.customerSeq = customerSeq;
        this.boardSeq = board_seq;
    }

    public int getBoardCommentSeq() {
        return boardCommentSeq;
    }

    public void setBoardCommentSeq(int boardCommentSeq) {
        this.boardCommentSeq = boardCommentSeq;
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

    public int getBoardSeq() {
        return boardSeq;
    }

    public void setBoardSeq(int boardSeq) {
        this.boardSeq = boardSeq;
    }
}
