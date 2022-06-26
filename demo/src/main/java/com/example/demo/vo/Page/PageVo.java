package com.example.demo.vo.Page;

public class PageVo {

    private int page;
    private int countPerPage;

    public PageVo() {
        this.page = 1;
        this.countPerPage = 10;
    }

    public int getStartPage() {
        return (page-1) * countPerPage;
    }

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getCountPerPage() {
        return countPerPage;
    }
    public void setCountPerPage(int countPerPage) {
        this.countPerPage = countPerPage;
    }

}
