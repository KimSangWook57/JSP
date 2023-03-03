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
	<h2>대륙별 영어를 사용하는 나라 수</h2>
	<%
	// db에 연결.
	JDBConnect jdbc = new JDBConnect();

	// 쿼리문 생성.
	String sql = "select country.Continent, count(countrylanguage.CountryCode) as ContinentCount from countrylanguage inner join country on country.Code = countrylanguage.CountryCode where countrylanguage.Language = 'English' group by country.Continent";

	Statement stmt = jdbc.con.createStatement();

	// 쿼리 수행.
	ResultSet rs = stmt.executeQuery(sql);

	// 테이블 생성.
	%>

	<table class="table table-striped">
		<thead>
			<tr>
				<th>Continent</th>
				<th>ContinentCount</th>
			</tr>
		</thead>
		<tbody>
			<%
			// 결과 확인.
			while (rs.next()) {
				// 값을 하나씩 가져온다.
				String Continent = rs.getString(1);
				String ContinentCount = rs.getString(2);
			%>

			<%
			// 단순 %로는 자바 문법, 변수는 %뒤에 =이 들어간다!!!
			%>
			<%
			// 읽어온 데이터를 테이블로 만든다.
			%>
			<tr>
				<th><%=Continent%></th>
				<td><%=ContinentCount%></td>
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