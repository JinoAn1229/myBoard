package com.example.demo.service;


import com.example.demo.mapper.FilesMapper;
import com.example.demo.vo.FilesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.List;



@Service
public class FilesService {
    @Autowired
    private FilesMapper filesMapper;

    public int setFiles(MultipartFile[] files) {

        FilesVo filesVo = new FilesVo();
        return filesMapper.setFiles(filesVo);
    }

    public List<FilesVo> getFilesList(int boardId) {
        return filesMapper.getFilesList(boardId);
    }

    public FilesVo getFiles(int filesId) {
        return filesMapper.getFiles(filesId);
    }

    public int deleteFiles(int filesId) {
        return filesMapper.deleteFiles(filesId);
    }

    //TODO 파일업로드 기능 추가
    public void filesUpload(MultipartFile[] files) {

    }

    //TODO 파일다운로드 기능 추가
    public void filesDownload(String filesPath,
                              String filesRealname,
                              HttpServletResponse response
                              ) {

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
        }
    }

    //TODO 파일삭제 기능 추가
    public void filesRemove(String filesPath, String filesRealname) {

        File file = new File(filesPath, filesRealname); // 파일 객체생성
        if (file.exists())  file.delete(); // 파일이 존재하면 파일을 삭제한다.

    }
}
