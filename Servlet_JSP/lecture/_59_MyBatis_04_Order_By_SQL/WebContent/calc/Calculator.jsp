<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// java 코드 영역
	String v1 = "";
	String v2 = "";
	String result = "";
	String[] selected = { "", "", "", "" };

	// request같은 객체를 "jsp 내장 객체"라고 한다.
	// 값이 있을 때만 꺼내자
	// 값이 있다는 얘기는 계산 요청이 들어온 상태
	if (request.getParameter("v1") != null) {
		v1 = request.getParameter("v1");
		v2 = request.getParameter("v2");
		String op = request.getParameter("op");

		result = calculate(Integer.parseInt(v1), Integer.parseInt(v2), op);

		switch (op) {
		case "+":
			selected[0] = "selected";
			break;
		case "-":
			selected[1] = "selected";
			break;
		case "*":
			selected[2] = "selected";
			break;
		case "/":
			selected[3] = "selected";
			break;
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계산기</title>
</head>
<!-- 계산 요청이 들어왔을 때 처리하는 코드를 추가 -->
<!-- jsp 파일은 Servlet이다. 그러므로 jsp에서 java코드를 사용할 수 있다. -->

<!-- html코드내에서 java의 객체를 접근하려고 하면 표현식을 사용한다. 
	 
	 html코드는 _jspService()내에서 out.println("~"); 로 변환된다.
-->

<body>
	<h2>JSP 계산기</h2>
	<form action="Calculator.jsp" method="get">
		<input type="text" name="v1" size="4" value="<%=v1%>" />
		<select name="op" id="">
			<option value="+" <%=selected[0]%>>+</option>
			<option value="-" <%=selected[1]%>>-</option>
			<option value="*" <%=selected[2]%>>*</option>
			<option value="/" <%=selected[3]%>>/</option>
		</select>
		<input type="text" name="v2" size="4" value="<%=v2%>" />
		<input type="submit" value="=" />
		<input type="text" size="8" value="<%=result%>" />
		<br>
	</form>
</body>
</html>

<%!// jsp 선언문
	// _jspService() 바깥의 클래스 내에 메서드로 위치한다.

	// jsp내에서 사용할 메서드를 추가할 때 이렇게 한다
	// 위치는 관계 없다
	private String calculate(int a, int b, String op) {
		int result = 0;
		switch (op) {
		case "+":
			result = a + b;
			break;
		case "-":
			result = a - b;
			break;
		case "*":
			result = a * b;
			break;
		case "/":
			result = a / b;
			break;
		}
		return Integer.toString(result);
	}%>