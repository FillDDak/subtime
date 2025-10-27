<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🚇 지하철 이용 게시글 상세</title>

<link rel="stylesheet" href="./bootstrap-3.3.7-dist/css/bootstrap.min.css" />

<!-- 사용자 지정 스타일 (Custom CSS) -->
<style>
/* -------------------- 1. 기본 레이아웃 및 폰트 설정 -------------------- */
body {
    font-family: 'Malgun Gothic', 'Arial', sans-serif;
    background-color: #eaf1f7;
}
#container {
    width: 75%; 
    max-width: 900px; 
    margin: 40px auto; 
    background-color: #ffffff; 
    padding: 30px;
    border: 1px solid #b3cde0;
    border-radius: 10px;
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}
h2 {
    text-align: center;
    color: #0056b3; 
    margin-bottom: 25px;
    padding-bottom: 10px;
    border-bottom: 3px solid #007bff; 
    display: inline-block; 
}
center {
    display: flex;
    flex-direction: column;
    align-items: center; 
}

/* -------------------- 2. 테이블 스타일 설정 -------------------- */
.table {
    width: 100%;
    margin-top: 20px;
    border-collapse: separate; 
    border-spacing: 0;
}
.table th, .table td {
    padding: 15px;
    vertical-align: middle;
    border-bottom: 1px solid #e0e0e0; 
}
.table th {
    background-color: #d9edf7; 
    color: #333;
    text-align: right;
    width: 150px; 
    font-weight: bold;
    border-right: 1px solid #e0e0e0; 
}
.table tr:last-child th, .table tr:last-child td {
    border-bottom: none; 
}

/* -------------------- 3. 내용 (Content) 영역 스타일 -------------------- */
.content-row td {
    white-space: pre-wrap; 
    text-align: left;
    min-height: 150px; 
}

/* -------------------- 4. 버튼 그룹 스타일 설정 -------------------- */
.btn-group {
    margin-top: 20px;
}
.btn-group input[type="button"] {
    padding: 10px 20px;
    margin: 0 5px;
    border: none;
    border-radius: 5px;
    font-size: 15px;
    cursor: pointer;
    transition: background-color 0.3s, transform 0.1s; 
}

/* 버튼별 색상 지정 */
.btn-reply { 
    background-color: #28a745; 
    color: #fff;
}
.btn-reply:hover {
    background-color: #218838;
}
.btn-update { 
    background-color: #ffc107; 
    color: #333;
}
.btn-update:hover {
    background-color: #e0a800;
}
.btn-delete { 
    background-color: #dc3545; 
    color: #fff;
}
.btn-delete:hover {
    background-color: #c82333;
}
.btn-list { 
    background-color: #007bff; 
    color: #fff;
}
.btn-list:hover {
    background-color: #0056b3;
}
</style>

<script>
    // 글 삭제 POST 요청을 위한 함수
    function confirmDelete() {
        if (confirm('정말로 게시글을 삭제하시겠습니까?')) {
            // 숨겨진 폼을 제출하여 POST 요청을 보냄
            document.getElementById('deleteForm').submit();
        }
    }
</script>

</head>
<body>
   <div id="container">
      <center>
         <h2>🚇 실시간 지하철 이용 게시글</h2>
         <!-- 게시글 상세 정보를 표시하는 테이블 시작 -->
         <table class="table table-bordered" width="100%">
             <!-- 1. 게시글 번호 및 조회수 정보 -->
             <tr height="45">
                <th width="150">글번호</th>
                <td>${bean.num }</td>
                <th width="150">조회수</th>
                <td>${bean.readcount }</td>
             </tr>
             
             <!-- 2. 게시판 컨셉 (지하철) 관련 정보 -->
             <tr height="45">
                 <th>지하철 노선</th>
                 <td>${bean.subwayLine }</td> 
                 <th>역 이름</th>
                 <td>${bean.stationName }</td> 
             </tr>
             
             <!-- 3. 작성자 및 작성일 정보 -->
             <tr height="45">
                <th>작성자</th>
                <td>${bean.writer }</td>
                <th>작성일</th>
                <td>${bean.reg_date }</td>
             </tr>
             
             <!-- 4. 제목 정보 -->
             <tr height="45">
                <th>제목</th>
                <td colspan="3" style="font-weight: bold; font-size: 1.1em; color: #333;">${bean.subject }</td>
             </tr>
             
             <!-- 5. 글 내용 정보 -->
             <tr class="content-row">
                <th style="vertical-align: top;">글내용</th>
                <td colspan="3" style="text-align: left;">${bean.content }</td>
             </tr>
             
             <!-- 6. 액션 버튼 그룹 -->
             <tr>
                <td colspan="4" style="text-align: center; background-color: #f8f8f8;">
                   <div class="btn-group">
                       <!-- 1. 답글 달기 버튼: ref, re_step, re_level 파라미터 전달 -->
                       <input type="button" value="답글달기" class="btn-reply"
                              onclick="location.href='BoardReWriteForm.do?num=${bean.num }&ref=${bean.ref }&re_step=${bean.re_step }&re_level=${bean.re_level}'"/>
                       
                       <!-- 2. 글 수정 버튼: num 파라미터 전달 (작성자만 보이도록 조건 추가) -->
                       <c:if test="${!empty s_id && s_id == bean.writer}">
                           <input type="button" value="글수정" class="btn-update" 
                                  onclick="location.href='BoardUpdateForm.do?num=${bean.num }'" />
                       </c:if>
                              
                       <!-- 3. 글 삭제 버튼: POST 요청을 위해 JS 함수 호출 (작성자만 보이도록 조건 추가) -->
                       <c:if test="${!empty s_id && s_id == bean.writer}">
                           <input type="button" value="글삭제" class="btn-delete" 
                                  onclick="confirmDelete()" />
                       </c:if>
                              
                       <!-- 4. 목록 보기 버튼: 목록 컨트롤러로 이동 -->
                       <input type="button" value="목록보기" class="btn-list" 
                              onclick="location.href='BoardListCon.do'" />
                   </div>
                </td>
             </tr>
         </table>
      </center>
   </div>
   
   <!-- 글 삭제 POST 요청을 위한 숨겨진 폼 -->
   <form id="deleteForm" action="BoardDelete.do" method="post" style="display: none;">
       <input type="hidden" name="num" value="${bean.num}">
   </form>
</body>
</html>
