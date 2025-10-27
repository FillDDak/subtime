package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PostDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	//연결
	public void getCon() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/xe");
			con = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//#1. 게시글 저장
	public void insertBoard(PostDTO bean) {
		
		getCon();
		
		int hit;
		
		try {
			
			String resql = "select max(ref) from board";
			pstmt=con.prepareStatement(resql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				hit = rs.getInt(1)+1;
			}
			
			//실제 게시글 테이블에 저장
			String sql = "insert into posts values (POST_ID,?,?,?,?,sysdate,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getTITLE());
			pstmt.setString(2, bean.getCONTENT());
			pstmt.setString(3, bean.getWRITER_ID());
			pstmt.setInt(4, bean.getHIT());
			pstmt.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//#2. 전체 게시글 수 리턴
	public int getAllCount() {
		getCon();
		
		int count = 0;
		
		try {
			
			String sql = "select count(*) from posts";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	//#3. 전체 게시글 출력
	public Vector<PostDTO> getAllBoard(int startRow, int endRow){
		getCon();
		
		Vector<PostDTO> v = new Vector<PostDTO>();
		
		try {
			//댓글까지 포함하여 최근글 10개 조회
			String sql = "select * from (select A.*, Rownum Rnum from (select * from posts order by hit desc) A) where Rnum>=? and Rnum<=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PostDTO bean = new PostDTO();
				bean.setPOST_ID(rs.getInt(1));
				bean.setTITLE(rs.getString(2));
				bean.setCONTENT(rs.getString(3));
				bean.setWRITER_ID(rs.getString(4));
				bean.setHIT(rs.getInt(5));
				bean.setREG_DT(rs.getString(6).toString());
				bean.setMOD_DT(rs.getString(7).toString());
				
				v.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}
	
	
	//#4. 하나의 게시글 리턴
	public PostDTO getOneBoard(int num) {
		
		getCon();
		PostDTO bean =null;
		
		try {
			//조회수 증가
			String countsql = "update posts set readcount = readcount+1 where num= ?";
			pstmt = con.prepareStatement(countsql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			//하나의 게시글
			String sql = "select * from posts where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				bean = new PostDTO();
				bean.setPOST_ID(rs.getInt(1));
				bean.setTITLE(rs.getString(2));
				bean.setCONTENT(rs.getString(3));
				bean.setWRITER_ID(rs.getString(4));
				bean.setHIT(rs.getInt(5));
				bean.setREG_DT(rs.getString(6).toString());
				bean.setMOD_DT(rs.getString(7).toString());
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	//#5. 수정할 게시글 리턴
	public PostDTO getOneUpdateBoard(int num) {
		getCon();
		PostDTO bean = null;
		
		try {
			String sql = "select * from posts where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				bean = new PostDTO();
				bean.setPOST_ID(rs.getInt(1));
				bean.setTITLE(rs.getString(2));
				bean.setCONTENT(rs.getString(3));
				bean.setWRITER_ID(rs.getString(4));
				bean.setHIT(rs.getInt(5));
				bean.setREG_DT(rs.getString(6).toString());
				bean.setMOD_DT(rs.getString(7).toString());
			}
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	//#6. 게시글 수정
	public void UpdateBoard(int num, String subject, String content) {
		getCon();
		
		try {
			
			String sql = "update posts set subject=?, content=? where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setInt(3, num);
			pstmt.executeUpdate();
			con.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//#7. 게시글 삭제
	public void deleteBoard(int num) {
		
		getCon();
		
		try {
			
			String sql = "delete from posts where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			con.close();
			pstmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//#8. 답글 저장
	public void reInsertBoard(PostDTO bean) {
		
		getCon();
		int hit = 0;
		try {
			
			String levelsql = "update posts set re_level+1 where ref = ? and re_level>?";
			pstmt = con.prepareStatement(levelsql);
			pstmt.setInt(1, hit);
			pstmt.executeUpdate();
			
			String sql = "insert into posts values(board_seq.nextval,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getTITLE());
			pstmt.setString(2, bean.getCONTENT());
			pstmt.setString(3, bean.getWRITER_ID());
			pstmt.setInt(4, bean.getHIT());
			pstmt.executeUpdate();
			con.close();
			
			pstmt.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
}
