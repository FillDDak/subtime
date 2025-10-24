package controller;

import model.UserDAO;
import model.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("userId");
        String pw = req.getParameter("userPw");

        if (userDAO.checkLogin(id, pw)) {
            HttpSession session = req.getSession();
            session.setAttribute("loginId", id);

            // 이름 표시용(옵션)
            UserDTO u = userDAO.findById(id);
            if (u != null) session.setAttribute("loginName", u.getUserName());

            String redirect = req.getParameter("redirect");
            if (redirect == null || redirect.isBlank()) redirect = req.getContextPath() + "/index";
            resp.sendRedirect(redirect);
        } else {
            req.setAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
