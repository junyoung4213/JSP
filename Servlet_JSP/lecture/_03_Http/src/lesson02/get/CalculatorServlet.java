package lesson02.get;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 클라이언트(브라우저)가 아래 주소로 요청하면 이 클래스가 담당한다
@SuppressWarnings("serial")
@WebServlet("/CalculatorServlet")
public class CalculatorServlet extends HttpServlet {
	private Hashtable<String, Operator> operatorTable = new Hashtable<String, Operator>();

	public CalculatorServlet() {
		operatorTable.put("+", new PlusOperator());
		operatorTable.put("-", new MinusOperator());
		operatorTable.put("*", new MultipleOperator());
		operatorTable.put("/", new DivideOperator());
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 클라이언트가 보낸 변수값을 추출한다
		String op = req.getParameter("op");
		double v1 = Double.parseDouble(req.getParameter("v1"));
		double v2 = Double.parseDouble(req.getParameter("v2"));

		// 2. 우리가 보내는 데이터는 utf-8로 해석해
		resp.setContentType("text/html; charset=UTF-8");

		// 3. 클라이언트와 연결된 스트림을 포함하고 있는 연결 객체 추출

		PrintWriter out = resp.getWriter();

		// 4. 클라이언트한테 데이터 전송
		out.println("<html><body>");
		out.println("<h1>계산결과</h2>");
		out.println("결과: ");

		// 4.1 결과값도 계산해서 전송
		try {
			// op 연산자(명령)를 통해 담당 객체를 추출
			Operator operator = operatorTable.get(op);
			if (operator == null) {
				out.println("존재하지 않는 연산자 입니다.");
			} else {
				double result = operator.execute(v1, v2);
				out.println(String.format("%.3f", result));
			}
		} catch (Exception e) {
			out.println("연산 오류가 발생하였습니다.");

		}

		out.println("</body></html>");
	}

	// GET 요청일 때
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("GET 요청");
		process(req,resp);
	}

	// POST 요청일 때
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("POST 요청");
		process(req,resp);
	}

}
