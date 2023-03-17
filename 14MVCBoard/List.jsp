<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<style>a{text-decoration:none}</style>
</head>
<body>
	<h2>파일 첨부형 게시판 - 목록 보기(리스트)</h2>
	
	<!-- 검색창 -->
	<form method="get">
	<table border="1" width="90%" class="table table-striped">
	<tr>
		<td align="center">
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
	
	<!-- 목록창 -->
	
	<table border="1" width="90%" class="table table-striped">
	<tr>
		<th width="10%">번호</th>
		<th width="*">제목</th>
		<th width="15%">작성자</th>
		<th width="10%">조회수</th>
		<th width="15%">작성일</th>
		<th width="8%">첨부</th>
	</tr>
<c:choose>
	<c:when test="${ empty boardLists }">
		<tr>
			<td colspan="6" align="center">
				등록된 게시물이 없습니다.
			</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${ boardLists }" var="row" varStatus="loop">
		<tr align="center">
			<td>	<!-- 번호 -->
				${ map.totalCount - (((map.pageNum - 1) * map.pageSize) + loop.index)}
			</td>
			<td align="left"> 	<!-- 제목(링크) -->
				<a href="../mvcboard/wiew.do?idx=${ row.idx }">${ row.title }</a>
			</td>
			<td>${ row.name }</td> 			<!-- 작성자 -->
			<td>${ row.visitcount }</td> 	<!-- 조회수 -->
			<td>${ row.postdate }</td> 		<!-- 작성일 -->
			<td>
			<c:if test="${ not empty row.ofile }"> 		<!-- 참부파일 -->
				<a href="../mvcboard/download.do?ofile=${ row.ofile }&sfile=${ row.sfile }&idx=${ row.idx }">[Down]</a>
			</c:if>
			</td>
		</tr>
		</c:forEach>
	</c:otherwise>	
</c:choose>	
	</table>
	
	<!-- 하단 메뉴(바로가기, 글쓰기) -->
	<table border="1" width="90%" class="table table-striped">
		<tr align="center">
			<td>
				${ map.pagingImg }
			</td>
			<td width="100"><button type="button" onclick="loaction.href='../mvcboard/write.do';">글쓰기</button></td>
		</tr>
	</table>
</body>
</html>