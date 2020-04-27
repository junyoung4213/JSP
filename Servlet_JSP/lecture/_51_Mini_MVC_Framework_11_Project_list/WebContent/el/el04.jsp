<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약어는 식별자로 사용할 수 없다</title>
<style>
body {
	font-size: small;
}

table {
	border: thin solid gray;
	border-collapse: collapse;
}

td, th {
	border: thin dotted gray;
	padding: 2px;
}

th {
	background-color: lightgray;
}

pre {
	font-size: 90%;
}
</style>
</head>
<body>
	<p><a href="el03.jsp">[이전]</a></p>
	
	<h2>예약된 키워드</h2>
	<table>
		<tr>
			<td>and</td>
			<td>or</td>
			<td>not</td>
			<td>eq</td>
		</tr>
		<tr>
			<td>ne</td>
			<td>lt</td>
			<td>gt</td>
			<td>le</td>
		</tr>
		<tr>
			<td>ge</td>
			<td>true</td>
			<td>false</td>
			<td>null</td>
		</tr>
		<tr>
			<td>instanceof</td>
			<td>empty</td>
			<td>div</td>
			<td>mod</td>
		</tr>
	</table>
	<%
		// ne는 예약어 이므로 꺼내 쓸 수 없다.
		//pageContext.setAttribute("ne", "실수!");
		pageContext.setAttribute("se", "잘된다!");
	%>
	
	${se}
	
	
	<p><a href="el03.jsp">[이전]</a></p>
</body>
</html>