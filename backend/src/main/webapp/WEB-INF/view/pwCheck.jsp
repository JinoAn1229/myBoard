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
                alert("비밀번호를 확인해주세요.")
            }
        })

        function boardList(boardId){
            location.href='boardView?board_id=' + boardId;

        }

        function check(){

            var pwcheck = document.getElementById('pwcheck').value;


            if(pwcheck == ""){
                alert("값을 입력하세요.");
                return false;
            }

        }

    </script>






</head>
<body>

<div style="font-size: 2.0em;">비밀번호 확인</div>
<form action="./funcCheck" method = "post">
    <table border="1">
        <tr>
            <th>비밀번호입력</th>
            <td>
                <input type="password" name="pwcheck" placeholder="비밀번호를 입력하시오.">
                <input type="hidden" name="boardId" value="${boardId}">
                <input type="hidden" name="func" value="${func}">
            </td>
        </tr>
    </table>
    <div style="float: right; width: 90%;">
        <input type="submit" value="확인" onClick="return check();">
    </div>
</form>
<div style="float: left;">
    <button onclick="boardList(${boardId});">취소</button>
</div>

</body>
</html>