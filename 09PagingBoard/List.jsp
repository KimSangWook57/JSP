<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="model.board.BoardDTO"%>
<%@page import="model.board.BoardDAO"%>
<%@page import="utils.BoardPage" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// db 연결
BoardDAO dao = new BoardDAO(application);

// 검색 조건 저장
Map<String, Object> param = new HashMap<String, Object>();
String searchField = request.getParameter("searchField");
String searchWord = request.getParameter("searchWord");
if (searchWord != null) {
	param.put("searchField", searchField);
	param.put("searchWord", searchWord);
}
// 총 게시물 개수.
int totalCount = dao.selectCount(param);

// 전체 페이지 수 계산.
int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
int totalPage = (int)Math.ceil((double)totalCount / pageSize); // 전체 페이지 수 계산(올림)

// 현재 페이지 확인
int pageNum = 1; // 디폴트 1페이지.
String pageTemp = request.getParameter("pageNum");
if (pageTemp != null && !pageTemp.equals("")) 
	pageNum = Integer.parseInt(pageTemp);

// 출력할 게시물 범위 계산.
int start = (pageNum - 1) * pageSize + 1; // 첫 게시물 번호.
int end = pageSize; // 마지막 게시물 번호.
param.put("start", start);
param.put("end", end);

// 게시물 목록 받기.
List<BoardDTO> boardLists = dao.selectListPage(param);
dao.close();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 게시판</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
</head>
<body>
	<jsp:include page="../Common/Link.jsp" />
	<h2>목록 보기(List) - 현재 페이지 : <%= pageNum %> (전체 페이지 : <%= totalPage %>)</h2>
	
	<form method="get">
	<table class="table table-striped" border="1" width="90%">
	<tr>
		<td class="table-light" align="center">
			<select name="searchField">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchWord" />
			<input type="submit" value="검색하기" />
		</td>
	</tr>
	</table>
	</form>
	
	<table class="table table-striped" border="1" width="90%">
		<tr class="table-dark" align="center">
			<th width="10%">번호</th>
			<th width="50%">제목</th>
			<th width="15%">작성자</th>
			<th width="10%">조회수</th>
			<th width="15%">작성일</th>
		</tr>
		
<%
// 게시물 없음
if (boardLists.isEmpty()) {
%>	

		<tr>
			<td colspan="5" align="center">
				등록된 게시물이 없습니다.
			</td>
		</tr>
<%
}
// 게시물 있음
else {
	int virtualNum = 0;
	int countNum = 0;
	for (BoardDTO dto : boardLists) {
		virtualNum = totalCount - (((pageNum - 1) * pageSize) + countNum++);
%>
		
		<tr align="center">
			<td><%= virtualNum %></td>
			<td align="left">
				<a href="View.jsp?num=<%= dto.getNum()%>"><%= dto.getTitle() %></a>
			</td>
			<td align="center"><%= dto.getId() %></td>
			<td align="center"><%= dto.getVisitcount() %></td>
			<td align="center"><%= dto.getPostdate() %></td>
		</tr>
<%
	}

}
%>
	</table>
	<table class="table table-striped" border="1" width="90%">
		<tr align="center">
			<td><%= BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, request.getRequestURI()) %></td>
			<!-- 글쓰기 버튼 작성 -->
			<td><button type="button" onclick="location.href='Write.jsp';">글쓰기</button></td>
		</tr>
	</table>
</body>
</html>