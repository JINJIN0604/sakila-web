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
	
	CustomerDao customerDao = new CustomerDao();
	int count = customerDao.selectCustomerCount(); 
	int lastPage = (int)Math.ceil((double)count / (double)rowPerPage); 
	int beginRow = (currentPage-1)*rowPerPage; 
	
	ArrayList<CustomerAddressCityCountry> list = customerDao.selectCustomerJoinList(beginRow, rowPerPage);
	// 3. View
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- domain 타입 사용 -->
	<div>
		<h1>customer join list</h1>
		<table border="1">
			<tr>
				<th>fristName</th>
				<th>lastName</th>
				<th>address</th>
				<th>city</th>
				<th>country</th>
			</tr>
			<%
				for(CustomerAddressCityCountry cacc : list){
			%>
				<tr>
					<td><%=cacc.getCustomer().getFirstName()%></td>
					<td><%=cacc.getCustomer().getLastName()%></td>
					<td><%=cacc.getAddress().getAddress()%></td>
					<td><%=cacc.getCity().getCity() %></td>
					<td><%=cacc.getCountry().getCountry() %></td>
				</tr>
			<%
				}
			%>
		</table>	
		<!-- 페이징 -->
		<a href="<%=request.getContextPath()%>/customerJoinList.jsp?currentPage=1">처음</a>
			<%
				if(currentPage > 1) {
			%>
					<a href="<%=request.getContextPath()%>/customerJoinList.jsp?currentPage=<%=currentPage-1%>">이전</a>
			<%
				}
			%>
			<span><%=currentPage%></span>
			<%
				if(currentPage < lastPage){
			%>
					<a href="<%=request.getContextPath()%>/customerJoinList.jsp?currentPage=<%=currentPage+1%>" >다음</a>
			<%		
				}
			%>
		<a href="<%=request.getContextPath()%>/customerJoinList.jsp?currentPage=<%=lastPage%>" >마지막</a>
	</div>
</body>
</html>