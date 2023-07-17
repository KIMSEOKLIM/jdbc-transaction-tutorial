package kr.co.mz.tutorial.jdbc.model;

public class File {

    private int seq;

    private String fileName;

    private String filePath;

    private int customerSeq;

    private int boardSeq;

    public File(String fileName, String filePath, int customerSeq, int boardSeq) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.customerSeq = customerSeq;
        this.boardSeq = boardSeq;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
