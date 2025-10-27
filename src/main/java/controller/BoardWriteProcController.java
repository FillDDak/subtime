package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PostDAO;
import model.PostDTO;

@WebServlet("/BoardWriteProcController.do")
public class BoardWriteProcController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rePro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rePro(request, response);
	}

	protected void rePro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		PostDTO bean = new PostDTO();
		bean.setWRITER_ID(request.getParameter("writer"));
		bean.setTITLE(request.getParameter("subject"));
		bean.setCONTENT(request.getParameter("content"));
		
		PostDAO bdao = new PostDAO();
		bdao.insertBoard(bean);
		
		RequestDispatcher dis = request.getRequestDispatcher("BoardListController.do");
		dis.forward(request, response);
	}


}
