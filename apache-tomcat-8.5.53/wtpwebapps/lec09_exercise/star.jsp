<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>별찍기</title>
</head>
<body>
	<p>별찍기를 테스트하는 곳입니다</p>
	<%
		for(int i=0;i<=10;i++){
			for(int j=0;j<=i;j++){
				%>
				 *
				<%
			}
			%>
			<br>
			<% 
		}

	%>
</body>
</html>