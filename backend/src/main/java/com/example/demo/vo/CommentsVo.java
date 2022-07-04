package com.example.demo.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentsVo {

    private int commentsId;
    private String commentsContent;
    private int boardId;
    private String regDate;
}
