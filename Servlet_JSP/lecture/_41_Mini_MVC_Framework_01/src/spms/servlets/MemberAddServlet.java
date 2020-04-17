package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("viewUrl", "/member/MemberForm.jsp");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ServletContext sc = this.getServletContext();
			// ServletContext에 있는 Connection 객체를 사용하겠다
			MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
			
			Member member = (Member)request.getAttribute("member");
			memberDao.insert(member);
			
			request.setAttribute("viewUrl", "redirect:list.do");
			
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}
}















