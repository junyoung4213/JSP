package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spms.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 경로로 들어왔을 때 로그인 화면을 보여주자
		RequestDispatcher rd = req.getRequestDispatcher("/auth/LogInForm.jsp");
		rd.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String sqlLogIn = "SELECT mname,email FROM members WHERE email=? AND pwd=?";

		try {
			ServletContext sc = this.getServletContext();
			conn = (Connection) sc.getAttribute("conn");
			pstmt = conn.prepareStatement(sqlLogIn);
			pstmt.setString(1, req.getParameter("email"));
			pstmt.setString(2, req.getParameter("password"));
			rs = pstmt.executeQuery();
			// 로그인 성공
			if (rs.next()) {
				// vo객체에 로그인 정보를 저장한다
				Member member = new Member().setEmail("email").setName("name");
				// HttpSession 객체 접근
				HttpSession session = req.getSession();
				session.setAttribute("member", member);

				resp.sendRedirect("../member/list");
			}
			// 로그인 실패
			else {
				RequestDispatcher rd = req.getRequestDispatcher("/auth/LogInFail.jsp");
				rd.forward(req, resp);
			}

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
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
