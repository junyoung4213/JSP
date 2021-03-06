package com.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.MySqlMemberDao;

@SuppressWarnings("serial")
@WebServlet("/member/delete")
public class MemberDelete extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ServletContext sc = this.getServletContext();
//			Connection conn = (Connection) sc.getAttribute("conn");

			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");

//			memberDao.setConnection(conn);

			memberDao.delete(Integer.parseInt(request.getParameter("no")));

			request.setAttribute("viewUrl", "redirect:list.do");
		} catch (Exception e) {
			throw new ServletException(e);

		}

	}

}
