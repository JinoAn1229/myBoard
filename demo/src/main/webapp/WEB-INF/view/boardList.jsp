<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <title>View Test Page</title>
    <style>
        /*datepicer 버튼 롤오버 시 손가락 모양 표시*/
        .ui-datepicker-trigger{cursor: pointer;}
        /*datepicer input 롤오버 시 손가락 모양 표시*/
        .hasDatepicker{cursor: pointer;}
    </style>
</head>
<body>


<script>
    $(function() {



        //모든 datepicker에 대한 공통 옵션 설정
        $.datepicker.setDefaults({
            dateFormat: 'yy-mm-dd' //Input Display Format 변경
            ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
            ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
            ,changeYear: true //콤보박스에서 년 선택 가능
            ,changeMonth: true //콤보박스에서 월 선택 가능
            ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시
            ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
            ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
            ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트
            ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
            ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
            ,minDate: "-1Y" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
            ,maxDate: "+1Y" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)
        });

        //input을 datepicker로 선언
        $("#startDate").datepicker();
        $("#endDate").datepicker();

        //From의 초기값을 오늘 날짜로 설정
        $('#startDate').datepicker('setDate', '${search.startDate}'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
        //To의 초기값을 내일로 설정
        $('#endDate').datepicker('setDate', '${search.endDate}'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)


        /*function boardView() {
            $.ajax({
                type: 'POST',
                url: '/boardView',
                date: {
                        condition : "${condition}",
			            keyword : "${keyword}"
			            startDate : "${startDate}",
			            endDate : "${endDate}"
                error : function(error) {
			            console.log("error");
		        },
		        success : function(data) {
			            console.log("success");
		        }
            });
        }*/

    });
</script>


<div style="font-size: 2.0em;">게시판 - 등록</div>

<form action="/boardList" method = "get">

     <input type="text" id="startDate" name="startDate">~
     <input type="text" id="endDate" name="endDate">

    <table border="1">
        <tr>
            <td>
    <select id="condition" class="form-control" name="condition">
        <option value="boardTitle">제목</option>
        <option value="writer">작성자</option>
        <option value="boardContent">내용</option>
        <option value="titleWriterContent">제목+작성자+내용</option>
    </select>
            </td>
            <td>
                 <input type="text" class="form-control" name="keyword" id="keywordInput" placeholder="검색어" value="${search.keyword}">
            </td>
            <td>
            <span class="input-group-btn">
                <input type="submit" value="검색" class="btn btn-izone btn-flat" id="searchBtn">
            </span>
            </td>
        </tr>
    </table>
    <input type="hidden" name="condition" value="${condition}">
    <input type="hidden" name="keyword" value="${keyword}">


</form>

<div style="font-size: 1.0em;">총${pc.totalArticles}건</div>
<table border="1">
    <thead>
    <tr>
        <th>카테고리</th>
        <th>제목</th>
        <th>작성자</th>
        <th>조회수</th>
        <th>등록일시</th>
        <th>수정일시</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="board">
    <tr>
        <td>${board.categorys}</td>
        <!-- 게시글 제목을 누르면 해당 글을 볼 수 있도록 링크를 걸어둔다 -->
        <td><a href="boardView?boardId=${board.boardId}">${board.boardTitle}</a></td>
        <td>${board.writer}</td>
        <td>${board.views}</td>
        <td>${board.regDate}</td>
        <td>${board.rewriteDate}</td>
    </tr>
    </c:forEach>
    </tbody>
</table>

<ul class="paging">
    <c:if test="${pc.prev}">
        <a class="page-link" href="boardList?page=${pc.paging.page - 1}&countePerPage=${pc.paging.countPerPage}&keyword=${pc.paging.keyword}&condition=${pc.paging.condition}&startDate=${pc.paging.startDate}&endDate=${pc.paging.endDate}"
           style="background-color: #ff52a0; margin-top: 0; heght: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8"><</a>
    </c:if>


    <c:forEach var="pageNum" begin="${pc.beginPage}" end="${pc.endPage}">
        <a href="boardList?page=${pageNum}&countePerPage=${pc.paging.countPerPage}&keyword=${pc.paging.keyword}&condition=${pc.paging.condition}&startDate=${pc.paging.startDate}&endDate=${pc.paging.endDate}" class="page-link" style="margin-top: 0; height: 40px; color: pink; border: 1px solid pink;">${pageNum}</a>
    </c:forEach>

    <c:if test="${pc.next}">
        <a class="page-link" href="boardList?page=${pc.paging.page + 1}&countePerPage=${pc.paging.countPerPage}&keyword=${pc.paging.keyword}&condition=${pc.paging.condition}&startDate=${pc.paging.startDate}&endDate=${pc.paging.endDate}"
           style="background-color: #ff52a0; margin-top: 0; height: 40px; color: white; border: 0px solid #f78f24; opacity: 0.8">></a>
    </c:if>
</ul>







<button onclick="location='boardAdd'">등록</button>
</body>
</html>

