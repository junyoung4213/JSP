package com.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vo.Member;

/**
 * Servlet implementation class Dispatcher
 */
@SuppressWarnings("serial")
@WebServlet("*.do")
public class Dispatcher extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ServletPath = request.getServletPath();

		try {
			String pageControllerPath = null;

			if ("/member/list.do".equals(ServletPath)) {
				pageControllerPath = "/member/list";
			} else if ("/member/add.do".equals(ServletPath)) {
				pageControllerPath = "/member/add";
			} else if ("/member/update.do".equals(ServletPath)) {
				pageControllerPath = "/member/update";
				if (request.getParameter("email") != null) {
					request.setAttribute("member", new Member().setEmail(request.getParameter("email"))
							.setName(request.getParameter("name")).setPassword(request.getParameter("password")));
				}
			} else if ("/member/delete.do".equals(ServletPath)) {
				pageControllerPath = "/member/delete";
				if (request.getParameter("email") != null) {
					request.setAttribute("member", new Member().setNo(Integer.parseInt(request.getParameter("no")))
							.setEmail(request.getParameter("email")).setName(request.getParameter("name")));
				}
			} else if ("/auth/login.do".equals(ServletPath)) {
				pageControllerPath = "/auth/login";
			} else if ("/auth/logout.do".equals(ServletPath)) {
				pageControllerPath = "/auth/logout";
			}

			RequestDispatcher rd = request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);

			String viewUrl = (String) request.getAttribute("viewUrl");

			if (viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}

	}
}
