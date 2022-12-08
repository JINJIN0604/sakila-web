<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*"%>
<%@ page import = "dao.*"%>
<%@ page import = "java.util.*"%>

<%
	// 1 Controller
	//	(rating)
	String[] rating = request.getParameterValues("rating"); // 복수의 값을 받아올때
	System.out.println("rating : " + rating);
	if(rating != null) {
		System.out.println("rating.length : " + rating.length);
	}
	
	//(searchTitle)
	String searchTitle = request.getParameter("searchTitle");
	System.out.println("searchTitle : "+searchTitle);
	
	// (fromMinute & toMinute)
	// System.out.println("fromMinute받아오는값 : "+request.getParameter("fromMinute"));
	int fromMinute = 0; // 초기화
	int toMinute = 0;	// 초기화
	//둘다 공백값이 아니면 -> 정상적인 숫자값이 넘어 왔다면
	if(request.getParameter("fromMinute").equals("")){
		fromMinute = 46; // 데이터 상의 상영시간 최솟값
	} else {
		fromMinute = Integer.parseInt(request.getParameter("fromMinute"));
	}
	if(request.getParameter("toMinute").equals("")){
		toMinute = 185; // 데이터 상의 상영시간 최댓값
	} else {
		toMinute = Integer.parseInt(request.getParameter("toMinute"));
	}
	System.out.println("fromMinute : "+fromMinute);
	System.out.println("toMinute : "+toMinute);
	
	// 출시년도 받아오기
	String releaseYear = request.getParameter("releaseYear");
	System.out.println("releaseYear : "+releaseYear);
	
	// 출력
	FilmDao filmDao = new FilmDao();
	ArrayList<Film> list = filmDao.selectFilmList2(releaseYear, searchTitle, rating, fromMinute, toMinute);

%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>검색결과</h1>
	<table border="1">
		<tr>
			<th>출시 년도</th>
			<th>필름 제목</th>
			<th>필름 등급</th>
			<th>필름 상영시간</th>
		</tr>
		<%
			for(Film f : list){
		%>
				<tr>
					<td><%=f.getReleaseYear()%></td>
					<td><%=f.getTitle()%></td>
					<td><%=f.getRating()%></td>
					<td><%=f.getLength()%></td>
				</tr>
		<%
			}
		%>
	</table>
</body>
</html>