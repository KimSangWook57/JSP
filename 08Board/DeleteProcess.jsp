<%@page import="model.board.BoardDTO"%>
<%@page import="model.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp"%>

<%
String num = request.getParameter("num");

BoardDTO dto = new BoardDTO();
BoardDAO dao = new BoardDAO(application);
dto = dao.selectView(num);


String sessionId = session.getAttribute("UserId").toString();

int delResult = 0;

if (sessionId.equals(dto.getId())) {
	dto.setNum(num);
	delResult = dao.deletePost(dto);
	dao.close();
	
	if (delResult == 1) 
		JSFunction.alertLocation("삭제 완료.", "List.jsp", out);
	else
		JSFunction.alertBack("삭제 실패!", out);
}
else {
	JSFunction.alertBack("작성자 본인만 삭제가 가능합니다.", out);
}
%>