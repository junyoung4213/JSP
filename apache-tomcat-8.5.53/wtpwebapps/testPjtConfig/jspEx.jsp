<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String adminId;
		String adminPw;

		String imgDir;
		String testServerIP;
		String realServerIP;
	%>

	<%
		adminId = config.getInitParameter("adminId");
		adminPw = config.getInitParameter("adminPw");
	%>

	<p>adminId : <%=adminId%></p>
	<p>adminPw : <%=adminPw%></p>

	<%
		imgDir = getServletContext().getInitParameter("imgDir");
		testServerIP = getServletContext().getInitParameter("testServerIP");
		realServerIP = getServletContext().getInitParameter("realServerIP");
	%>

	<p>imgDir : <%=imgDir%></p>
	<p>testServerIP : <%=testServerIP%></p>
	<p>realServerIP : <%=realServerIP%></p>
</body>
</html>