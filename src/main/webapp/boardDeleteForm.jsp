<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제 확인</title>

<!-- 부트스트랩 대신 간결한 인라인 스타일 유지 -->
<style>
body {
    font-family: 'Malgun Gothic', 'Arial', sans-serif;
    background-color: #f0f4f8;
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
}
#container {
    max-width: 450px;
    width: 100%;
    background-color: #fff;
    padding: 30px 40px;
    border-radius: 12px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
    border-top: 5px solid #d9534f; /* 삭제 테마 컬러 */
}
h2 {
    text-align: center;
    color: #d9534f;
    margin-bottom: 20px;
    border-bottom: 1px solid #eee;
    padding-bottom: 15px;
}
p.warning {
    text-align: center;
    color: #6c757d;
    font-size: 1.1em;
    margin-bottom: 30px;
    line-height: 1.5;
}
.btn-group {
    text-align: center;
    padding-top: 15px;
}
.btn {
    padding: 12px 25px;
    font-size: 16px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    margin: 0 10px;
    transition: background-color 0.3s, transform 0.1s;
    font-weight: bold;
}
.btn-delete {
    background-color: #d9534f; /* 빨간색: 삭제 */
    color: #fff;
}
.btn-delete:hover {
    background-color: #c9302c;
    transform: translateY(-1px);
}
.btn-cancel {
    background-color: #6c757d; /* 회색: 취소 */
    color: #fff;
}
.btn-cancel:hover {
    background-color: #5a6268;
    transform: translateY(-1px);
}
.post-num {
    font-weight: bold;
    color: #333;
}
</style>

</head>
<body>
    <div id="container">
        <h2>게시글 삭제 확인</h2>
        
        <p class="warning">
            현재 로그인된 사용자 ID로 권한이 확인되었습니다.<br>
            정말로 **<span class="post-num">No.${param.num}</span>** 게시글을 삭제하시겠습니까?
            <br><br>
            삭제된 게시글은 복구할 수 없습니다.
        </p>

        <form action="BoardDeleteProcCon.do" method="post">
            <div class="btn-group">
                <!-- 
                    넘겨받은 게시글 번호(num)를 hidden 필드로 전달합니다. 
                    Controller에서 BoardDeleteForm.do로 포워딩할 때 num 파라미터를 넘겨준다고 가정합니다. 
                -->
                <input type="hidden" name="num" value="${param.num}">
                
                <input type="submit" value="확인, 삭제합니다" class="btn btn-delete" />
                
                <button type="button" class="btn btn-cancel" onclick="history.back()">취소</button>
            </div>
        </form>
    </div>
</body>
</html>
