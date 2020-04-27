<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="spms.vo.Member" %>



<div style="background-color:#00008b; color:white; 
		height:20px; padding:5px;">
SPMS(Simple Project Management System)

	<span style="float:right;">
		${member.name}
		<a style="color:white;"
			href="${pageContext.request.contextPath }/auth/logout.do">
			로그아웃
		</a>
	</span>
</div>









