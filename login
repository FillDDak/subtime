<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="/inc/header.jsp" />

<div style="text-align:center; margin-top:50px;">
    <h2>로그인</h2>
    <form action="loginProc.jsp" method="post" style="display:inline-block; margin-top:20px;">
        <table border="0" cellpadding="10">
            <tr>
                <td>아이디</td>
                <td><input type="text" name="userId" required></td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td><input type="password" name="userPw" required></td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="로그인" style="padding:5px 20px;">
                </td>
            </tr>
        </table>
    </form>
</div>

<jsp:include page="/inc/footer.jsp" />
