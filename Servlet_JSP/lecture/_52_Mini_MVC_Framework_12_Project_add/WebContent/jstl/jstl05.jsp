<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSTL</title>
</head>
<body>
	<p>
		<a href="jstl04.jsp">[이전]</a>
		<a href="jstl06.jsp">[다음]</a>
	</p>
	
	
	<h2>c:choose 태그</h2>
	<c:set var="userid" value="admin"/>
	<c:choose>
		<c:when test="${userid=='hong'}">
			홍길동님 반갑습니다.
		</c:when>
		<c:when test="${userid=='lim'}">
			임꺽정님 반갑습니다.
		</c:when>
		<c:when test="${userid=='admin'}">
			관리자님 반갑습니다.
		</c:when>
		<c:otherwise>
			등록되지 않은 사용자입니다.
		</c:otherwise>
	</c:choose>
	
	
	<p>
		<a href="jstl04.jsp">[이전]</a>
		<a href="jstl06.jsp">[다음]</a>
	</p>
</body>
</html>