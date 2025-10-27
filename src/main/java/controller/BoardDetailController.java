package controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BoardDetail.do")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. Mock Data: 상세 게시글 정보 생성
		
		// 클라이언트로부터 넘어온 게시글 번호
		int num = 0; 
		String numStr = request.getParameter("num");
		try {
			// 실제 DB에서는 이 번호로 게시글 정보를 조회합니다.
			num = (numStr != null && !numStr.isEmpty()) ? Integer.parseInt(numStr) : 1;
		} catch (NumberFormatException e) {
			num = 1; // 오류 시 기본값 1
		}
		
		Map<String, Object> bean = new HashMap<>();
		
		// 글 수정 권한 테스트를 위해 작성자를 설정합니다. 
		// 짝수 글은 MockUser1이 작성한 것으로, 홀수 글은 OtherUser가 작성한 것으로 가정합니다.
		String mockWriter = (num % 2 == 0) ? "MockUser1" : "OtherUser"; 
		
		bean.put("num", num);
		bean.put("writer", mockWriter); // 게시글 작성자 ID
		bean.put("subject", "Mock 글 번호 " + num + " 상세 제목입니다.");
		bean.put("content", "이것은 Mock 데이터로 만든 게시글의 상세 내용입니다. 작성자 ID: " + mockWriter);
		bean.put("readcount", 150 + num);
		bean.put("reg_date", new Date().toLocaleString());
		bean.put("subwayLine", "2호선");
		bean.put("stationName", "강남역");
		bean.put("ref", num);
		bean.put("re_step", 0);
		bean.put("re_level", 0);
		
		// 2. Mock Data: 로그인 사용자 정보 설정
		
		// 세션 가져오기
		HttpSession session = request.getSession();
		
		// ***************************************************************
		// [자동 로그인 (Mock) 영역 시작]
		// 현재 글 수정 권한 테스트를 위해 임시로 'MockUser1'을 로그인 상태로 설정합니다.
		// 실제 구현 시, 이 코드는 로그인 성공 후 세션에 ID를 저장하는 로직으로 대체되어야 합니다.
		session.setAttribute("s_id", "MockUser1"); 
		// [자동 로그인 (Mock) 영역 끝]
		// ***************************************************************

		
		// 3. Request에 속성 저장
		request.setAttribute("bean", bean);
		
		// 4. View (JSP)로 포워딩
		RequestDispatcher dis = request.getRequestDispatcher("/boardDetail.jsp");
		dis.forward(request, response);
	}
}