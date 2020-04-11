package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/su")
public class SignUp extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html;charset=utf-8");
		System.out.println("--- do get ---");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		System.out.println("id = " + id);
		System.out.println("pw = " + pw);
		
		PrintWriter out = response.getWriter();
		out.println("당신의 아이디는: " + id);
		out.println("당신의 비밀번호는: " + pw);

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--- do Post ---");
		doGet(request, response);
	}

}
