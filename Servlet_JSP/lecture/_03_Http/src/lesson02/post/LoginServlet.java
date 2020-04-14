package lesson02.post;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 클라이언트(브라우저)에 한글이 포함되면 깨지므로
		// 설정을 해줘야 한다
		// 클라이언트 정보를 UTF-8로 해석을 해
		req.setCharacterEncoding("UTF-8");
		
		// 2. 클라이언트가 보낸 변수값을 추출
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		
		// 3. 클라이언트에 utf-8로 전송하겠다고 설정함
		resp.setContentType("text/html; charset=UTF-8");
		
		// 4. 클라이언트와 연결된 통신 객체를 얻어야 함
		PrintWriter out = resp.getWriter();
		
		// 5. 결과를 전송한다
		out.println("<html><body>");
		if(password.equals("1111")) {
			out.println("<h1>로그인 결과</h1>");
			out.println("<strong>" + id + "</strong>님을 환영합니다.");
		}else {
			out.println("암호가 틀렸습니다!");
		}
		out.println("</body></html>");
		
	}
}
