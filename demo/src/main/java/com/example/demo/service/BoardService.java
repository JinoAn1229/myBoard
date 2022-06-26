package com.example.demo.service;

import com.example.demo.mapper.BoardMapper;
import com.example.demo.vo.BoardVo;

import com.example.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;


    public List<BoardVo> selectBoard() {
        List<BoardVo> board = boardMapper.getBoardList();
        return board;
    }


    public void setBoard(BoardVo boardVo) {

        boardMapper.setBoard(boardVo);
    }


    public BoardVo getBoard(int boardId) {
        BoardVo board = boardMapper.getBoard(boardId);
        return board;
    }




    public List<BoardVo> getSearchBoardList(SearchVo vo) throws Exception {
        return boardMapper.getSearchBoardList(vo);

    }




    public int searchBoardListCnt(SearchVo vo) throws Exception {
        return boardMapper.searchBoardListCnt(vo);

    }


    public int viewCountAdd(int boardId) {
        return boardMapper.viewCountAdd(boardId);

    }


    public void deleteBoard(int boardId){

        boardMapper.deleteBoard(boardId);
    }


    public void boardUpdate(BoardVo board) throws Exception{
        boardMapper.boardUpdate(board);
    }

}
