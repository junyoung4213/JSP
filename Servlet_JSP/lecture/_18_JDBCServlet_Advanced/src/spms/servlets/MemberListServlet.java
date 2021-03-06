package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Connection conn = null; // MySQL 연결담당
		Statement stmt = null; // SQL문 담당
		ResultSet rs = null; // SELECT문의 결과 담당

		final String sqlSelect = "SELECT mno,mname,email,cre_date" + "\r\n" + "FROM members" + "\r\n"
				+ "ORDER BY mno ASC";

		try {
			// 1) MySQL 제어 객체를 로딩
			ServletContext sc = this.getServletContext();

			Class.forName(sc.getInitParameter("driver"));
			// 2) MySQL과 연결
			conn = DriverManager.getConnection(

					sc.getInitParameter("url"), // 5.1.x 이후
					sc.getInitParameter("username"), // id
					sc.getInitParameter("password")); // password

			stmt = conn.createStatement();
			// 4) Query 전송
			rs = stmt.executeQuery(sqlSelect);

			// 5) 결과를 클라이언트에 전송한다
			res.setContentType("text/html; charset=UTF-8"); // 먼저 호출
			PrintWriter out = res.getWriter(); // 나중 호출
			out.println("<html><head><title>회원 목록</title></head>");
			out.println("<body><h1>회원 목록</h1>");

			out.println("<p><a href='add'>신규 회원</a></p>");
//			while (rs.next()) {
//				out.println(
//						rs.getInt(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getDate(4) + "<br>");
//			}
			// mname에 list->update로 이동하는 링크를 설정한다
			// 링크이동은 GET요청에 해당되고 매개변수는 no를 가지고 간다.
			// 즉, /member/update에 GET요청을 하는 것이다.
			while (rs.next()) {
				out.println(rs.getInt("mno") + ", " + "<a href='update?no=" + rs.getInt("mno") + "'>"
						+ rs.getString("mname") + "</a>, " + rs.getString("email") + ", " + rs.getDate("cre_date")
						+ "<a href='delete?no=" + rs.getInt("MNO") + "'>[삭제]</a><br>");
			}
			out.println("</body></html>");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
