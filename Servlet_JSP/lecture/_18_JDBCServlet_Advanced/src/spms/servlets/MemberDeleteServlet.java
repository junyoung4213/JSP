package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// utf-8로 코드셋을 처리해라
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8"); // 이걸 써줘야 DB상에서 한글이 외계어로 안나온다.

		Connection conn = null;
		Statement stmt = null;

		try {
			ServletContext sc = this.getServletContext();

			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"),
					sc.getInitParameter("password"));
			stmt= conn.createStatement();
			stmt.execute("DELETE FROM members WHERE mno="+req.getParameter("no"));

			// 바로 add -> list로 이동
			resp.sendRedirect("list");

			// 위처럼 바로 이동을 하면 아래 부분은 실행되지 않는다.
			/*
			 * resp.setContentType("text/html; charset=UTF-8"); PrintWriter out =
			 * resp.getWriter(); out.println("<html><head><title>회원등록결과</title>");
			 * out.println("</head>"); out.println("<body>");
			 * out.println("<p>등록 성공입니다!</p>"); out.println("</body></html>");
			 */
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}
