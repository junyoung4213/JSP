package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.servlet.dao.DAO;
import com.servlet.dto.DTO;

@WebServlet("/signin")
public class SignIn extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		DAO dao = new DAO(this.getServletContext());

		
		DTO dto = new DTO(request.getParameter("id"), request.getParameter("pw"), request.getParameter("mname"), request.getParameter("birth_date"));

		if(dao.add(dto)<=0) {
			out.println("회원가입에 실패하셨습니다!");
		}else{
			out.println("회원가입에 성공하셨습니다!");
		};
		
//		RequestDispatcher rd = request.getRequestDispatcher("view");
//		rd.forward(request, response);
// 		위 방식은 request와 response가 유지된다.
		
		response.sendRedirect("view");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
