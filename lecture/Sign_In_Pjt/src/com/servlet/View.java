package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.servlet.dao.DAO;
import com.servlet.dto.DTO;

@WebServlet("/view")
public class View extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		DAO dao = new DAO(this.getServletContext());
		ArrayList<DTO> list = dao.select();

		for (int i = 0; i < list.size(); i++) {
			DTO dto = list.get(i);
			out.println("ID : " + dto.getId() + ", PW : " + dto.getPw() + ", mname : " + dto.getMname() + ", birth_date : " + dto.getBirth_date() + "<br>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
