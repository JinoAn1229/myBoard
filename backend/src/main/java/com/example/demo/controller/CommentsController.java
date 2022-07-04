package com.example.demo.controller;

import com.example.demo.service.BoardService;
import com.example.demo.service.CommentsService;
import com.example.demo.vo.BoardVo;
import com.example.demo.vo.CommentsVo;
import com.example.demo.vo.Page.PageCreatorVo;
import com.example.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentsController {

    @Autowired
    CommentsService commentsService;


    /*@PostMapping("/comments")
    public ResponseEntity<CommentsVo> commentsWrite(CommentsVo comments) throws Exception {
        commentsService.setComments(comments);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<Map<String,Object>> commentsList(@PathVariable int id) {

        List<CommentsVo> commentsList = commentsService.getCommentsList(id);

        Map<String, Object> result = new HashMap<>();
        result.put("list", commentsList);

        return ResponseEntity.ok().body(result);
    }*/



    /*@GetMapping("/getCommentsList")
    @ResponseBody
    public List<CommentsVo> getCommentsList(@RequestParam("boardId") int boardId) {
        CommentsVo commentsVo = new CommentsVo();
        commentsVo.setBoardId(boardId);
        return commentsService.getCommentsList(boardId);
    }*/

    /*@GetMapping("/boardView")
    public ModelAndView getCommentsList(int boardId, Model model) throws Exception {

        List<CommentsVo> list = commentsService.getCommentsList(boardId);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("list", list);
        modelAndView.setViewName("redirect:/boardView?boardId=" + boardId);


        return modelAndView;
    }*/

}
