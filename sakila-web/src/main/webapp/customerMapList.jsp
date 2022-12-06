<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "vo.*" %>
<%
	// c
	// 페이징
	int currentPage = 1;
	if(request.getParameter("currentPage") != null){
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int rowPerPage = 10;
	int beginRow = (currentPage-1)*rowPerPage;
	
	CustomerDao customerDao = new CustomerDao();
	int count = customerDao.selectCustomerCount();
	int lastPage = (int)Math.ceil((double)count / (double)rowPerPage); 
	
	//m
	ArrayList<HashMap<String,Object>> list = customerDao.selectCustomerMapList(beginRow, rowPerPage);
	
	// 3. View
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- Map 타입 사용 -->
	<div>
	<h1>customer map list</h1>
		<table border="1">
			<tr>
				<th>fristName</th>
				<th>lastName</th>
				<th>address</th>
				<th>city</th>
				<th>country</th>
			</tr>
			<%
				for(HashMap<String,Object> map : list){
			%>
				<tr>
					<td><%=map.get("firstName")%></td>
					<td><%=map.get("lastName")%></td>
					<td><%=map.get("address")%></td>
					<td><%=map.get("city")%></td>
					<td><%=map.get("country")%></td>
				</tr>
			<%
				}
			%>
		
		</table>	
		
		<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=1">처음</a>
			<%
				if(currentPage > 1) {
			%>
					<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=currentPage-1%>">이전</a>
			<%
				}
			%>
			<span><%=currentPage%></span>
			<%
				if(currentPage < lastPage){
			%>
					<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=currentPage+1%>">다음</a>
			<%		
				}
			%>
		<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=lastPage%>">마지막</a>
	</div>
</body>
</html>