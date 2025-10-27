<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🚇 실시간 지하철 이용 게시판</title>

<link rel="stylesheet"
   href="./bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<style>
/* 전체 레이아웃 및 테마 색상 지정 */
body {
   font-family: 'Malgun Gothic', 'Arial', sans-serif;
   background-color: #f0f8ff; /* 연한 하늘색 배경 */
}

#container {
   width: 95%; /* 더 넓게 사용 */
   max-width: 1200px;
   margin: 40px auto;
   background-color: #ffffff;
   padding: 30px;
   border: 1px solid #cceeff;
   border-radius: 12px; /* 더 부드러운 모서리 */
   box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
}

#list h2 {
    color: #007bff; /* 제목 색상 */
    font-weight: bold;
    border-bottom: 2px solid #007bff;
    padding-bottom: 10px;
    margin-bottom: 25px;
}

/* 테이블 스타일 */
.table {
   width: 100%;
   border-collapse: separate;
   border-spacing: 0;
}

.table th, .table td {
   padding: 12px;
   /* 경계선은 테이블 자체에만 적용하고 셀에는 최소화 */
   border-right: none; 
   border-left: none;
   border-bottom: 1px solid #e0e0e0;
   text-align: center;
   vertical-align: middle;
}

.table thead th {
   background-color: #007bff; /* 지하철 테마색 (파란색) */
   color: #ffffff;
   font-weight: 600;
   border-bottom: 3px solid #0056b3;
}

.table tbody tr:hover {
   background-color: #e6f7ff; /* 호버 시 밝은 파란색 */
   cursor: pointer;
}

/* 게시판 상단 정보 스타일 */
#list, #write {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
}
#list b {
    font-size: 1.2em;
    color: #333;
}
#list span {
    color: #dc3545; /* 전체 글 수 강조 */
    font-weight: bold;
    margin-left: 5px;
}

/* 글쓰기 버튼 스타일 */
#write a {
   text-decoration: none;
   padding: 8px 15px;
   border-radius: 4px;
   background-color: #28a745; /* 녹색 버튼 */
   color: #fff;
   font-size: 16px;
   transition: background-color 0.3s;
}

#write a:hover {
   background-color: #218838;
}

/* 페이징 스타일 */
#paging {
   text-align: center;
   margin-top: 30px;
   font-size: 1.1em;
}

#paging a {
    color: #007bff;
    padding: 5px 10px;
    margin: 0 3px;
    border: 1px solid #cceeff;
    border-radius: 4px;
    transition: background-color 0.2s, color 0.2s;
}

#paging a:hover {
    background-color: #007bff;
    color: #ffffff;
    text-decoration: none;
}
</style>

</head>
<body>
   <!-- 메시지 출력 스크립트는 alert() 사용 금지 원칙에 따라 제거했습니다. -->

   <div id="container">
      
      <div id="list">
         <h2>🚇 실시간 지하철 게시판</h2>
         <b>전체 게시글: <span>${count }</span>개</b>
         <div id="write">
            <a href="BoardWriteFormController.do">글쓰기</a>
         </div>
      </div>
      
      <div>
         <table class="table table-striped table-bordered table-hover">
            <thead>
               <tr height="40">
                  <th width="8%">번호</th>
                  <th width="10%">노선</th>        <!-- 지하철 노선 추가 -->
                  <th width="15%">역 명</th>        <!-- 역 이름 추가 -->
                  <th width="40%">제목</th>
                  <th width="10%">작성자</th>
                  <th width="10%">작성일</th>
                  <th width="7%">조회</th>
               </tr>
            </thead>
            
            <tbody>
            <c:set var="number" value="${number }" />
            <c:forEach var="bean" items="${v }">
               <tr height="40">
                  <td align="center">${number }</td>
                  
                  <!-- 지하철 노선 출력 (bean.subwayLine 필드 가정) -->
                  <td align="center">
                    <span style="font-weight: bold; color: #007bff;">${bean.subwayLine }</span>
                  </td>
                  
                  <!-- 역 이름 출력 (bean.stationName 필드 가정) -->
                  <td align="center">
                    ${bean.stationName }
                  </td>

                  <td align="left">
                     <!-- 답글 들여쓰기 처리 -->
                     <c:if test="${bean.re_step > 1 }">
                        <c:forEach var="j" begin="1" end="${(bean.re_step-1) }">
                           &nbsp;&nbsp;&nbsp;
                        </c:forEach>
                        <span style="color:#777;">[답변]</span>
                     </c:if> 
                     
                     <!-- 상세 보기 링크: BoardDetail.do로 변경 -->
                     <a href="BoardDetail.do?num=${bean.num }" style="color:#333;">
                        ${bean.subject } 
                     </a>
                  </td>
                  
                  <td align="center">${bean.writer }</td>
                  <td align="center">${bean.reg_date }</td>
                  <td align="center">${bean.readcount }</td>
               </tr>
               <c:set var="number" value="${number-1 }" />
            </c:forEach>
            </tbody>
         </table>
         <p></p>

         <!-- 페이징처리 구현 영역 -->
         <div id="paging">
            <c:if test="${count>0 }">
               <c:set var="pageCount"
                  value="${count/pageSize + (count%pageSize==0? 0:1) }" />

               <c:set var="startPage" value="1" />
               <c:if test="${currentPage%10 != 0 }">
                  <fmt:parseNumber var="result" value="${currentPage/10 }"
                     integerOnly="true" />
                  <c:set var="startPage" value="${result*10+1 }" />
               </c:if>

               <c:set var="pageBlock" value="10" />
               <c:set var="endPage" value="${startPage+pageBlock-1 }" />

               <c:if test="${endPage>pageCount }">
                  <c:set var="endPage" value="${pageCount}" />
               </c:if>

               <!-- 이전 페이지 블록 이동 -->
               <c:if test="${startPage>10 }">
                  <a href="BoardListController.do?pageNum=${startPage-10 }"> &lt;&lt; 이전 </a>
               </c:if>

               <!-- 페이지 번호 링크 -->
               <c:forEach var="i" begin="${startPage }" end="${endPage }">
                  <c:choose>
                      <c:when test="${i == currentPage}">
                          <a href="BoardListController.do?pageNum=${i }" style="background-color: #007bff; color: #fff; font-weight: bold;"> ${i } </a>
                      </c:when>
                      <c:otherwise>
                          <a href="BoardListController.do?pageNum=${i }"> ${i } </a>
                      </c:otherwise>
                  </c:choose>
               </c:forEach>

               <!-- 다음 페이지 블록 이동 -->
               <c:if test="${endPage<pageCount }">
                  <a href="BoardListController.do?pageNum=${startPage+10 }"> 다음 &gt;&gt; </a>
               </c:if>

            </c:if>
         </div>

      </div>
   </div>
</body>
</html>
