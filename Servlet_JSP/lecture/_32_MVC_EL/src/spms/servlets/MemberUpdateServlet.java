package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
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

import spms.vo.Member;

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
			ServletContext sc = this.getServletContext();
			conn = (Connection)sc.getAttribute("conn");

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			if(rs.next()) {
				req.setAttribute("member", new Member()
									.setNo(rs.getInt("mno"))
									.setEmail(rs.getString("email"))
									.setName(rs.getString("mname"))
									.setCreatedDate(rs.getDate("cre_date")));
			}else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다");
			}

			RequestDispatcher rd = req.getRequestDispatcher
							("/member/MemberUpdateForm.jsp");
			rd.forward(req, resp);
			
		}catch(Exception e) {
			throw new ServletException(e);
		}finally {
			try {if(rs!=null) rs.close();}
			catch(Exception e) {e.printStackTrace();}
			try {if(stmt!=null) stmt.close();}
			catch(Exception e) {e.printStackTrace();}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Filter로 옮김
		//req.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		final String sqlUpdate = "UPDATE members SET email=?," + "\r\n" +
								"mname=?,mod_date=now() WHERE mno=?";
		try {
			ServletContext sc = this.getServletContext();
			
			// ServletContext에 있는 Connection 객체를 사용하겠다
			conn = (Connection)sc.getAttribute("conn");
			stmt = conn.prepareStatement(sqlUpdate);
			stmt.setString(1, req.getParameter("email"));
			stmt.setString(2, req.getParameter("name"));
			stmt.setInt(3, Integer.parseInt(req.getParameter("no")));
			stmt.executeUpdate();
			
			resp.sendRedirect("list");
			
		}catch(Exception e) {
			//throw new ServletException(e);
			e.printStackTrace();
			req.setAttribute("error", e);
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
		}finally {
			try {
				if(stmt!=null) 
					stmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}			
		}
	}
}














