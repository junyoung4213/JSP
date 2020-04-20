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

import com.bind.DataBinding;
import com.bind.ServletRequestDataBinder;
import com.context.ApplicationContext;
import com.controls.Controller;
import com.listeners.ContextLoaderListener;
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

		// page Controller에 분기 전송을 위해 경로를 얻는다
		String servletPath = request.getServletPath();

		try {

			ApplicationContext ctx = ContextLoaderListener.getApplicationContext();

			Controller pageController = (Controller) ctx.getBean(servletPath);

			if (pageController == null) {
				throw new Exception("요청한 서비스를 찾을 수 없습니다");
			}

			// pageController에게 전달할 Map 객체를 준비한다
			HashMap<String, Object> model = new HashMap<String, Object>();

			// model에 session 객체를 저장해준다(로그인&로그아웃때 사용할 것)
			model.put("session", request.getSession());

			if (pageController instanceof DataBinding) {
				/*
				 * request : 클라이언트의 매개변수를 추출 model : vo객체를 저장 pageController : 준비해야 할 vo객체 정보 제공
				 */
				prepareRequestData(request, model, (DataBinding) pageController);
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

	public void prepareRequestData(HttpServletRequest request, HashMap<String, Object> model, DataBinding databinding)
			throws Exception {
		// 생성해야 할 vo 객체 정보를 얻는다
		Object[] dataBinders = databinding.getDataBinders();
		String dataName = null; // key 이름
		Class<?> dataType = null; // 생성할 클래스 정보
		Object dataObj = null; // 생성한 객체

		for (int i = 0; i < dataBinders.length; i += 2) {
			dataName = (String) dataBinders[i];
			dataType = (Class<?>) dataBinders[i + 1];
			/*
			 * request : 매개변수 추출 dataType : 객체를 생성할 클래스 타입 dataName : 매개변수 이름
			 */
			// 객체를 생성하자
			dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
			// 만들어진 객체를 model에 저장하자
			model.put(dataName, dataObj);
		}

	}
}
