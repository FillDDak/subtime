<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board Write</title>

<link rel="stylesheet" href="./bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<style>
body {
    font-family: 'Arial', sans-serif;
    background-color: #f7f7f7;
}
#container {
    width: 70%;
    margin: 40px auto;
    background-color: #ffffff;
    padding: 20px;
    border: 1px solid #cccccc;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.table {
    width: 100%;
    margin-top: 20px;
    border-collapse: collapse;
}
.table th, .table td {
    padding: 12px;
    vertical-align: middle;
    border: 1px solid #cccccc;
}
.table th {
    background-color: #e9f5ff;
    color: #333;
    text-align: right;
    width: 100px;
}
input[type="text"], input[type="email"], input[type="password"], textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}
textarea {
    resize: vertical;
}
input[type="submit"], input[type="reset"], button {
    padding: 10px 20px;
    margin-right: 10px;
    border: none;
    border-radius: 4px;
    background-color: #007bff;
    color: #fff;
    font-size: 16px;
    cursor: pointer;
    transition: background-color 0.3s;
}
input[type="submit"]:hover, input[type="reset"]:hover {
    background-color: #0056b3;
}
button {
    background-color: #28a745;
}
button:hover {
    background-color: #217530;
}
h2 {
    text-align: center;
    color: #333;
}
</style>

</head>
<body>
	<div id="container">
		<b>게시글 입력하기</b>
		<p />
		<form action="BoardWriteProcCon.do" method="get">
			<center>
				<table class="table table-striped table-bordered table-hover">
					<tbody>
						<tr height="50">
							<th width="150" align="center">작성자</th>
							<td width="400" align="left">
								<input type="text" name="writer" size="60" style="background: transparent;"/>
							</td>
						</tr>
						<tr height="50">
							<th width="150" align="center">제목</th>
							<td width="400" align="left">
								<input type="text" name="subject" size="60" style="background: transparent;"/>
							</td>
						</tr>
						<tr height="50">
							<th width="150" align="center">이메일</th>
							<td width="400" align="left">
								<input type="email" name="email" size="60" style="background: transparent;"/>
							</td>
						</tr>
						<tr height="50">
							<th width="150" align="center">비밀번호</th>
							<td width="400" align="left">
								<input type="password" name="password" size="60" style="background: transparent;"/>
							</td>
						</tr>
						<tr height="50">
							<th width="150" align="center">글내용</th>
							<td width="400" align="left">
								<textarea rows="10" cols="50" name="content"></textarea>
							</td>
						</tr>
						<tr height="50">
							<th align="center" colspan="2">
								<input type="submit" value="글쓰기"/>&nbsp;&nbsp;
								<input type="reset" value="다시작성"/>&nbsp;&nbsp;
								<button type="button" onclick="location.href='BoardListController.do'">목록보기</button>
							</th>
						</tr>
					
					</tbody>
				</table>
			</center>
		</form>
	</div>
</body>
</html>