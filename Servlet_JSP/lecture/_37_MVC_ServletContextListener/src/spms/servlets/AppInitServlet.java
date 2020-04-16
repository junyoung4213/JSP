package spms.servlets;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

// 클라이언트의 요청에 의해 생성되는 서블릿이 아니라
// 웹어플리케이션이 동작시 생성되어 초기화를 담당
@SuppressWarnings("serial")
public class AppInitServlet extends HttpServlet{
	
	// 웹 어플리케이션 초기화 때
	// 데이터베이스 연결 객체(Connection)을 생성해서
	// ServletContext영역에 저장해 놓는다.
	@Override
	public void init() throws ServletException {
		System.out.println("AppInitServlet 준비...");
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			Connection conn = DriverManager.getConnection(
						sc.getInitParameter("url"),
						sc.getInitParameter("username"),
						sc.getInitParameter("password"));
			
			// ServletContext 영역에 conn 이름으로 저장
			// 이곳에 저장하면 모든 서블릿에서 사용가능하다
			sc.setAttribute("conn", conn);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	public void destroy() {
		System.out.println("AppInitServlet 마무리...");
		super.destroy();
		ServletContext sc = this.getServletContext();
		Connection conn = (Connection)sc.getAttribute("conn");
		try {
			if(conn != null && conn.isClosed() == false) {
				conn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
				
	}
}

















