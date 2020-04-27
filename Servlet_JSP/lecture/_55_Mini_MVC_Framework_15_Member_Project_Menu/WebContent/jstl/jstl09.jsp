<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL</title>
</head>
<body>
	<p>
		<a href="jstl08.jsp">[이전]</a>
		<a href="jstl10.jsp">[다음]</a>
	</p>
	
	<h2>c:import 태그</h2>
	
	<h3>RSS 피드 가져오기</h3>
	<textarea cols="80" rows="10">
		<c:import url="http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=108"/>
	</textarea>
	
	<h3>RSS 피드 가져오기 - 보관소에 저장</h3>
	<c:import var="zdnetRSS" url="http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=108"/>
	<textarea cols="80" rows="10">
		${zdnetRSS}
	</textarea>
	
	<p>
		<a href="jstl08.jsp">[이전]</a>
		<a href="jstl10.jsp">[다음]</a>
	</p>
</body>
</html>