package com.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.MySqlMemberDao;
import com.vo.Member;

/* <어노테이션으로 initparam 등록하는 방법>
 * @WebServlet(urlPatterns = { "/member/update" }, initParams = {
 * 
 * @WebInitParam(name = "driver", value = "com.mysql.cj.jdbc.Driver"),
 * 
 * @WebInitParam(name = "url", value =
 * "jdbc:mysql://localhost:3307/studydb?serverTimezone=UTC"),
 * 
 * @WebInitParam(name = "username", value = "study"), @WebInitParam(name =
 * "password", value = "study") })
 */
@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdate extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ServletContext sc = this.getServletContext();

//			Connection conn = (Connection) sc.getAttribute("conn");

			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");

//			memberDao.setConnection(conn);

			Member member = memberDao.selectOne(Integer.parseInt(request.getParameter("no")));

			request.setAttribute("member", member);

			request.setAttribute("viewUrl", "/member/MemberUpdateForm.jsp");

		} catch (Exception e) {
			throw new ServletException(e);

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ServletContext sc = this.getServletContext();
//			Connection conn = (Connection) sc.getAttribute("conn");

			MySqlMemberDao memberDao = (MySqlMemberDao) sc.getAttribute("memberDao");
//			memberDao.setConnection(conn);

			memberDao.update(new Member().setEmail(request.getParameter("email")).setName(request.getParameter("name"))
					.setNo(Integer.parseInt(request.getParameter("no"))));

			request.setAttribute("viewUrl", "redirect:list.do");

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

}
