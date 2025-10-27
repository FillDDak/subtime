<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.ArrivalDTO" %>
<jsp:include page="inc/header.jsp" />

<h2>🚉 실시간 지하철 도착 정보</h2>

<form action="arrival" method="get">
    <input type="text" name="station" placeholder="예: 서울역" 
           value="<%= request.getParameter("station") == null ? "" : request.getParameter("station") %>" required>
    <button type="submit">조회</button>
</form>

<hr>

<%
    List<ArrivalDTO> arrivals = (List<ArrivalDTO>) request.getAttribute("arrivals");
    String station = (String) request.getAttribute("station");

    if (station != null) {
        if (arrivals == null || arrivals.isEmpty()) {
%>
        <p><%=station%>역의 도착 정보가 없습니다.</p>
<%
        } else {
%>
        <h3>📍 <%=station%>역 도착 예정</h3>
        <div class="arrival-list">
<%
            for (ArrivalDTO a : arrivals) {
%>
                <div class="arrival-box">
                    <p><strong><%=a.getTrainLine()%></strong></p>
                    <p><%=a.getDirection()%>행</p>
                    <p><%=a.getArrivalMsg()%></p>
                </div>
<%
            }
        }
    }
%>

</div>

<jsp:include page="inc/footer.jsp" />
