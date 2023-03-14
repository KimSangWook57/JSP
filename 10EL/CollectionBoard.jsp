<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.board.BoardDTO"%>
<%@page import="model.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// db 연결
// 없다면 일일이 수동으로 연결해야 함. (드라이버에서 쿼리를 통해 결과까지.)
BoardDAO dao = new BoardDAO(application);

// 리스트 생성.
List<BoardDTO> aList = dao.selectList(null);

//aList 안에 데이터를 집어넣는다.
request.setAttribute("aList", aList);

// db 종료.
dao.close();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내용 전부 출력하기.</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
</head>
<body>
<table class="table table-striped" border="1" >
<%	

// 위에서 null을 get하려는 것을 방지하기 위해 map != null && 조건을 selectList에 넣어 주었다.
for (int idx = 0; idx < aList.size(); idx++) {
	// 루프를 돌며 요소들을 하나씩 들고 올 준비를 한다.
	pageContext.setAttribute("idx", idx); 
	// 반환된 값들을 출력해준다. %>
	<tr align="center">
		<td>${ aList[idx].num }</td>
		<td>${ aList[idx].title }</td>
		<td>${ aList[idx].content }</td>
		<td>${ aList[idx].postdate }</td>
		<td>${ aList[idx].id }</td>
		<td>${ aList[idx].visitcount }</td>
	</tr>
<% } %>
</table>
</body>
</html>