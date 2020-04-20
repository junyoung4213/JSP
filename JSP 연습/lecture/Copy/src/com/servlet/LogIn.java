package com.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.MySqlMemberDao;
import com.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LogIn extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("viewUrl", "/auth/LogInForm.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ServletContext sc = this.getServletContext();
//			Connection conn = (Connection) sc.getAttribute("conn");

			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");
//			memberDao.setConnection(conn);
			Member member = memberDao.exist(request.getParameter("email"), request.getParameter("password"));

			if (member != null) {
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				request.setAttribute("viewUrl", "redirect:../member/list.do");

			} else {
				request.setAttribute("viewUrl", "/auth/LogInFail.jsp");
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
