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
<script type="text/javascript">
	function vaildateForm(form) {
		if (form.name.value == "") {
			alert("작성자를 입력하세요.");
			form.name.focus();
			return false;
		}
		if (form.title.value == "") {
			alert("제목을 입력하세요.");
			form.title.focus();
			return false;
		}
		if (form.context.value == "") {
			alert("내용을 입력하세요.");
			form.context.focus();
			return false;
		}
	}
</script>
</head>
<body>
	<h2>파일 첨부형 게시판 - 수정하기</h2>
	<form name="writeFrm" method="post" enctype="multipart/form-data" action="../mvcboard/edit.do" onsubmit="return vaildateForm(this);">
		<input type="hidden" name="idx" value="${ dto.idx }" />
		<input type="hidden" name="prevOfile" value="${ dto.ofile }" />
		<input type="hidden" name="prevSfile" value="${ dto.sfile }" />
		
		<table border="1" width="90%" class="table table-striped">
			<tr>
				<td>작성자<input type="text" name="name" style="width:150px;" value="${ dto.name }" /></td>
			</tr>
			<tr>
				<td>제목<input type="text" name="title" style="width:90%;" value="${ dto.title }" /></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" style="width:90%;heigth:100px;">${ dto.content }</textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="ofile" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">작성완료</button>
					<button type="reset">리셋하기</button>
					<button type="button" onclick="location.href='../mvcboard/list.do';">목록으로</button>
				</td>
			</tr>
		</table>
	</form>	
</body>
</html>