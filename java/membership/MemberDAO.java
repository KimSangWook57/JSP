package membership;

import common.JDBConnect;

public class MemberDAO extends JDBConnect {
	// 데이터베이스와 연결된 MemberDAO 객체를 생성.
	public MemberDAO(String drv, String url, String id, String pw) {
		super(drv, url, id, pw);
	}
	
	// 아이디/패스워드와 일치하는 회원정보를 반환.
	public MemberDTO getMemberDTO(String uid, String upass) {
		MemberDTO dto = new MemberDTO(); // 회원정보 객체.
		String query = "SELECT * FROM member WHERE id=? AND pass=?"; // 쿼리문 생성.
		
		try {
			psmt = con.prepareStatement(query); // 쿼리문을 준비하고...
			psmt.setString(1, uid); 			// 아이디와
			psmt.setString(2, upass); 			// 비번으로
			rs = psmt.executeQuery();			// 쿼리문 실행
			
			// 회원정보가 있다면...
			if (rs.next()) {
				// 회원정보를 DTO에 저장.
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString(3));
				dto.setRegidate(rs.getString(4));
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// 저장한 DTO 객체 반환.
		return dto;
		
	}
	
	
}
