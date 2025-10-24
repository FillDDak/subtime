<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 쇼핑몰</title>
<style>
   nav { background: #eee; padding: 10px;}
   nav a {margin-right: 15px; text-decoration: none; font-weight: bold;}
</style>
</head>
<body>
<nav>
   <a href="index.jsp">홈</a>
   <a href="product.jsp?view=all">상품목록</a>
   <a href="mypage.jsp?check=1">마이페이지</a>
   <c:choose>
      <c:when test="${empty sessionScope.userId}">
         <a href="login.jsp?redirect=product">로그인</a>
      </c:when>
   <c:otherwise>
      <a href="logout.jsp">로그아웃 (${sessionScope.userId })</a>
   </c:otherwise>
   </c:choose>
</nav>
</body>
</html>