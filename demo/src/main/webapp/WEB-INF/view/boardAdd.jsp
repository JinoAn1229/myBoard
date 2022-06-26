<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>View Test Page</title>
    <script>

        function check(){

            var categorys = document.getElementById('categorys').value;
            var writer = document.getElementById('writer').value;
            var pw = document.getElementById('pw').value;
            var pwcheck = document.getElementById('pwcheck').value;
            var title = document.getElementById('title').value;
            var content = document.getElementById('content').value;

            if(categorys == "" || writer == "" || pw == "" || pwcheck == "" || title == "" || content == ""){
                alert("값을 입력하세요.");
                return false;
            }
            if(pw != pwcheck) {
                alert("비밀번호를 올바르게 입력해주세요.");
                return false;
            }
        }


    </script>
</head>
<body>
<form action="/boardAdd" method = "post" enctype="multipart/form-data">
    <div style=" font-size: 2.0em;">게시판 - 등록</div>
    <table border="1">
        <tr>
            <th>카테고리</th>
            <td>
                <select id="categorys" name="categorys" size="1"> //TODO db값으로 가져오기 category테이블에서
                    <option value=""></option>
                    <option value="jsp">jsp</option>
                    <option value="java">java</option>
                    <option value="javaScript">javaScript</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>작성자</th>
            <td>
                <input type="text" id="writer" name="writer">
            </td>
        </tr>
        <tr>
            <th>비밀번호</th>
            <td>
                <div style="float: left; width: 33%;">
                    <input type="password"  id="pw" name="pw" placeholder="비밀번호">
                </div>
                <div style="float: left; width: 33%;">
                    <input type="password" id="pwcheck" name="pwcheck" placeholder="비밀번호확인">
                </div>
            </td>
        </tr>
        <tr>
            <th>제목</th>
            <td>
                <input type="text" id="boardTitle" name="boardTitle">
            </td>
        </tr>
        <tr>
            <th>내용</th>
            <td><textarea cols="70" rows="15" id="boardContent" name="boardContent"></textarea></td>
        </tr>
    </table>
    <!-- 파일 업로드 3개-->
    <div>
        <input multiple="multiple" type="file" name="firstFile">
    </div>
    <div>
        <input multiple="multiple" type="file" name="secondFile">
    </div>
    <div>
        <input multiple="multiple" type="file" name="thirdFile">
    </div>
    <div style="float: right; width: 33%;">
        <input type="submit" value="저장" id="insert"  onClick="return check();">
    </div>
</form>
<div style="float: left; width: 50%;">
    <button onclick="location='boardList'">취소</button>
</div>
</body>
</html>

