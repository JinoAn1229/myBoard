package com.example.demo.controller;

import com.example.demo.service.BoardService;
import com.example.demo.service.CommentsService;
import com.example.demo.service.FileSystemStorageService;
import com.example.demo.service.FilesService;
import com.example.demo.vo.BoardVo;
import com.example.demo.vo.CommentsVo;
import com.example.demo.vo.FilesVo;
import com.example.demo.vo.Page.PageCreatorVo;
import com.example.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.http.WebSocketHandshakeException;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.util.Map;


@Controller
public class BoardController {

    @Autowired
    BoardService boardService;
    @Autowired
    CommentsService commentsService;

    @Autowired
    FilesService filesService;

    @Autowired
    FileSystemStorageService fileSystemStorageService;


    @GetMapping("/boardList")
    public ResponseEntity<Map<String,Object>> boardList(SearchVo vo, Model model) throws Exception {

        List<BoardVo> list = boardService.getSearchBoardList(vo);
        PageCreatorVo pc = new PageCreatorVo();
        pc.setPaging(vo);
        pc.setTotalArticles(boardService.searchBoardListCnt(vo));


        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("pc", pc);
        result.put("search", vo);

        return ResponseEntity.ok().body(result);
    }



    @PostMapping("/boardAdd")
    public ResponseEntity<BoardVo> boardAdd(@Validated BoardVo boardVo, @RequestParam("files") List<MultipartFile> files) throws Exception{


        //파일 폼 데이터 보내는 방식 다시 한번 검토
        boardService.setBoard(boardVo);
        FilesVo filesVo = new FilesVo();

        fileSystemStorageService.store(files);;

        return new ResponseEntity<>(boardVo, HttpStatus.OK);
    }



    @GetMapping("/boardView")
    public ResponseEntity<BoardVo> boardView(@RequestParam(value="boardId") int boardId, Model model) {

        boardService.viewCountAdd(boardId); //조회수 증가

        BoardVo boardVo = boardService.getBoard(boardId);

        List<FilesVo> filesVoList = filesService.getFilesList(boardId); //파일 목록 가져오기 (분리할까..) 첨에 한번만 가져오면 되니까..


        model.addAttribute("board", boardVo);
        //model.addAttribute("filesList", filesVoList);
        return new ResponseEntity<>(boardVo, HttpStatus.OK);
    }

    @PostMapping("/commentsAdd")
    public ResponseEntity<String> commentsAdd(@RequestBody CommentsVo commentsVo) {

        commentsVo.setBoardId(commentsVo.getBoardId());
        commentsVo.setCommentsContent(commentsVo.getCommentsContent());

        int result = commentsService.setComments(commentsVo);
        return result == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/commentsList")
    public ResponseEntity<List<CommentsVo>> commentsList(@RequestParam(value="boardId") int boardId) {

        return new ResponseEntity<>(commentsService.getCommentsList(boardId), HttpStatus.OK);
    }

    //TODO ajax로  구현
    @GetMapping("/filesDownload")
    public void filesDownload(@RequestParam(value="filesId") int filesId, HttpServletResponse response) {

        FilesVo filesVo = filesService.getFiles(filesId);

        String filesRealname = filesVo.getFilesRealname(); // 서버에 실제 저장된 파일명
        String filesPath = filesVo.getFilesPath();

        fileSystemStorageService.loadAsResource(filesRealname);

        /*FilesVo filesVo = filesService.getFiles(filesId);

        String filesRealname = filesVo.getFilesRealname(); // 서버에 실제 저장된 파일명
        String filesPath = filesVo.getFilesPath();  // 파일 업로드된 경로

        try{

            String filename =  new String(filesRealname.toString().getBytes("euc-kr"),"iso-8859-1");		//--- 한글 파일명이 깨지지 않도록 처리합니다. toString의 경우 객체가 null이면 에러가 발생되니 주의해서 사용하세요~
            File file = new File( filesPath, filesRealname);		//--- 파일명과 경로를 이용해서 File객체를 가져옵니다.
            String mimeType= URLConnection.guessContentTypeFromName(filename);		//--- 파일의 mime타입을 확인합니다.
            if(mimeType==null){			//--- 마임타입이 없을 경우 application/octet-stream으로 설정합니다.
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);	//--- reponse에 mimetype을 설정합니다.
            response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");		//--- Content-Disposition를 attachment로 설정하여 다운로드 받을 파일임을 브라우저에게 알려줍니다.
            response.setContentLength((int)file.length());		//--- response content length를 설정합니다.
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));	//--- inputstream 객체를 얻습니다.
            FileCopyUtils.copy(inputStream, response.getOutputStream());		//--- inputstream으로 파일을 읽고 outputsream으로 파일을 씁니다.

        }catch( Exception e ){
            e.printStackTrace();
        }*/







        /*InputStream in = null;
        OutputStream os = null;
        File file = null;
        boolean skip = false;
        String client = "";

        try{
            // 파일을 읽어 스트림에 담기
            try{
                file = new File(filesPath, filesRealname);  //업로드된 경로와 실제 파일명으로 파일객체 생성
                in = new FileInputStream(file);
            }catch(FileNotFoundException fe){  //파일이 지정된 경로에 없거나 할때 예외처리
                skip = true;
            }
            client = request.getHeader("User-Agent");

            // 파일 다운로드 헤더 지정
            response.reset() ;
            response.setContentType("application/octet-stream");  //response할 형식이 파일이라고 알려줌
            response.setHeader("Content-Description", "JSP Generated Data");

            if(!skip){  //파일이 존재하면
                // IE
                if(client.indexOf("MSIE") != -1){
                    response.setHeader ("Content-Disposition", "attachment; filename="+new String(filesName.getBytes("KSC5601"),"ISO8859_1"));

                }else{
                    // 한글 파일명 처리
                    filesName = new String(filesName.getBytes("utf-8"),"iso-8859-1");

                    response.setHeader("Content-Disposition", "attachment; filename=\"" + filesName + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }

                response.setHeader ("Content-Length", ""+file.length() ); //파일 받는것까지 완료

                os = response.getOutputStream();
                byte b[] = new byte[1024];  //파일을 한번에 얼마나 읽을지 설정. 조절가능 , 한번에 다 읽거나 크게 읽을면 오류날 수 있음
                int leng = 0;

                while( (leng = in.read(b)) > 0 ){  //in에 담겨져있는 파일을 os로 읽는다
                    os.write(b,0,leng);
                }

            }

            in.close();
            os.close();

        }catch(Exception e){
            e.printStackTrace();
        }*/


    }

    //TODO ajax로 post로 구현
    @DeleteMapping("/filesDelete")
    public ResponseEntity<String> filesDelete(@RequestParam(value="filesId") int filesId) {

        //TODO 실제 파일 삭제하는 코드 넣기
        FilesVo filesVo = filesService.getFiles(filesId);
        filesService.filesRemove(filesVo.getFilesPath(), filesVo.getFilesRealname()); //실제 파일에서 삭제

        filesService.deleteFiles(filesId); //db에서 삭제

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/pwCheck")
    public String pwCheck(Model model, @RequestParam(value="boardId") int boardId,
                                        @RequestParam(value="func") String func) throws IOException{

        model.addAttribute("boardId", boardId);
        model.addAttribute("func", func);
        return "pwCheck";
    }


    @PostMapping("/funcCheck")
    public ModelAndView pwCheckAction(HttpServletRequest request) throws IOException {

        int boardId = Integer.parseInt(request.getParameter("boardId"));
        String func = (String)request.getParameter("func");
        String pwCheck = (String)request.getParameter("pwcheck");


        BoardVo board = boardService.getBoard(boardId);

        ModelAndView modelAndView = new ModelAndView();

        if(pwCheck.equals(board.getPw())) {
            if(func.equals("update")){
                modelAndView.setViewName("redirect:/boardUpdate?boardId=" + boardId);
            }
            else if(func.equals("delete")){
                modelAndView.setViewName("redirect:/boardDelete?boardId=" + boardId);
            }
        }
        else{
            modelAndView.setViewName("redirect:/pwCheck?boardId=" + boardId +"&func=" + func);
        }
        return modelAndView;
    }

    @GetMapping("/boardUpdate")
    public String boardUpdateView(@RequestParam(value="boardId") int boardId, Model model) {

        BoardVo board = boardService.getBoard(boardId);

        List<FilesVo> filesVoList = filesService.getFilesList(boardId); //파일 목록 가져오기 (분리할까..) 첨에 한번만 가져오면 되니까..

        model.addAttribute("filesList", filesVoList);
        model.addAttribute("board", board);

        return "boardUpdate";
    }

    //삭제기능이라 post로 바꿔서 보내는게 좋겠다
    @GetMapping("/boardDelete")
    public String boardDelete(@RequestParam(value="boardId") int boardId, Model model) {

        boardService.deleteBoard(boardId);

        model.addAttribute("msg", "글 삭제 완료");
        model.addAttribute("url", "/boardList");

        return "message";
    }




    @PostMapping("/boardUpdate")
    public String boardUpdate(BoardVo board, Model model) throws Exception {

        boardService.boardUpdate(board);

        //TODO 파일업로드 기능 추가
       //filesService.setFiles(filesVo); //db에서 파일 추가
        //filesService.filesUpload(filesVo); //실제 파일에 업로드

        model.addAttribute("msg", "글 수정 완료");
        model.addAttribute("url", "/boardView?boardId=" + board.getBoardId());


        return "message";
    }








}


