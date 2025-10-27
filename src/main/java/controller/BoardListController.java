package controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostDAO;
import model.PostDTO;

public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 총 게시글 수 저장
		int count = 0;

		PostDAO bdao = new PostDAO();
		count = bdao.getAllCount();

		// 수정, 삭제시 받아오는 메시지
		String msg = (String) request.getAttribute("msg");

		// 한페이지에 보여줄 게시글 수
		int pageSize = 10;

		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}

		int number = 0;
		int currentPage = Integer.parseInt(pageNum);

		// 페이지 시작번호, 페이지 끝번호
		// currentPage 1 -> 1~10번 게시글
		// currentPage 2 -> 11~20번 게시글
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;

		// 11개의 글일 (2-1)*10 = 1
		number = count - (currentPage - 1) * pageSize;

		Vector<PostDTO> v = bdao.getAllBoard(startRow, endRow);

		request.setAttribute("count", count);
		request.setAttribute("msg", msg);
		request.setAttribute("v", v);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("number", number);

		RequestDispatcher dis = request.getRequestDispatcher("boardList.jsp");
		dis.forward(request, response);

	}

}
