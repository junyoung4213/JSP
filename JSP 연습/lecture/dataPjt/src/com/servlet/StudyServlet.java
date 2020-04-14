package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.servlet.dao.StudyDAO;
import com.servlet.dto.StudyDTO;


@WebServlet("/st")
public class StudyServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		
		StudyDAO studyDAO = new StudyDAO();
		ArrayList<StudyDTO> list = studyDAO.select();
		
		for (int i = 0; i < list.size(); i++) {
			StudyDTO dto = list.get(i);
			String email=dto.getEmail();
			String pwd=dto.getPwd();
			String mname=dto.getMname();
			
			out.println("email : " + email + ", ");
			out.println("pwd : " + pwd + ", ");
			out.println("mname : " + mname + "<br>");
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
