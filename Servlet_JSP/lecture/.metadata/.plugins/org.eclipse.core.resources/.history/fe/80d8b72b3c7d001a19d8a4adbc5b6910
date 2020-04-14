package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelect = "SELECT mno,email,mname,cre_date" + "\r\n" +
								"FROM members WHERE mno=" + "\r\n" +
								req.getParameter("no");
		
		try {
			// web.xml에서 모든 서블릿이 공유가능한
			// 설정 값을 가져온다.
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password")
					);
			/*
			 // 현재 서블릿에서만 사용하는 초기화 매개변수
			Class.forName(this.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					this.getInitParameter("url"),
					this.getInitParameter("username"),
					this.getInitParameter("password")
					);
			*/
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			rs.next();	// 처음에는 1행 이전을 가리키니까
			
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원 정보</title></head>");
			out.println("<body><h1>회원 정보</h1>");
			out.println("<form action='update' method='post'>");
			out.println("번호: <input type='text' name='no' value='" +
					req.getParameter("no") + "'readonly><br>");
			out.println("이름: <input type='text' name='name'" +
					" value='" + rs.getString("mname") + "'><br>");
			out.println("이메일: <input type='text' name='email'" +
					" value='" + rs.getString("email") + "'><br>");
			out.println("가입일: " + rs.getDate("cre_date") + "<br>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='삭제' "
					+ "onclick='location.href=\"delete?no=" + 
					req.getParameter("no") + "\";'>");			
			out.println("<input type='button' value='취소'" +
					" onclick='location.href=\"list\"'>");
			out.println("</form>");
			out.println("</body></html>");
			
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {
			try {
				if(rs!=null) 
					rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Connection conn = null;
		PreparedStatement stmt = null;
		final String sqlUpdate = "UPDATE members SET email=?," + "\r\n" +
								"mname=?,mod_date=now() WHERE mno=?";
		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password")
					);
			/*
			Class.forName(this.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					this.getInitParameter("url"),
					this.getInitParameter("username"),
					this.getInitParameter("password")
					);
			*/
			stmt = conn.prepareStatement(sqlUpdate);
			stmt.setString(1, req.getParameter("email"));
			stmt.setString(2, req.getParameter("name"));
			stmt.setInt(3, Integer.parseInt(req.getParameter("no")));
			stmt.executeUpdate();
			
			resp.sendRedirect("list");
			
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














