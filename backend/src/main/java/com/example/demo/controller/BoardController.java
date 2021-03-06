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


        //?????? ??? ????????? ????????? ?????? ?????? ?????? ??????
        boardService.setBoard(boardVo);
        FilesVo filesVo = new FilesVo();

        fileSystemStorageService.store(files);;

        return new ResponseEntity<>(boardVo, HttpStatus.OK);
    }



    @GetMapping("/boardView")
    public ResponseEntity<BoardVo> boardView(@RequestParam(value="boardId") int boardId, Model model) {

        boardService.viewCountAdd(boardId); //????????? ??????

        BoardVo boardVo = boardService.getBoard(boardId);

        List<FilesVo> filesVoList = filesService.getFilesList(boardId); //?????? ?????? ???????????? (????????????..) ?????? ????????? ???????????? ?????????..


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

    //TODO ajax???  ??????
    @GetMapping("/filesDownload")
    public void filesDownload(@RequestParam(value="filesId") int filesId, HttpServletResponse response) {

        FilesVo filesVo = filesService.getFiles(filesId);

        String filesRealname = filesVo.getFilesRealname(); // ????????? ?????? ????????? ?????????
        String filesPath = filesVo.getFilesPath();

        fileSystemStorageService.loadAsResource(filesRealname);

        /*FilesVo filesVo = filesService.getFiles(filesId);

        String filesRealname = filesVo.getFilesRealname(); // ????????? ?????? ????????? ?????????
        String filesPath = filesVo.getFilesPath();  // ?????? ???????????? ??????

        try{

            String filename =  new String(filesRealname.toString().getBytes("euc-kr"),"iso-8859-1");		//--- ?????? ???????????? ????????? ????????? ???????????????. toString??? ?????? ????????? null?????? ????????? ???????????? ???????????? ???????????????~
            File file = new File( filesPath, filesRealname);		//--- ???????????? ????????? ???????????? File????????? ???????????????.
            String mimeType= URLConnection.guessContentTypeFromName(filename);		//--- ????????? mime????????? ???????????????.
            if(mimeType==null){			//--- ??????????????? ?????? ?????? application/octet-stream?????? ???????????????.
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);	//--- reponse??? mimetype??? ???????????????.
            response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");		//--- Content-Disposition??? attachment??? ???????????? ???????????? ?????? ???????????? ?????????????????? ???????????????.
            response.setContentLength((int)file.length());		//--- response content length??? ???????????????.
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));	//--- inputstream ????????? ????????????.
            FileCopyUtils.copy(inputStream, response.getOutputStream());		//--- inputstream?????? ????????? ?????? outputsream?????? ????????? ?????????.

        }catch( Exception e ){
            e.printStackTrace();
        }*/







        /*InputStream in = null;
        OutputStream os = null;
        File file = null;
        boolean skip = false;
        String client = "";

        try{
            // ????????? ?????? ???????????? ??????
            try{
                file = new File(filesPath, filesRealname);  //???????????? ????????? ?????? ??????????????? ???????????? ??????
                in = new FileInputStream(file);
            }catch(FileNotFoundException fe){  //????????? ????????? ????????? ????????? ?????? ????????????
                skip = true;
            }
            client = request.getHeader("User-Agent");

            // ?????? ???????????? ?????? ??????
            response.reset() ;
            response.setContentType("application/octet-stream");  //response??? ????????? ??????????????? ?????????
            response.setHeader("Content-Description", "JSP Generated Data");

            if(!skip){  //????????? ????????????
                // IE
                if(client.indexOf("MSIE") != -1){
                    response.setHeader ("Content-Disposition", "attachment; filename="+new String(filesName.getBytes("KSC5601"),"ISO8859_1"));

                }else{
                    // ?????? ????????? ??????
                    filesName = new String(filesName.getBytes("utf-8"),"iso-8859-1");

                    response.setHeader("Content-Disposition", "attachment; filename=\"" + filesName + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }

                response.setHeader ("Content-Length", ""+file.length() ); //?????? ??????????????? ??????

                os = response.getOutputStream();
                byte b[] = new byte[1024];  //????????? ????????? ????????? ????????? ??????. ???????????? , ????????? ??? ????????? ?????? ????????? ????????? ??? ??????
                int leng = 0;

                while( (leng = in.read(b)) > 0 ){  //in??? ??????????????? ????????? os??? ?????????
                    os.write(b,0,leng);
                }

            }

            in.close();
            os.close();

        }catch(Exception e){
            e.printStackTrace();
        }*/


    }

    //TODO ajax??? post??? ??????
    @DeleteMapping("/filesDelete")
    public ResponseEntity<String> filesDelete(@RequestParam(value="filesId") int filesId) {

        //TODO ?????? ?????? ???????????? ?????? ??????
        FilesVo filesVo = filesService.getFiles(filesId);
        filesService.filesRemove(filesVo.getFilesPath(), filesVo.getFilesRealname()); //?????? ???????????? ??????

        filesService.deleteFiles(filesId); //db?????? ??????

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

        List<FilesVo> filesVoList = filesService.getFilesList(boardId); //?????? ?????? ???????????? (????????????..) ?????? ????????? ???????????? ?????????..

        model.addAttribute("filesList", filesVoList);
        model.addAttribute("board", board);

        return "boardUpdate";
    }

    //?????????????????? post??? ????????? ???????????? ?????????
    @GetMapping("/boardDelete")
    public String boardDelete(@RequestParam(value="boardId") int boardId, Model model) {

        boardService.deleteBoard(boardId);

        model.addAttribute("msg", "??? ?????? ??????");
        model.addAttribute("url", "/boardList");

        return "message";
    }




    @PostMapping("/boardUpdate")
    public String boardUpdate(BoardVo board, Model model) throws Exception {

        boardService.boardUpdate(board);

        //TODO ??????????????? ?????? ??????
       //filesService.setFiles(filesVo); //db?????? ?????? ??????
        //filesService.filesUpload(filesVo); //?????? ????????? ?????????

        model.addAttribute("msg", "??? ?????? ??????");
        model.addAttribute("url", "/boardView?boardId=" + board.getBoardId());


        return "message";
    }








}


