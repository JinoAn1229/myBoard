package com.example.demo.vo;


import com.example.demo.vo.Page.PageVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchVo extends PageVo {

    private String keyword;
    private String condition;
    private String startDate;
    private String endDate;

    public SearchVo() {
        this.keyword = "";
        this.condition= "";
        this.startDate= "";
        this.endDate= "";
    }

    @Override
    public String toString() {
        return "SearchVO [keyword=" + keyword + ", condition=" + condition + "]";
    }




}
