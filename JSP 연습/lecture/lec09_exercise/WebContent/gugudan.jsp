<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>구구단을 테스트하는 페이지입니다</p>
	<%
		for (int i = 2; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				%>
				<%=i%>곱하기<%=j %>는 <%=i*j %>입니다<br>
				<% 
			}
		}
	%>
	<%@include file="star.jsp" %>
	<%@include file="lotto.jsp" %>

</body>
</html>