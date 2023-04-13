package com.yzk.Bean;

public class Borrowing {
   private Integer id;
   private String startTime;
   private String endTime;
   private Integer status;
   private String username;
   private Integer bookid;

    public Borrowing() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public Borrowing(Integer id, String startTime, String endTime, Integer status, String username, Integer bookid) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.username = username;
        this.bookid = bookid;
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", bookid=" + bookid +
                '}';
    }
}
