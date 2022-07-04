package com.example.demo.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilesVo extends BoardVo{

    private String filesName;
    private String filesRealname;
    private String filesPath;
    private String filesExtension;
    private int filesId;
    private int boardId;

}
