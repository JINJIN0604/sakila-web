<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="dao.*" %>
<%@ page import="java.util.*" %>
<%
	// filmListAction.jsp
	// 1) Controller(요청처리 + 모델(Model)호출(dao))
	String col = "film_id";
	String sort = "asc";
	
	if(request.getParameter("col") != null) {
		col = request.getParameter("col");
	}
	if(request.getParameter("sort") != null) {
		sort = request.getParameter("sort");
	}
	
	String paramSort = "asc"; // 제목클릭시 넘겨질 sort값(sort변수의 반대값)
	if(sort.equals("asc")) { 
		paramSort = "desc";
	}
	
	String searchCol = request.getParameter("searchCol"); // title, description, titleAndDescription
	String searchWord = request.getParameter("searchWord"); // 입력한 검색어
	
	FilmDao filmDao = new FilmDao();
	ArrayList<Film> list = filmDao.selectFilmListBySearch(col, sort, searchCol, searchWord);
	
	// 2) View
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>필름 목록(검색 : 동적쿼리)</h1>
	<hr>
	<h2>검색 조건</h2>
	<form action="<%=request.getContextPath()%>/filmListAction.jsp" method="get">
		<select name="searchCol">
			<option value="title">title</option>
			<option value="description">description</option>
			<option value="titleAndDescription">title + description</option>
		</select>
		<input type="text" name="searchWord">
		<button type="submit">검색</button>
	</form>
	
	<hr>
	
	<table border="1">
		<tr>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=film_id&sort=<%=paramSort%>">
					filmId
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=title&sort=<%=paramSort%>">
					title
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=description&sort=<%=paramSort%>">
					description
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=release_year&sort=<%=paramSort%>">
					release_year
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=language_id&sort=<%=paramSort%>">
					language_id
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=original_language_id&sort=<%=paramSort%>">
					original_language_id
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=rental_duration&sort=<%=paramSort%>">
					rental_duration
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=rental_rate&sort=<%=paramSort%>">
					rental_rate
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=length&sort=<%=paramSort%>">
					length
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=replacement_cost&sort=<%=paramSort%>">
					replacement_cost
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=rating&sort=<%=paramSort%>">
					rating
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=special_features&sort=<%=paramSort%>">
					special_features
				</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/filmListAction.jsp?col=last_update&sort=<%=paramSort%>">
					last_update
				</a>
			</th>
		</tr>
		<%
			for(Film f : list) {
		%>
				<tr>
					<td><%=f.getFilmId()%></td>
					<td><%=f.getTitle()%></td>
					<td><%=f.getDescription()%></td>
					<td><%=f.getReleaseYear()%></td>
					<td><%=f.getLanguageId()%></td>
					<td><%=f.getOriginalLanguageId()%></td>
					<td><%=f.getRentalDuration()%></td>
					<td><%=f.getRentalRate()%></td>
					<td><%=f.getLength()%></td>
					<td><%=f.getReplacementCost()%></td>
					<td><%=f.getRating()%></td>
					<td><%=f.getSpecialFeatures()%></td>
					<td><%=f.getLastUpdate()%></td>
				</tr>
		<%		
			}
		%>
	</table>
</body>
</html>