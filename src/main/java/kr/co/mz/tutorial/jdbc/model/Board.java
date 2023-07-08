package kr.co.mz.tutorial.jdbc.model;

public class Board extends AbstractModel {
    private int seq;
    private String title;
    private String content;

    private int evaluationTotal;
    private int customerSeq;

    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public int getEvaluationTotal() {
        return evaluationTotal;
    }

    public void setEvaluationTotal(int evaluationTotal) {
        this.evaluationTotal = evaluationTotal;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Board() {
    }

    public Board(String title, String content, int customerSeq) {
        this.title = title;
        this.content = content;
        this.customerSeq = customerSeq;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}

