package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null; // MySQL 연결담당
		Statement stmt = null; // SQL문 담당
		ResultSet rs = null; // SELECT문의 결과 담당

		final String sqlSelect = "SELECT mno,mname,email,cre_date" + "\r\n" + "FROM members" + "\r\n"
				+ "ORDER BY mno ASC";

		try {
			ServletContext sc = this.getServletContext();
			// DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);

			resp.setContentType("text/html; charset=UTF-8"); // 먼저 호출

			// 회원이 여러명이니까 ArrayList에 회원전체를 담자
			// 이 ArrayList객체를 jsp에서 사용할 수 있도록 공유하자
			ArrayList<Member> members = new ArrayList<Member>();

			// db에서 꺼내서 member 객체에 담자
			while (rs.next()) {
				members.add(new Member().setNo(rs.getInt("mno")).setName(rs.getString("mname"))
						.setEmail(rs.getString("email")).setCreatedDate(rs.getDate("cre_date")));
			}

			// jsp에 전달하기 위해 request의 공유공간에 저장한다
			req.setAttribute("members", members);

			// JSP로 출력을 위임한다
			RequestDispatcher rd = req.getRequestDispatcher("/member/MemberList.jsp");
			rd.include(req, resp);
			/*
			 * jsp로 위임하는 방식 2가지
			 * 1) forward : 제어권을 아예 넘겨준다
			 * 2) include : 실행이 끝나면 제어권을 넘겨받는다.
			 */
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
