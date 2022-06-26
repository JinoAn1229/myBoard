package com.example.demo.mapper;

import com.example.demo.vo.FilesVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FilesMapper {

    int setFiles(FilesVo filesVo);

    List<FilesVo> getFilesList(int boardId);

    FilesVo getFiles(int filesId);
    int deleteFiles(int filesId);
}
