<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>lotto</title>
</head>
<body>
	<%! boolean[] num=new boolean[45];
		int[] win = new int[6];
		String result=null;%>
	<p>로또 당첨번호를 알려주는 페이지입니다.</p>
	
	
	<%
		for(int i=0; i<6;i++){
			win[i]=(int)(Math.random()*45)+1;
			if(num[win[i]-1]){
				i--;
				continue;
			}else{
 				num[win[i]-1]=true;
			}
		}
		Arrays.sort(win);
		
	%>
	
	<p>이번주 로또 추천번호는? <%= Arrays.toString(win) %></p>
</body>
</html>