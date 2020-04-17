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
		<a href="jstl03.jsp">[이전]</a>
		<a href="jstl05.jsp">[다음]</a>
	</p>

	<h3>c:if태그</h3>
	<c:if test="${10 > 20 }" var="result1">
	1) 10은 20보다 크다.
	</c:if>
	2) ${result1 }
	<br>

	<c:if test="${10 < 20 }" var="result2">
	1) 20은 10보다 크다.
	</c:if>
	2) ${result2 }
	<br>

	<p>
		<a href="jstl03.jsp">[이전]</a>
		<a href="jstl05.jsp">[다음]</a>
	</p>
</body>
</html>