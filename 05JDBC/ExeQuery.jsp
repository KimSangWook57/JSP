<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="common.JDBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JDBC</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
</head>
<body>
	<h2>회원 조회 테스트</h2>

	<%
	// db에 연결.
	JDBConnect jdbc = new JDBConnect();

	// 쿼리문 생성.
	String sql = "SELECT id, pass, name, regidate FROM member";
	Statement stmt = jdbc.con.createStatement();

	// 쿼리 수행.
	ResultSet rs = stmt.executeQuery(sql);
	
	// 테이블 생성.
	%>

	<table class="table table-striped">
		<thead>
			<tr>
				<th>id</th>
				<th>pass</th>
				<th>name</th>
				<th>regidate</th>
			</tr>
		</thead>
		<tbody>
			<%
			// 결과 확인.
			while (rs.next()) {
				// 값을 하나씩 가져온다.
				String id = rs.getString(1);
				String pw = rs.getString(2);
				String name = rs.getString("name");
				java.sql.Date regidate = rs.getDate("regidate");
				// 단순한 출력 코드.
				// out.println(String.format("%s %s %s %s", id, pw, name, regidate) + "<br />");
			%>
			
			<%// 단순 %로는 자바 문법, 변수는 %뒤에 =이 들어간다!!! %>
			<%// 읽어온 데이터를 테이블로 만든다. %>
			<tr>
				<th><%= id %></th>
				<td><%= pw %></td>
				<td><%= name %></td>
				<td><%= regidate %></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>

	<%
	// 연결 닫기.
	jdbc.close();
	%>

</body>
</html>