package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BoardListController.do")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 게시판 페이징 처리 변수 설정 (Mock Data 기반)
		
		// 1-1. 전체 게시글 수 설정 (DB 대신 고정값 사용)
		int count = 30; // 가상의 전체 게시글 수 30개
		
		// 1-2. 페이징 관련 변수 설정
		String pageNumStr = request.getParameter("pageNum");
		// 현재 페이지 설정 (파라미터가 없으면 1페이지)
		int currentPage = (pageNumStr == null || pageNumStr.isEmpty()) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = 10; // 한 페이지당 보여줄 게시글 수
		
		int startRow = (currentPage - 1) * pageSize; // 시작 행 (DB 쿼리용)
		
		// 1-3. 목록에 표시될 시작 번호 계산 (JSP에서 사용됨)
		int number = count - startRow; 
		
		// 2. Mock Data (가짜 게시글 목록) 생성
		List<Map<String, Object>> mockList = new ArrayList<>();
		
		// 30개의 가짜 데이터를 생성하여 List에 추가
		for(int i = 0; i < count; i++) {
			Map<String, Object> bean = new HashMap<>();
			
			// 지하철 정보는 번호에 따라 달라지도록 설정
			String line;
			String station;
			if (i % 3 == 0) {
				line = "2호선";
				station = "강남역";
			} else if (i % 3 == 1) {
				line = "9호선";
				station = "여의도역";
			} else {
				line = "신분당선";
				station = "판교역";
			}
			
			// DTO가 있어야 할 필드들을 Map에 채워넣음
			bean.put("num", count - i); // 최신 글이 위에 오도록 num 역순 설정
			bean.put("writer", "이용자" + (count - i));
			bean.put("subject", line + " " + station + " 실시간 상황 보고");
			bean.put("content", "가짜 게시글 내용입니다.");
			bean.put("readcount", 10 + i);
			bean.put("reg_date", new Date().toLocaleString());
			
			// 지하철 커스텀 필드
			bean.put("subwayLine", line);
			bean.put("stationName", station);
			
			// 답글/원글 처리를 위한 필드 (임시값)
			bean.put("ref", count - i);
			bean.put("re_step", (i % 5 == 0 && i != 0) ? 2 : 1); // 5개 중 1개는 답글로 설정
			bean.put("re_level", (i % 5 == 0 && i != 0) ? 1 : 0);
			
			mockList.add(bean);
		}
		
		// 3. 현재 페이지에 해당하는 데이터만 잘라서 사용 (페이징 시뮬레이션)
		List<Map<String, Object>> pagedList = new ArrayList<>();
		int listSize = mockList.size();
		
		for (int i = startRow; i < startRow + pageSize && i < listSize; i++) {
			pagedList.add(mockList.get(i));
		}
		
		// 4. Request에 속성 저장
		
		// 게시글 목록 (JSP의 <c:forEach items="${v }">에서 사용됨)
		request.setAttribute("v", pagedList); 
		
		// 페이징 및 목록 표시 관련 변수들
		request.setAttribute("count", count); 
		request.setAttribute("number", number); 
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage); 
		
		// 5. View (JSP)로 포워딩
		// 파일명을 "boardList.jsp"로 정확하게 수정했습니다.
		RequestDispatcher dis = request.getRequestDispatcher("/boardList.jsp");
		dis.forward(request, response);
	}
}