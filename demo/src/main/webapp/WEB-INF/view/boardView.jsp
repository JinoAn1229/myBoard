<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
    <title>View Test Page</title>

    <script>

       $(function(){
            getCommentsList();
        });


    
        function commentsAdd(){

            var boardId = $('#boardId').val();
		    var commentsContent = $('#commentsContent').val();
		    var query = {boardId : boardId, commentsContent : commentsContent};

		    $.ajax({
                type : "POST",
                headers:{"Content-type":"application/json","X-HTTP-Method-Override":"POST"},
		        url : '/commentsAdd',
		        data : JSON.stringify({
                    boardId:boardId,
                    commentsContent:commentsContent
                }),
                dataType : "text",
	            success: function(data){
                        getCommentsList(); //파일을 추가하면 다시 댓글 목록을 가져와 보여준다
		        },
	            error : function(data) {
		            alert("댓글이 추가되지 않았습니다.");
	            }
		    });
		    clearText();

	    }


	    function getCommentsList(){
            $('#commentsList').empty(); //댓글 목록을 새로 가져와 테이블을 그리기 전에 미리 있던 내용 지우고 그리기
		    var boardId = $('#boardId').val();
	        var sParam = "boardId=" + boardId;

		    $.getJSON("commentsList",sParam,function(data){
                var html = "";
                $(data).each(function(){
                    html = "<tr>"
			        + "<td><div>" +  this.regDate + "</div>"
			        + "<div>" + this.commentsContent  +"</div></td>"
			        + "</tr>";
                    $('#commentsList').append(html);
		        });

		    });
	    }

        function clearText(){  //댓글추가 후 댓글내용란 클리어 - 매개변수를 넣어서 유동적이게 사용할 수 있게 바꿔도 좋을듯
	        document.getElementById("commentsContent").value ="";
	    }
        
    function updates(boardId){
        location.href='pwCheck?boardId=' + boardId +'&func=update';
    }

    function deletes(boardId){
        location.href='pwCheck?boardId=' + boardId +'&func=delete';
    }

    function boardList(){
           location.href='boardList';
    }

    function filesDownload(filesId){
        location.href='filesDownload?filesId=' + filesId;
    }

    </script>

</head>
<body>
<div style="font-size: 2.0em;">게시판 - 보기</div>


<table>
    <tr>
        <td width="100">${board.categorys}</td>
        <td width="300">${board.boardTitle}</td>
        <td width="100">조회수:${board.views}</td>
    </tr>

</table>
<table>
    <tr>
        <td width="300"><textarea name="commentsContent" class="form-control" value = ""cols="50" rows="4" readonly>${board.boardContent}</textarea></td>
    </tr>
</table>
<br><br>

<c:forEach items="${filesList}" var="filesList">
    <div>
        <span>${filesList.filesName}</span>
        <span><input type='button' value='Download' onclick="filesDownload('${filesList.filesId}');"></span>
    </div>
</c:forEach>
<br>
<br>

    <div class="comments">
        <table id='commentsList'>

        </table>

        <input type="hidden" id= "boardId" name="boardId" value="${board.boardId}">
        <table border="1">
            <tr>
                <th>내용</th>
                <td><textarea cols="50" rows="3" name="commentsContent" id="commentsContent"></textarea></td>
                <td><button onclick="commentsAdd();">등록</button></td>
            </tr>
        </table>
    </div>

<button onclick="boardList();">목록</button>
<button onclick="updates(${board.boardId});">수정</button>
<button onclick="deletes(${board.boardId});">삭제</button>
</body>
</html>

