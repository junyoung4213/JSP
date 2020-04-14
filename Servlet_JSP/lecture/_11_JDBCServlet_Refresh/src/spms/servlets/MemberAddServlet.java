package spms.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
		// utf-8로 코드셋을 처리해라
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// GET요청시 url에 포함된 한글을 아래처럼 설정해도 처리되지 않는다
		// 그래서 tomcat의 server.xml에서 
		// <connector URIEncoding="UTF-8" ~/>처리를 해야한다.
		// <Connector URIEndoing="UTF-8" connectionTimeout="2000">
		
		
		
		// 숫자/영문의 코드셋에 영향을 받지 않는다
		// 브라우저에서 utf-8로 보낸 한글은 utf-8로 처리해야 한다
		req.setCharacterEncoding("UTF-8"); // 이걸 써줘야 DB상에서 한글이 외계어로 안나온다.
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		final String sqlInsert = "INSERT INTO members(email,pwd,mname,cre_date,mod_date) VALUES(?,?,?,NOW(),NOW())"; // NOW는 현재시간
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/studydb?serverTimezone=UTC","study","study");
			pstmt=conn.prepareStatement(sqlInsert);
			pstmt.setString(1, req.getParameter("email"));
			pstmt.setString(2, req.getParameter("password"));
			pstmt.setString(3, req.getParameter("name"));
			pstmt.executeUpdate();
			
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원등록결과</title>");
			// 1초후에 add -> list로 주소 변환해서 다시 보여줘라
			out.println("<meta http-equiv='Refresh' content='1; url=list'>");
			out.println("</head>");
			out.println("<body>");
			out.println("<p>등록 성공입니다!</p>");
			out.println("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if(pstmt!=null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(conn!=null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
