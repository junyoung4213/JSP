package lesson01.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet("/calc")
public class CalculatorServlet extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// 클라이언트로부터 변수값을 추출하자
		String op = req.getParameter("op");
		int v1 = Integer.parseInt(req.getParameter("v1"));
		int v2 = Integer.parseInt(req.getParameter("v2"));
		int result = 0;

		res.setContentType("text/html;charset=UTF-8");
		PrintWriter out = res.getWriter(); // 스트림 연결

		switch (op) {
		case "+":
			result = v1 + v2;
			break;
		case "-":
			result = v1 - v2;
			break;
		case "*":
			result = v1 * v2;
			break;
		case "/":
			if (v2 == 0) {
				out.println("0으로 나눌 수 없습니다!");
				return;
			}
			result = v1 / v2;
			break;
		}
		// 클라이언트에 결과 전송
		out.println(v1+" " + op + " "+ v2 + " = " + result);

	}

}
