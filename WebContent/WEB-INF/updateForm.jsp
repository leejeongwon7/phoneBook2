<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ page import="com.javaex.vo.PersonVo"%>
    
    <%
    //Action으로 넘어온 값을 변경시킨후 JSP 페이지로 받아오기
    PersonVo personVo = (PersonVo)request.getAttribute("psnVo");
    //getAttribute 는 여러가지 객체를 get 할 수 있는데 Date 타입 객체를 받았다. Date 외에도, String, File 타입의 객체도 받을 수 있다
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>[Phonebook2]</h1>
	<h2>전화번호 수정폼</h2>
	<p>전화번호를 수정하려면 아래 항목을 기입하고 수정 버튼을 누르세요</p>

	<form action="/phonebook2/pbc" method="get"> 
		이름(name) : <input type="text" name="name" value="<%=personVo.getName()%>"> 
		<br> 
		핸드폰(hp) : <input	type="text" name="hp" value="<%=personVo.getHp()%>"> 
		<br>
		회사(company) <input type="text" name="company" value="<%=personVo.getCompany()%>"> 
		<br> 
		저장ID <input type="text" name="id" value="<%=personVo.getPersonId()%>"> 
		<br> 
		히든처리할 액션 <input type="text" name="action" value="update">
		<button type="submit">수정</button>
	</form>
	<br>
	<a href="http://localhost:8088/phonebook1/list.jsp">목록으로 (절대경로)</a>
	<a href="./list.jsp">목록으로 돌아가기</a>
</body>
</html>