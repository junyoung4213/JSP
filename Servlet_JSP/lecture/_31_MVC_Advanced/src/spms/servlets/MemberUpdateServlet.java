package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		final String sqlSelect = "SELECT mno,email,mname,cre_date" + "\r\n" + "FROM members WHERE mno=" + "\r\n"
				+ req.getParameter("no");

		try {
			// web.xml에서 모든 서블릿이 공유가능한
			// 설정 값을 가져온다.
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			/*
			 * // 현재 서블릿에서만 사용하는 초기화 매개변수 Class.forName(this.getInitParameter("driver"));
			 * conn = DriverManager.getConnection( this.getInitParameter("url"),
			 * this.getInitParameter("username"), this.getInitParameter("password") );
			 */
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			rs.next(); // 처음에는 1행 이전을 가리키니까

			// 이 ArrayList객체를 jsp에서 사용할 수 있도록 공유
			ArrayList<Member> members = new ArrayList<Member>();
			
			// db에서 꺼내서 member 객체에 담음.
			
				members.add(new Member().setNo(rs.getInt("mno")).setName(rs.getString("mname"))
						.setEmail(rs.getString("email")).setCreatedDate(rs.getDate("cre_date")));
			

			// jsp에 전달하기 위해 request의 공유공간에 저장한다
			req.setAttribute("members", members);

			RequestDispatcher rd = req.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.forward(req, resp);

		} catch (Exception e) {
			req.setAttribute("error", e);
			
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
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
//			try {
//				if(conn!=null) 
//					conn.close();
//			}catch(Exception e) {
//				e.printStackTrace();
//			}			
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Filter로 옮김
		// req.setCharacterEncoding("UTF-8");
		Connection conn = null;
		PreparedStatement stmt = null;
		final String sqlUpdate = "UPDATE members SET email=?," + "\r\n" + "mname=?,mod_date=now() WHERE mno=?";
		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			/*
			 * Class.forName(this.getInitParameter("driver")); conn =
			 * DriverManager.getConnection( this.getInitParameter("url"),
			 * this.getInitParameter("username"), this.getInitParameter("password") );
			 */
			stmt = conn.prepareStatement(sqlUpdate);
			stmt.setString(1, req.getParameter("email"));
			stmt.setString(2, req.getParameter("name"));
			stmt.setInt(3, Integer.parseInt(req.getParameter("no")));
			stmt.executeUpdate();

			resp.sendRedirect("list");

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
//			try {
//				if(conn!=null) 
//					conn.close();
//			}catch(Exception e) {
//				e.printStackTrace();
//			}				
		}
	}
}
