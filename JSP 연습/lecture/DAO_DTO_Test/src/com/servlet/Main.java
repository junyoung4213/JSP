package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DAO;
import com.dto.DTO;


@WebServlet("/Main")
public class Main extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		
		ArrayList<DTO> list = new ArrayList<DTO>();
		
		DAO dao = new DAO(this.getServletContext());
		
		list = dao.select();
		
		PrintWriter out = response.getWriter();
		
		for (DTO dto:list) {
			out.println("email : " + dto.getEmail() + ", name : " +  dto.getMname() +  ", pwd : " + dto.getPwd()+"<br>");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
