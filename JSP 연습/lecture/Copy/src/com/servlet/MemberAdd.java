package com.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.MemberDao;
import com.vo.Member;


@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAdd extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("viewUrl", "/member/MemberForm.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			ServletContext sc = this.getServletContext();
			
//			Connection conn = (Connection)sc.getAttribute("conn");
			
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");
			
//			memberDao.setConnection(conn);
									
			// 3) Query문 준비
			memberDao.insert(new Member().setEmail(request.getParameter("email")).setPassword(request.getParameter("password")).setName(request.getParameter("name")));
			
//			res.addHeader("Refresh", "1; url=list");	// 리프레쉬 방법2. 헤더에 추가시킨다.
			
			request.setAttribute("viewUrl", "redirect:list.do");
		} catch (Exception e) {
			throw new ServletException(e);
			
		} 
		
	}
}
