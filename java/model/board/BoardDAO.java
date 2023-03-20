package model.board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;

public class BoardDAO extends JDBConnect {

	public BoardDAO(ServletContext application) {
		super(application);
	}
	// 검색 조건에 맞는 게시물의 개수 반환.
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;

		String query = "SELECT COUNT(*) FROM board";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " " 
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생.");
			e.printStackTrace();
		}

		return totalCount;
	}
	// 검색 조건에 맞는 게시물의 목록 반환.
	public List<BoardDTO> selectList(Map<String, Object> map) {
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		String query = "SELECT * FROM board";
		if (map != null && map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " " 
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}
		
		query += " ORDER BY num DESC ";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				bbs.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 에러 발생.");
			e.printStackTrace();
		}
		
		return bbs;
	}
	
	public List<BoardDTO> selectListPage(Map<String, Object> map) {
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		// 오라클 쿼리 예시.
//		String query = " SELECT * FROM ( " 
//					 + " 	SELECT Tb.*, ROWNUM rNUM FROM ( "
//					 + " 		SELECT * FROM board ";	
//		
//		if (map.get("SearchWord") != null) {
//			query += " WHERE " + map.get("SearchField") 
//					+ " Like '%" + map.get("SearchWord") + "%' ";
//		}
//		
//		query += " 			ORDER BY num DESC "
//				+ " 		) Tb "
//				+ " ) "
//				+ " WHERE rNum BETWEEN ? AND ?";
		
		// MySQL 쿼리문으로 작성 필요.
		String query = "SELECT * FROM board ";
		
		if (map.get("SearchWord") != null) {
			query += " WHERE " + map.get("SearchField") 
					+ " Like '%" + map.get("SearchWord") + "%' ";
		}
		// Order by와 Limit를 이용해 정리.
		query += " ORDER BY num DESC LIMIT ?,?";
		
		try {
			psmt = con.prepareStatement(query);
			// 오라클 범위 지정 예시.
//			psmt.setString(1, map.get("start").toString());
//			psmt.setString(2, map.get("end").toString());
			// MySQL 범위 지정 예시.
			psmt.setInt(1, (int)map.get("start") - 1);
			psmt.setInt(2, (int)map.get("end"));
			
			
			rs= psmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				bbs.add(dto);
			}
		}
		catch (Exception e) {
			System.out.println("게시물 조회 중 오류 발생");
			e.printStackTrace();
		}
		
		return bbs;
	}
	
	
	
	public int insertWrite(BoardDTO dto) {
		int result = 0;
		
		try { 
			String query = "INSERT INTO board ( "
						+ " title,content,id) "
						+ " VALUES ( "
						+ " ?, ?, ?)";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 오류 발생.");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO();
		
		String query = "SELECT B.*, M.name " 
				 	+ " FROM member M INNER JOIN board B " 
				 	+ " ON M.id=B.id "
				 	+ " WHERE num=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				dto.setNum(rs.getString(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString(6));
				dto.setName(rs.getString("name"));
			}
			
		}
		catch (Exception e) {
			System.out.println("게시물 상세보기 중 에러 발생.");
			e.printStackTrace();
		}
		return dto;
	}
	
	public void updateVisitCount(String num) {
		String query = "UPDATE board SET "
					+ " visitcount = visitcount + 1 "
					+ " WHERE num = ?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			psmt.executeUpdate();
		}	
		catch (Exception e) {
			System.out.println("게시물 조회수 증가 중 오류 발생.");
			e.printStackTrace();
		}
	}
	
	public int updateEdit(BoardDTO dto) {
		int result = 0;
		
		try {
			String query = "UPDATE board SET " 
						+ " title=?, content=? "
						+ "WHERE num=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getNum());
			
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 수정 중 오류 발생.");
			e.printStackTrace();
		}
		return result;
	}
	
	public int deletePost(BoardDTO dto) {
		int result = 0;
		
		try {
			String query = "DELETE FROM board WHERE num=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getNum());
			
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 삭제 중 오류 발생.");
			e.printStackTrace();
		}
		
		return result;
	}
	
}
