package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.context.ApplicationContext;
import spms.controls.Controller;
import spms.listeners.ContextLoaderListener;

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
			ApplicationContext ctx = 
					ContextLoaderListener.getApplicationContext();
			
			Controller pageController = 
					(Controller)ctx.getBean(servletPath);
			if(pageController == null) {
				throw new Exception("요청한 서비스를 찾을 수 없습니다");
			}
			
			// pageController에게 전달한 Map객체를 준비한다
			HashMap<String, Object> model = 
					new HashMap<String, Object>();
			model.put("session", request.getSession());
			
			/* 이곳에서 pageController가 사용할 vo객체를 자동으로
			 * 생성한 후, 클라이언트가 보내온 변수를 저장한다
			 */
			
			// 준비해야 할 vo객체가 존재한다면
			if(pageController instanceof DataBinding) {
				/*
				 * request : 클라이언트의 매개변수를 추출
				 * model : vo객체를 저장
				 * pageController : 준비해야 할 vo객체 정보 제공
				 * */
				prepareRequestData(request, model, 
						(DataBinding)pageController);
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
	
	private void prepareRequestData(
					HttpServletRequest request,
					HashMap<String, Object> model,
					DataBinding dataBinding) 
					throws Exception{
		// 생성해야 할 vo객체 정보를 얻는다
		Object[] dataBinders = dataBinding.getDataBinders();
		String dataName = null;		// key 이름
		Class<?> dataType = null;	// 생성할 클래스 정보
		Object dataObj = null;		// 생성한 객체
		for(int i=0;i<dataBinders.length;i+=2) {
			dataName = (String)dataBinders[i];
			dataType = (Class<?>)dataBinders[i+1];
			/* request : 매개변수 추출
			 * dataType : 객체를 생성할 클래스 타입
			 * dataName : 매개변수 이름
			 * */
			// 객체를 생성하자
			dataObj = ServletRequestDataBinder.bind(
						request, dataType, dataName);
			// 만들어진 객체를 model에 저장하자
			model.put(dataName, dataObj);
		}
	}
}














