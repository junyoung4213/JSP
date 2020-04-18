package com.servlet;

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

import com.vo.Member;

@SuppressWarnings("serial")
@WebServlet("/member/list")
public class MemberList extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT mno,mname,email,cre_date FROM members";
		try {
			ServletContext sc = this.getServletContext();

			conn = (Connection)sc.getAttribute("conn");
			
			stmt = conn.createStatement();

			// 4) Query 전송
			rs = stmt.executeQuery(sql);

			// 회원이 여러명이므로 Member 객체를 담을 수 있는 ArrayList를 만든다.
			ArrayList<Member> members = new ArrayList<Member>();
	
			// 회원 정보를 ArrayList에 담는다.
			while (rs.next()) {
				members.add(new Member().setNo(rs.getInt(1)).setName(rs.getString(2)).setEmail(rs.getString(3)).setCreatedDate(rs.getDate(4)));
			}
			// request 공유 공간에 저장한다.
			request.setAttribute("members", members);
			
			// RequestDispatcher로 MemberList.jsp에 출력을 위임한다.
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberList.jsp");
			rd.include(request, response);
			/*
			 * jsp로 위임하는 방식 2가지
			 * 1) forward : 제어권을 아예 넘겨준다
			 * 2) include : 실행이 끝나면 제어권을 넘겨받는다.
			 */
			
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
}
