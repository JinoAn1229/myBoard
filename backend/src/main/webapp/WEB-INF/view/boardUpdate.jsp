<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Test Page</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script>

        $(function(){
            var responseMessage = "<c:out value='${errorMsg}'/>";
            if(responseMessage != ""){
                alert(responseMessage)
            }
        })


        function check(){


            var writer = document.getElementById('writer').value;
            var pw = document.getElementById('pw').value;
            var title = document.getElementById('title').value;
            var content = document.getElementById('content').value;

            if( writer == "" || pw == "" ||  title == "" || content == ""){
                alert("값을 입력하세요.");
                return false;
            }

        }

        /*function getFilesList(){
            $('#commentsList').empty(); //댓글 목록을 새로 가져와 테이블을 그리기 전에 미리 있던 내용 지우고 그리기
            var boardId = $('#boardId').val();
            var sParam = "boardId=" + boardId;

            $.getJSON("filesList",sParam,function(data){
                var html = "";
                for(var i=0; i < date.length; i++) {
                }
                $(data).each(function(){
                    html = "<tr>"
                        + "<td><div>" +  this.regDate + "</div>"
                        + "<div>" + this.commentsContent  +"</div></td>"
                        + "</tr>";
                    $('#commentsList').append(html);
                });

            });
        }*/

        function filesDownload(filesId){
            var query = {filesId : filesId};

            $.ajax({
                type : "GET",
                url : '/filesDownload',
                data : query,
                dataType : "text",
                success: function(data){

                },
                error : function(data) {
                    alert("다운로드 실패");
                }
            });
        }

        function filesDelete(filesId){
            var query = {filesId : filesId};

            $.ajax({
                type : "DELETE",
                url : '/filesDelete',
                data : query,
                dataType : "text",
                success: function(data){
                    location.reload();
                },
                error : function(data) {
                    alert("삭제실패");
                }
            });


        }


    </script>

</head>
<body>
<form action="/boardUpdate" method = "post">
<div style="font-size: 2.0em;">게시판 - 수정</div>
    <input type="hidden" name="boardId" value="${board.boardId}">
<table border="1">
    <tr>
        <th>작성자</th>
        <td>
            ${board.categorys}
        </td>
    </tr>
    <tr>
        <th>등록일시</th>
        <td>
            ${board.regDate}
        </td>
    </tr>
    <tr>
        <th>수정일시</th>
        <td>
            ${board.rewriteDate}
        </td>
    </tr>
    <tr>
        <th>조회수</th>
        <td>
            ${board.views}
        </td>
    </tr>
    <tr>
        <th>작성자</th>
        <td>
            <input type="text" id="writer" name="writer" value="${board.writer}">
        </td>
    </tr>
    <tr>
        <th>비밀번호</th>
        <td>
            <input type="text" id="pw" name="pw" value="${board.pw}">
        </td>
    </tr>
    <tr>
        <th>제목</th>
        <td>
            <input type="text" id="boardTitle" name="boardTitle" value="${board.boardTitle}">
        </td>
    </tr>
    <tr>
        <th>내용</th>
        <td><textarea cols="70" rows="15" id="boardContent" name="boardContent">${board.boardContent}</textarea></td>
    </tr>
    <tr>
        <th>파일첨부</th>
        <td>
            <c:forEach items="${filesList}" var="filesList">
                <div>
                    <span>${filesList.filesName}</span>
                    <span><input type='button' value='Download' onclick="filesDownload('${filesList.filesId}');"></span>
                    <span><input type='button' value='삭제' onclick="filesDelete('${filesList.filesId}');"></span>
                </div>
            </c:forEach>
        </td>
    </tr>

</table>
    <div style="float: right; width: 33%;">
        <input type="submit" value="저장" id="update"  onClick="return check();">
    </div>
</form>
<button type="button" onclick="location.href='boardView?boardId=${board.boardId}'">취소</button>
</body>
</html>

