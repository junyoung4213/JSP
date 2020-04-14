package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet{

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		
		Connection conn = null;	// MySQL 연결담당
		Statement stmt = null;	// SQL문 담당
		ResultSet rs = null;	// SELECT문의 결과 담당
		
		final String sqlSelect = "SELECT mno,mname,email,cre_date" + "\r\n" +
								"FROM members" + "\r\n" +
								"ORDER BY mno ASC";
//		final String sqlSelect = "SELECT mno,mname,email,cre_date" +
//								" FROM members" + 
//								" ORDER BY mno ASC";
//		final String sqlSelect = "SELECT mno,mname,email,cre_date FROM members ORDER BY mno ASC";
		
		try {
			// 1) MySQL 제어 객체를 로딩
//			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			// 2) MySQL과 연결
			conn = DriverManager.getConnection(
//					"jdbc:mysql://localhost/studydb", // MySQL URL
					"jdbc:mysql://localhost:3307/studydb?serverTimezone=UTC", // 5.1.x 이후
					"study",						  // id
					"study");						  // password
			// 3) SQL문 객체 생성
			stmt = conn.createStatement();
			// 4) Query 전송
			rs = stmt.executeQuery(sqlSelect);
			
			// 5) 결과를 클라이언트에 전송한다
			res.setContentType("text/html; charset=UTF-8"); // 먼저 호출
			PrintWriter out = res.getWriter();				// 나중 호출
			out.println("<html><head><title>회원 목록</title></head>");
			out.println("<body><h1>회원 목록</h1>");
			while(rs.next()) {
				out.println(
						rs.getInt(1) + ", " + 		
						rs.getString(2) + ", " +
						rs.getString(3) + ", " +
						rs.getDate(4) + "<br>"
					);
			}
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
}






