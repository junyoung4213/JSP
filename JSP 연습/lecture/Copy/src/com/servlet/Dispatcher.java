package com.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controls.Controller;
import com.controls.LogInController;
import com.controls.LogOutController;
import com.controls.MemberAddController;
import com.controls.MemberDeleteController;
import com.controls.MemberListController;
import com.controls.MemberUpdateController;
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

		String servletPath = request.getServletPath();

		try {
			ServletContext sc = this.getServletContext();
			
			Controller pageController = (Controller) sc.getAttribute(servletPath);
			// pageController에게 전달할 Map 객체를 준비
			HashMap<String, Object> model = new HashMap<String, Object>();
			// model에 session 객체를 저장해준다(로그인&로그아웃때 사용할 것)
			model.put("session",request.getSession());
			
			if ("/member/list.do".equals(servletPath)) {
			} else if ("/member/add.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					model.put("member", new Member().setEmail(request.getParameter("email"))
							.setName(request.getParameter("name")).setPassword(request.getParameter("password")));
				}
			} else if ("/member/update.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					model.put("member", new Member().setEmail(request.getParameter("email"))
							.setName(request.getParameter("name")).setNo(Integer.parseInt(request.getParameter("no"))));
				} else {
					model.put("no", new Integer(request.getParameter("no")));
					// request.getParameter("no")의 반환값은 String이라서 HashMap 저장형식과 다르므로 new Integer로
					// Object화 시켜주었다.
				}
			} else if ("/member/delete.do".equals(servletPath)) {
				model.put("no", new Integer(request.getParameter("no")));
			} else if ("/auth/login.do".equals(servletPath)) {
				if(request.getParameter("email")!=null) {
					model.put("login", new Member().setEmail(request.getParameter("email")).setPassword(request.getParameter("password")));
				}
			} else if ("/auth/logout.do".equals(servletPath)) {
			}
			
			// pageController 객체에 업무를 위임한다.
			String viewUrl = pageController.excute(model);

			// jsp에 전달할 객체를 request공간에 저장한다.
			for (String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}

			// viewUrl이 'redirect:' 로 시작하면 뒷부분의 주소로 리다이렉트시킨다.
			if (viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				// 아니라면 RequestDispatcher를 이용해서 viewUrl로 처리를 요청한다.
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
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
