package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.MemberDao;
import com.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberList extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		try {
			ServletContext sc = this.getServletContext();			
//			Connection conn = (Connection)sc.getAttribute("conn");
			
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
//			memberDao.setConnection(conn);
			
			List<Member> members = memberDao.selectList();

			// request 공유 공간에 저장한다.
			request.setAttribute("members", members);
			
			// 어느 URL로 보낼 것인지 request 공유 공간에 저장한다.
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
			
		} catch (Exception e) {
			throw new ServletException(e);
		} 
	}
}
