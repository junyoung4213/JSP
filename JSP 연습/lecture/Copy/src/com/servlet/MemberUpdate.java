package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vo.Member;

/* <어노테이션으로 initparam 등록하는 방법>
 * @WebServlet(urlPatterns = { "/member/update" }, initParams = {
 * 
 * @WebInitParam(name = "driver", value = "com.mysql.cj.jdbc.Driver"),
 * 
 * @WebInitParam(name = "url", value =
 * "jdbc:mysql://localhost:3307/studydb?serverTimezone=UTC"),
 * 
 * @WebInitParam(name = "username", value = "study"), @WebInitParam(name =
 * "password", value = "study") })
 */
@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdate extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelect = "SELECT mno, email, mname, cre_date FROM members WHERE mno="
				+ request.getParameter("no");
		try {
			ServletContext sc = this.getServletContext();

			conn = (Connection) sc.getAttribute("conn");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);

			if (rs.next()) {

				Member member = new Member().setNo(rs.getInt(1)).setEmail(rs.getString(2)).setName(rs.getString(3))
						.setCreatedDate(rs.getDate(4));

				request.setAttribute("member", member);
			} else {
				throw new Exception("해당 회원을 찾을 수 없습니다");
			}
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.include(request, response);

		} catch (Exception e) {
//			throw new ServletException(e);
			request.setAttribute("error", e);

			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		final String sqlUpdate = "UPDATE members SET email=?, mname=?, mod_date=now() WHERE mno=?";

		try {
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"),
					sc.getInitParameter("password"));
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
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

}
