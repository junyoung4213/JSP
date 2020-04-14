package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/member/update" }, 
		initParams = {
		@WebInitParam(name = "driver", value = "com.mysql.cj.jdbc.Driver"),
		@WebInitParam(name = "url", value = "jdbc:mysql://localhost:3307/studydb?serverTimezone=UTC"),
		@WebInitParam(name = "username", value = "study"), @WebInitParam(name = "password", value = "study") })
public class MemberUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelect = "SELECT mno, email, mname, cre_date FROM members WHERE mno=" + req.getParameter("no");

		try {
			// web.xml에 서블릿 설정에 함께 기록한 init-param 설정값을 가져온다
			Class.forName(this.getInitParameter("driver"));
			conn = DriverManager.getConnection(this.getInitParameter("url"), this.getInitParameter("username"),
					this.getInitParameter("password"));
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			rs.next(); // 처음에는 1행 이전을 가리키니까

			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>회원 정보</title></head>");
			out.println("<body><h1>회원 정보</h1>");
			out.println("<form action='update' method='post'>");
			out.println("번호: <input type='text' name='no' value='" + req.getParameter("no") + "'readonly><br>");
			out.println("이름: <input type='text' name='name' value='" + rs.getString("mname") + "'><br>");
			out.println("이메일: <input type='text' name='email' value='" + rs.getString("email") + "'><br>");
			out.println("가입일: " + rs.getDate("cre_date") + "<br>");
			out.println("<input type='submit' value='저장'>");
			out.println("<input type='button' value='취소' onclick='location.href=\"list\"'>");
			out.println("</form>");
			out.println("</body></html>");

		} catch (Exception e) {
			throw new ServletException();
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Connection conn = null;
		PreparedStatement pstmt = null;
		final String sqlUpdate = "UPDATE members SET email=?, mname=?, mod_date=now() WHERE mno=?";
		try {
			Class.forName(this.getInitParameter("driver"));
			conn = DriverManager.getConnection(this.getInitParameter("url"), this.getInitParameter("username"),
					this.getInitParameter("password"));

			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setString(1, req.getParameter("email"));
			pstmt.setString(2, req.getParameter("name"));
			pstmt.setInt(3, Integer.parseInt(req.getParameter("no")));
			pstmt.executeUpdate();

			resp.sendRedirect("list");
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
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