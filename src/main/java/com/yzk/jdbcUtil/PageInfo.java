package com.yzk.jdbcUtil;

import java.util.List;

public class PageInfo {

    /**
     * 查询结果
     */
    private List date;

    /**
     * 总记录数
     */
    private Integer totalRecord;

    /**
     * 当前是第N页
     */
    private Integer pageNum;

    /**
     * 显示N条记录
     */
    private Integer pageSize;

    /**
     * 总页数= 总记录数/每页显示的记录数(totalRecord/pageSize)向上取整
     */
    private Integer pages;

    public PageInfo() {
    }

    public PageInfo(List date, int totalRecord, int pageNum, int pageSize) {
        this.date = date;
        this.totalRecord = totalRecord;
        this.pageNum = pageNum;
        this.pageSize = pageSize;

        this.pages = (int)Math.ceil((totalRecord+0.0)/pageSize);
    }


    @Override
    public String toString() {
        return "PageInfo{" +
                "date=" + date +
                ", totalRecord=" + totalRecord +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", pages=" + pages +
                '}';
    }

    public List getDate() {
        return date;
    }

    public void setDate(List date) {
        this.date = date;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


}
