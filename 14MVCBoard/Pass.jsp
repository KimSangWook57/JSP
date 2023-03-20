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
	function validateForm(form) {
		if (form.pass.value == "") {
			alert("비밀번호를 입력핫게요.");
			form.pass.focus();
			reyurn false;
		}
	}
</script>
</head>
<body>
<h2>파일 첨부형 게시판 - 비밀번호 검증(Pass)</h2>
<form name="writeFrm" method="post" action="../mvcboard/pass.do" onsubmit="return validateForm(this);">
	<input type="hidden" name="idx" value="${ param.idx }" />
	<input type="hidden" name="mode" value="${ param.mode }" />
<table border="1" width="90%" class="table table-striped">
	<tr>
		<td>비밀번호</td>
		<td><input type="password" name="pass" style="width:100px;" /></td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<button type="submit">검증하기</button>
			<button type="reset">초기화</button>
			<button type="button" onclick="location.href='../mvcboard/list.do';">목록으로</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>