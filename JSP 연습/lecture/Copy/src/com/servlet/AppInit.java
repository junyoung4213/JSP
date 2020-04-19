package com.servlet;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/*
 * 서블릿 객체가 생성될 때 제일 먼저 실행되는 init() 메서드를 이용해서 Connection을 미리 셋팅한다.
 * web.xml에서 제일 먼저 이 클래스가 실행되도록 설정해주어야한다.
*/
@SuppressWarnings("serial")
public class AppInit extends HttpServlet {

	@Override
	public void init() throws ServletException {

		System.out.println("AppInitServlet 준비...");
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			Connection conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"),
					sc.getInitParameter("password"));

			sc.setAttribute("conn", conn);

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	@Override
	public void destroy() {
		System.out.println("AppInitServlet 마무리...");
		super.destroy();
		ServletContext sc = this.getServletContext();
		Connection conn = (Connection) sc.getAttribute("conn");
		try {

			if (conn != null && !conn.isClosed()) {
				conn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
