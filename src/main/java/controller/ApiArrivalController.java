package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ArrivalDTO;
import util.ApiClient;

public class ApiArrivalController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String station = req.getParameter("station");

        if (station != null && !station.trim().isEmpty()) {
            List<ArrivalDTO> arrivals = ApiClient.getArrivalInfo(station.trim());
            req.setAttribute("station", station);
            req.setAttribute("arrivals", arrivals);
        }

        RequestDispatcher rd = req.getRequestDispatcher("arrival.jsp");
        rd.forward(req, resp);
    }
}