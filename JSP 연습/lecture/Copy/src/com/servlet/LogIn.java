package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.MemberDao;
import com.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LogIn extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInForm.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ServletContext sc = this.getServletContext();
//			Connection conn = (Connection) sc.getAttribute("conn");
			
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
//			memberDao.setConnection(conn);
			Member member = memberDao.exist(request.getParameter("email"), request.getParameter("password"));

			if (member != null) {
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				response.sendRedirect("../member/list");

			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
				rd.forward(request, response);

			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
