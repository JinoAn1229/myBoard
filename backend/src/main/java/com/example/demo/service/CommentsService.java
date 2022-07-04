package com.example.demo.service;

import com.example.demo.mapper.CommentsMapper;
import com.example.demo.vo.CommentsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;


    public int setComments(CommentsVo commentsVo) {
        return commentsMapper.setComments(commentsVo);
    }

    public List<CommentsVo> getCommentsList(int boardId) {
        return commentsMapper.getCommentsList(boardId);
    }
}
