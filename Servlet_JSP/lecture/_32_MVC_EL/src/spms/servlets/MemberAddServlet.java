package spms.servlets;

import java.io.IOException;
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
public class MemberAddServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher rd = req.getRequestDispatcher
							("/member/MemberForm.jsp");
		rd.forward(req, resp);
		
		/*
		// 내가 전송하는 데이터를 utf-8로 코드셋을 처리해라
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>회원 등록</title></head>");
		out.println("<body><h1>회원 등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("이메일: <input type='text' name='email'><br>");
		out.println("암호: <input type='password' name='password'><br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
		*/
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		final String sqlInsert = "INSERT INTO members(email,pwd," + "\r\n" +
							"mname,cre_date,mod_date)" + "\r\n" +
							"VALUES(?, ?, ?, NOW(), NOW())";
		
		try {
			ServletContext sc = this.getServletContext();
			// ServletContext에 있는 Connection 객체를 사용하겠다
			conn = (Connection)sc.getAttribute("conn");

			stmt = conn.prepareStatement(sqlInsert);
			stmt.setString(1, req.getParameter("email"));
			stmt.setString(2, req.getParameter("password"));
			stmt.setString(3, req.getParameter("name"));
			stmt.executeUpdate();
			
			// 묻지도 따지지도 않고 바로 add -> list로 이동
			resp.sendRedirect("list");		
			
		}catch(Exception e) {
			//throw new ServletException(e);
			e.printStackTrace();
			req.setAttribute("error", e);
			RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
			rd.forward(req, resp);
		}finally {
			try {if(stmt!=null) stmt.close();}
			catch(Exception e) {e.printStackTrace();}
		}
	}
}















