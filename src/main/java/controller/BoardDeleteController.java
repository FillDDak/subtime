package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// BoardDAO 및 BoardDTO 관련 import는 제거되었습니다.

// 이 컨트롤러가 삭제 확인 폼을 보여주는 역할(GET)과 실제 삭제 처리 역할(POST)을 모두 담당합니다.
@WebServlet("/BoardDelete.do")
public class BoardDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ----------------------------------------------------
		// GET 요청: 삭제 확인 폼을 보여주는 로직 (Mock 데이터 사용)
		// ----------------------------------------------------

		// 1. 상세 페이지에서 넘어온 글 번호를 가져옵니다.
		int num = 0;
		try {
			num = Integer.parseInt(request.getParameter("num"));
		} catch (NumberFormatException e) {
			// 유효하지 않은 요청은 목록으로 돌려보냅니다.
			response.sendRedirect("BoardListCon.do");
			return;
		}

		// 2. DAO를 사용하지 않고 Mock 데이터를 생성합니다. (실제 DB에 저장된 패스워드를 가정)
		String mockPassword = "1234"; 

		// 3. JSP로 전달하기 위해 request에 저장합니다.
		// JSP에서 ${mockNum}과 ${mockPassword}를 사용하도록 코드가 변경되어야 합니다.
		request.setAttribute("mockNum", num);
		request.setAttribute("mockPassword", mockPassword);
		
		System.out.println("DEBUG: 글 번호 " + num + "의 삭제 폼 요청. Mock 패스워드 설정 완료 (패스워드: 1234)");


		// 4. 삭제 확인 폼 JSP로 포워딩합니다.
		RequestDispatcher dis = request.getRequestDispatcher("boardDeleteForm.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ----------------------------------------------------
		// POST 요청: 삭제를 최종적으로 처리하는 로직 (Mock 데이터 사용)
		// ----------------------------------------------------

		request.setCharacterEncoding("utf-8");

		// 1. 폼에서 넘어온 데이터(글번호, 사용자가 입력한 패스워드, DB에 저장된 패스워드)를 받습니다.
		int num = Integer.parseInt(request.getParameter("num"));
		String inputPassword = request.getParameter("password"); // 사용자가 입력한 패스워드
		String dbPass = request.getParameter("pass");          // hidden 필드에 담겨 넘어온 DB 패스워드
		
		System.out.println("DEBUG: 글 번호 " + num + " 삭제 처리 시도.");
		System.out.println("DEBUG: 입력 패스워드: " + inputPassword + ", DB 패스워드: " + dbPass);

		// 2. 패스워드 일치 여부 확인
		if (dbPass != null && dbPass.equals(inputPassword)) {
			// 패스워드가 일치하면 삭제 처리 (Mock)
			// 실제로는 DAO.deleteBoard(num); 코드가 들어갈 자리입니다.
			System.out.println("=============================================");
			System.out.println("SUCCESS: 글 번호 " + num + " 삭제 처리 완료 (Mock)");
			System.out.println("=============================================");


			// 삭제 성공 후 목록으로 이동
			RequestDispatcher dis = request.getRequestDispatcher("BoardListCon.do");
			dis.forward(request, response);

		} else {
			// 패스워드가 일치하지 않으면 오류 메시지를 설정하고 목록으로 포워딩
			request.setAttribute("msg", "1"); 
			
			System.out.println("ERROR: 글 번호 " + num + " 삭제 실패. 패스워드 불일치.");
			
			// 오류 메시지를 보여주기 위해 목록 컨트롤러로 포워딩
			RequestDispatcher dis = request.getRequestDispatcher("BoardListCon.do");
			dis.forward(request, response);
		}
	}
}