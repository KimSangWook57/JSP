package edu.pnu;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class JDBCClientMySQL {

	public static void main(String[] args) throws Exception {
		
		// JDBC 드라이버 로드. 
		// JDBCStudy 프로젝트 우클릭 - Build path => Configure build path
		// 라이브러리 => Classpath 선택 => add external jar => MySQL의 connecter - j 파일.
		// Class의 forName static 메소드를 들고 왔다.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// MySQL Database 연결 객체 생성. (컴퓨터의 IP, 유저명, 유저 패스워드)
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "musthave", "tiger");
		
		// 질의를 위한 객체 생성.
		Statement st = con.createStatement();
		
		// SQL 질의.
		ResultSet rs = st.executeQuery("select Name,Continent,Population,HeadOfState from country");
		
		// 질의 결과 Parsing. (cursor 프로세싱)
		// 빈 공간에 커서가 있다가, 첫 번째 값부터 가져오는 것이다.
		while(rs.next()) { // 다음 값으로 이동.
			// 1 ~ 4까지 루프를 돈다.
			for(int i = 1; i <= 4; i++) {
				// 2, 3, 4번째 값의 앞에 ,를 찍는다.
				if (i != 1) System.out.print(",");
				// 1, 2, 3, 4번째 값을 찍는다.
				System.out.print(rs.getString(i));
			}
			System.out.println();	// 다음 줄로 넘어가는 코드.
			System.out.println();	// 구분을 위한 코드.
		}
		
		// 생성된 객체 연결 해제.
		rs.close();
		st.close();
		con.close();
		
	}

}
