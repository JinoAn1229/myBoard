package com.example.demo.mapper;

import com.example.demo.vo.CommentsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentsMapper {

    int setComments(CommentsVo commentsVo);

    List<CommentsVo> getCommentsList(int boardId);
}
