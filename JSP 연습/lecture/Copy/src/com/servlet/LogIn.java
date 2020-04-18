package com.servlet;

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

import com.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/auth/login")
public class LogIn extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInForm.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String sqlSelect = "SELECT mname,email FROM members WHERE email=? AND pwd=?";

		try {
			ServletContext sc = this.getServletContext();
			Connection conn = (Connection) sc.getAttribute("conn");

			pstmt = conn.prepareStatement(sqlSelect);

			pstmt.setString(1, request.getParameter("email"));
			pstmt.setString(2, request.getParameter("password"));

			rs = pstmt.executeQuery();

			if (rs.next()) {

				Member member = new Member().setName(rs.getString("mname")).setEmail(rs.getString("email"));

				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				response.sendRedirect("../member/list");
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/auth/LogInFail.jsp");
				rd.forward(request, response);
			}

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
