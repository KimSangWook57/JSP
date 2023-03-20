package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;

public class JDBConnect {

	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;
    // case 1
	public JDBConnect() {
		try {
			// 드라이버 설치
			Class.forName("com.mysql.cj.jdbc.Driver");

			// db 연결
			String url = "jdbc:mysql://localhost:3306/musthave";
			// String url = "jdbc:mysql://localhost:3306/world";
			String id = "musthave";
			String pwd = "tiger";
			con = DriverManager.getConnection(url, id, pwd);

			System.out.println("db 연결 성공(기본 생성자)");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// case 2
	public JDBConnect(String driver, String url, String id, String pwd) {
		try {
			// 드라이버 로드
			Class.forName(driver);

			// db 연결
			con = DriverManager.getConnection(url, id, pwd);

			System.out.println("db 연결 성공(인수 생성자 1)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// case 3
	public JDBConnect(ServletContext application) {
		try {
			// 드라이버 로드
			String driver = application.getInitParameter("MySQLDriver");
			Class.forName(driver);

			// db 연결
			String url = application.getInitParameter("MySQLURL");
			String id = application.getInitParameter("MySQLId");
			String pwd = application.getInitParameter("MySQLPwd");
			con = DriverManager.getConnection(url, id, pwd);

			System.out.println("db 연결 성공(인수 생성자 2)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		JDBConnect jdbConnect = new JDBConnect();
	}

	// 연결 해제
	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (psmt != null)
				psmt.close();
			if (con != null)
				con.close();

			System.out.println("JDBC 자원 해제");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
