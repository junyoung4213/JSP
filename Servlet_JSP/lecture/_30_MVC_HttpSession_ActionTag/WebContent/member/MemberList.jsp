<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="spms.vo.Member" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
</head>
<body>
	<jsp:include page="/Header.jsp"/>
	<h1>회원 목록</h1>
	<p><a href='add'>신규 회원</a></p>
	<!-- request공간에서 members이름의 객체를 가져와라 -->
	<!-- 아래 java 코드 대신 jsp액션태그를 사용 -->
	<jsp:useBean id="members"
		scope="request"
		class="java.util.ArrayList"
		type="java.util.ArrayList<spms.vo.Member>"/>
	
	<!-- 스크립트릿 영역 -->
	<%
	/*
		ArrayList<Member> members = (ArrayList<Member>)
							request.getAttribute("member");
	*/
		for(Member member : members){
	%>
		<%=member.getNo()%>, 
		<a href='update?no=<%=member.getNo()%>'>
			<%=member.getName()%>
		</a>, 
		<%=member.getEmail()%>, 
		<%=member.getCreatedDate()%>
		<a href='delete?no=<%=member.getNo()%>'>
			[삭제]
		</a>
		<br>
	<%
		}
	%>
	<jsp:include page="/Tail.jsp"/>
</body>
</html>










