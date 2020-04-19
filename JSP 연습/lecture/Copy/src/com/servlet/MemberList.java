package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
			
			// RequestDispatcher로 MemberList.jsp에 출력을 위임한다.
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
			rd.include(request, response);
			/*
			 * jsp로 위임하는 방식 2가지
			 * 1) forward : 제어권을 아예 넘겨준다
			 * 2) include : 실행이 끝나면 제어권을 넘겨받는다.
			 */
			
		} catch (Exception e) {
//			throw new ServletException(e);
			request.setAttribute("error", e);
			
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
			
		} 
	}
}
