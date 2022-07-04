package com.example.demo.mapper;

import com.example.demo.vo.BoardVo;
import com.example.demo.vo.SearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
@Mapper
public interface BoardMapper {
    List<BoardVo> getBoardList();
    void setBoard(BoardVo boardVo);
    BoardVo getBoard(int boardId);


    List<BoardVo> getSearchBoardList(SearchVo vo) throws Exception;

    int boardListCnt() throws Exception;

    int searchBoardListCnt(SearchVo vo) throws Exception;

    int viewCountAdd(int boardId);

    void deleteBoard(int boardId);

    void boardUpdate(BoardVo board) throws Exception;








}
