package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.controls.Controller;
import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.vo.Member;

/* Design Pattern중에 Front Controller Pattern
 * DispatcherServlet이 직접적으로 tomcat에 의해 호출되고
 * 이곳에서 다른 Page Controller로 전송하는 역할을 한다
 * 
 * DispatcherServlet은 Servlet 클래스로 제작하고
 * 나머지 Page Controller 는 일반 POJO(Plain Old Java Object)
 * 클래스로 만든다.
 * 
 * 1. 기존의 pageController의 공통 기능을 이곳에서 처리
 *   1.1 전송 파라미터 추출 후 vo객체 생성
 *   1.2 페이지 이동(redirect, include)
 *   1.3 pageController에서 발생하는 예외처리
 *   
 * 2. 다른 WAS에 이식할 때 [프런트 컨트롤러]만 수정하면 된다
 * 3. 클라이언트한테 원하지 않는 페이지를 노출하지 않아도 된다.
 * */

@SuppressWarnings("serial")
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		// page Controller에 분기 전송을 위해 경로를 얻는다
		String servletPath = request.getServletPath();
		
		try {
			Controller pageController = null;
			
			ServletContext sc = this.getServletContext();
			
			// pageController에게 전달한 Map객체를 준비한다
			HashMap<String, Object> model = 
					new HashMap<String, Object>();
			model.put("memberDao", sc.getAttribute("memberDao"));
			model.put("session", request.getSession());
			
			if("/member/list.do".equals(servletPath)) {
				pageController = new MemberListController();
			}else if("/member/add.do".equals(servletPath)) {
				pageController = new MemberAddController();
				if(request.getParameter("email")!=null) {
					model.put("member", new Member()
						.setEmail(request.getParameter("email"))
						.setPassword(request.getParameter("password"))
						.setName(request.getParameter("name")));
				}
			}else if("/member/update.do".equals(servletPath)) {
				pageController = new MemberUpdateController();
		        if (request.getParameter("email") != null) {
		          model.put("member", new Member()
		            .setNo(Integer.parseInt(request.getParameter("no")))
		            .setEmail(request.getParameter("email"))
		            .setName(request.getParameter("name")));
		        } else {
		          model.put("no", new Integer(request.getParameter("no")));
		        }
			}else if("/member/delete.do".equals(servletPath)) {
		        pageController = new MemberDeleteController();
		        model.put("no", new Integer(request.getParameter("no")));
			}else if("/auth/login.do".equals(servletPath)) {
		        pageController = new LogInController();
		        if (request.getParameter("email") != null) {
		          model.put("loginInfo", new Member()
		            .setEmail(request.getParameter("email"))
		            .setPassword(request.getParameter("password")));
		        }
			}else if("/auth/logout.do".equals(servletPath)) {
		        pageController = new LogOutController();
			}
			
			// pageController객체에 업무 위임
			String viewUrl = pageController.execute(model);
			
			// jsp에 전달할 객체를 request공간에 저장
			for(String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}
			
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}else {
				RequestDispatcher rd = 
						request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
	
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = 
					request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}














