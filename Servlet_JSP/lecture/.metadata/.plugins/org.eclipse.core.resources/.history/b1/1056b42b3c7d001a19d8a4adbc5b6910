package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 내가 전송하는 데이터를 utf-8로 코드셋을 처리해라
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// GET요청시 url에 포함된 한글을 아래처럼 설정해도 처리되지 않는다
		// 그래서 tomcat의 server.xml에서
		//<Connector URIEncoding="UTF-8" ~/>처리를 해야 한다
		//<Connector URIEncoding="UTF-8" connectionTimeout="20000" port="9999" protocol="HTTP/1.1" redirectPort="8443"/>
		
		
		// 숫자/영문의 코드셋에 영향을 받지 않는다
		// 브라우저에서 utf-8로 보낸 한글은 utf-8로 처리해야 한다	
		req.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		final String sqlInsert = "INSERT INTO members(email,pwd," + "\r\n" +
							"mname,cre_date,mod_date)" + "\r\n" +
							"VALUES(?, ?, ?, NOW(), NOW())";
		
		try {
			ServletContext sc = this.getServletContext();
			//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			stmt = conn.prepareStatement(sqlInsert);
			stmt.setString(1, req.getParameter("email"));
			stmt.setString(2, req.getParameter("password"));
			stmt.setString(3, req.getParameter("name"));
			stmt.executeUpdate();
			
			// 묻지도 따지지도 않고 바로 add -> list로 이동
			resp.sendRedirect("list");
			
			// 위처럼 바로 이동을 하면 아래 부분은 실행되지 않는다
			/*
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원등록결과</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>등록 성공입니다!</p>");
			out.println("</body></html>");
			*/
			
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {
			try {
				if(stmt!=null)
					stmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				if(conn!=null)
					conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}















