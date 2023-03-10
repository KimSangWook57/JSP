<%@page import="model.board.BoardDTO"%>
<%@page import="model.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp"%>

<%
// form 값 설정
String title = request.getParameter("title");
String content = request.getParameter("content");

// form 값을 객체에 저장.
BoardDTO dto = new BoardDTO();
dto.setTitle(title);
dto.setContent(content);
dto.setId(session.getAttribute("UserId").toString());

// 객체를 사용해서 DB에 저장.
BoardDAO dao = new BoardDAO(application);
int iResult = dao.insertWrite(dto);
dao.close();

// 글쓰기 결과 확인.
if (iResult == 1) 
	response.sendRedirect("List.jsp");
else
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out);
%>