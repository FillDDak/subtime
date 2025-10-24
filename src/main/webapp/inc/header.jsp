<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String loginId = (String)session.getAttribute("loginId");
    String context = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SubTime</title>
    <link rel="stylesheet" href="<%=context%>/assets/css/style.css">
    <style>
        header {
            background-color: #004C99;
            color: white;
            padding: 15px;
            text-align: center;
        }
        nav {
            background-color: #f0f0f0;
            padding: 10px;
            text-align: center;
        }
        nav a {
            margin: 0 15px;
            color: #333;
            text-decoration: none;
            font-weight: bold;
        }
        nav a:hover {
            color: #004C99;
            text-decoration: underline;
        }
        .user-info {
            float: right;
            margin-right: 40px;
            color: #444;
        }
    </style>
</head>
<body>
<header>
    <h1>🚇 SubTime 지하철 실시간 정보 시스템</h1>
</header>
<nav>
    <a href="<%=context%>/index">홈</a>
    <a href="<%=context%>/arrival">실시간 도착정보</a>
    <a href="<%=context%>/board/list">게시판</a>

    <% if (loginId == null) { %>
        <a href="<%=context%>/login">로그인</a>
    <% } else { %>
        <span class="user-info"><%=loginId%> 님</span>
        <a href="<%=context%>/logout">로그아웃</a>
    <% } %>
</nav>
<hr/>
