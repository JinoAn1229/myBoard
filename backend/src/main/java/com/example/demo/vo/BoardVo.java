package com.example.demo.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BoardVo {

    private int boardId;
    @NotBlank
    private String boardTitle;
    private String boardContent;
    private String writer;
    private String pw;
    private int views;
    private String categorys;
    private String regDate;
    private String rewriteDate;

}
