package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
@WebServlet("/member/add")
public class MemberAdd extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		final String sql = "INSERT INTO members(email,pwd,mname,cre_date,mod_date) VALUES(?,?,?,now(),now())";
		
		try {
			ServletContext sc = this.getServletContext();
			
			conn = (Connection)sc.getAttribute("conn");
			// 3) Query문 준비
			pstmt=conn.prepareStatement(sql);

			pstmt.setString(1, request.getParameter("email"));
			pstmt.setString(2, request.getParameter("password"));
			pstmt.setString(3, request.getParameter("name"));
			
			
			pstmt.execute();
			
//			res.addHeader("Refresh", "1; url=list");	// 리프레쉬 방법2. 헤더에 추가시킨다.
			response.sendRedirect("list");
		} catch (Exception e) {
//			throw new ServletException(e);
			request.setAttribute("error", e);
			
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} finally {
			try {
				if(pstmt!=null) pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
}
