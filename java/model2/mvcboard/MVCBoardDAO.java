package model2.mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.JDBConnect;

public class MVCBoardDAO extends JDBConnect{
	
	public MVCBoardDAO() {
		super();
	}
	
		// 검색 조건에 맞는 게시물의 개수 반환.
		public int selectCount(Map<String, Object> map) {
			int totalCount = 0;

			String query = "SELECT COUNT(*) FROM mvcboard";
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
				System.out.println("게시물 수를 구하는 중 오류 발생.");
				e.printStackTrace();
			}

			return totalCount;
		}
		
		public List<MVCBoardDTO> selectListPage(Map<String, Object> map) {
			List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
			
			String query = "SELECT * FROM mvcboard ";
			
			if (map.get("SearchWord") != null) {
				query += " WHERE " + map.get("SearchField") 
						+ " Like '%" + map.get("SearchWord") + "%' ";
			}
			
			query += " ORDER BY idx DESC LIMIT ?,?";
			
			try {
				psmt = con.prepareStatement(query);
				// idx를 int로 선언했었기 때문에, 숫자로 바꾸어서 넣어주어야 한다!!!
				psmt.setInt(1, Integer.parseInt(map.get("start").toString()));
				psmt.setInt(2, Integer.parseInt(map.get("pageSize").toString()));
				rs = psmt.executeQuery();
				
				while(rs.next()) {
					MVCBoardDTO dto = new MVCBoardDTO();
					
					dto.setIdx(rs.getString(1));
					dto.setName(rs.getString(2));
					dto.setTitle(rs.getString(3));
					dto.setContent(rs.getString(4));
					dto.setPostdate(rs.getDate(5));
					dto.setOfile(rs.getString(6));
					dto.setSfile(rs.getString(7));
					dto.setDowncount(rs.getInt(8));
					dto.setPass(rs.getString(9));
					dto.setVisitcount(rs.getInt(10));
					
					board.add(dto);
					
				}
				
			}
			catch (Exception e) {
				System.out.println("게시물 조회 중 오류 발생");
				e.printStackTrace();
			}
			return board;
			
		}
		
		public int insertWrite(MVCBoardDTO dto) {
			int result = 0;
			
			try {
				String query = "INSERT INTO mvcboard ( "
							 + " name, title, content, ofile, sfile, pass) "
							 + " VALUES ( "
							 + " ?,?,?,?,?,?)";
				
				psmt = con.prepareStatement(query);
				psmt.setString(1, dto.getName());
				psmt.setString(2, dto.getTitle());
				psmt.setString(3, dto.getContent());
				psmt.setString(4, dto.getOfile());
				psmt.setString(5, dto.getSfile());
				psmt.setString(6, dto.getPass());
				result = psmt.executeUpdate();
				
			}
			catch (Exception e) {
				System.out.println("게시물 입력 중 오류 발생");
				e.printStackTrace();
			}
			return result;
			
		}
		
		public MVCBoardDTO selectView(String idx) { 
			MVCBoardDTO dto = new MVCBoardDTO();
			String query = "SELECT * FROM mvcboard WHERE idx = ?";
			
			try {
				psmt = con.prepareStatement(query);
				psmt.setString(1, idx);
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					dto.setIdx(rs.getString(1));
					dto.setName(rs.getString(2));
					dto.setTitle(rs.getString(3));
					dto.setContent(rs.getString(4));
					dto.setPostdate(rs.getDate(5));
					dto.setOfile(rs.getString(6));
					dto.setSfile(rs.getString(7));
					dto.setDowncount(rs.getInt(8));
					dto.setPass(rs.getString(9));
					dto.setVisitcount(rs.getInt(10));
				}
			}
			catch (Exception e) { 
				System.out.println("게시물 상세보기 중 오류 발생");
			}
			return dto;
			
		}
		
		public void updateVisitCount(String idx) {
			String query = "UPDATE mvcboard SET "
						 + " visitcount = visitcount + 1 "
						 + " WHERE idx = ?";
			
			try {
				psmt = con.prepareStatement(query);
				psmt.setString(1, idx);
				psmt.executeUpdate();
			}
			catch (Exception e) { 
				System.out.println("게시물 조회수 증가 중 오류 발생");
			}
			
		}
		
		public void downCountPlus(String idx) {
			String sql = "UPDATE mvcboard SET "
						 + " downcount = downcount + 1 "
						 + " WHERE idx = ?";
			
			try {
				psmt = con.prepareStatement(sql);
				psmt.setString(1, idx);
				psmt.executeUpdate();
			}
			catch (Exception e) { 
				System.out.println("다운로드 수 증가 중 오류 발생");
			}
			
		}
		
		public boolean confirmPassword(String pass, String idx) {
			boolean isCorr = true;
			
			try {
				String sql = "SELECT COUNT(*) FROM mvcboard WHERE pass=? AND idx=?";
				psmt = con.prepareStatement(sql);
				psmt.setString(1, pass);
				psmt.setString(2, idx);
				rs = psmt.executeQuery();
				rs.next();
				// 대상 게시물이 없다면 지울 것이 없다.
				if (rs.getInt(1) == 0) {
					isCorr = false;	
				}
			}
			catch (Exception e) {
				isCorr = false;
				e.printStackTrace();
			}
			return isCorr;
		}
		
		public int deletePost(String idx) {
			int result = 0;
			
			try {
				String query = "DELETE FROM mvcboard WHERE idx=?";
				psmt = con.prepareStatement(query);
				psmt.setString(1, idx);
				result = psmt.executeUpdate();
			}
			catch (Exception e) {
				System.out.println("게시물 삭제 중 오류 발생");
				e.printStackTrace();
			}
			// 정상적으로 삭제되면 1 반환.
			return result;
		}
		
		public int updatePost(MVCBoardDTO dto) {
			int result = 0;
			
			try {
				String query = "UPDATE mvcboard" 
							 + " SET title=?, name=?, content=?, ofile=?, sfile=? "
							 + " WHERE idx=? and pass=?";
				
				psmt = con.prepareStatement(query);
				psmt.setString(1, dto.getTitle());
				psmt.setString(2, dto.getName());
				psmt.setString(3, dto.getContent());
				psmt.setString(4, dto.getOfile());
				psmt.setString(5, dto.getSfile());
				psmt.setInt(6, Integer.parseInt(dto.getIdx()));
				psmt.setString(7, dto.getPass());
				
				result = psmt.executeUpdate();
				
			}
			catch (Exception e) {
				System.out.println("게시물 수정 중 오류 발생");
				e.printStackTrace();
			}
			return result;
		}
		
		
}
