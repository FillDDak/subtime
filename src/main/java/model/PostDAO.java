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
		
		int ref=0;
		int re_step = 1;
		int re_level = 1;
		
		try {
			
			String resql = "select max(ref) from board";
			pstmt=con.prepareStatement(resql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				ref = rs.getInt(1)+1;
			}
			
			//실제 게시글 테이블에 저장
			String sql = "insert into board values (board_seq.nextval,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());
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
			
			String sql = "select count(*) from board";
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
			String sql = "select * from (select A.*, Rownum Rnum from (select * from board order by ref desc, re_step asc) A) where Rnum>=? and Rnum<=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PostDTO bean = new PostDTO();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getString(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));;
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				
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
			String countsql = "update board set readcount = readcount+1 where num= ?";
			pstmt = con.prepareStatement(countsql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			//하나의 게시글
			String sql = "select * from board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				bean = new PostDTO();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getString(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));;
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
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
			String sql = "select * from board where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				bean = new PostDTO();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getString(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));;
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
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
			
			String sql = "update board set subject=?, content=? where num = ?";
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
			
			String sql = "delete from board where num = ?";
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
		int ref = bean.getRef();
		int re_step = bean.getRe_step();
		int re_level = bean.getRe_level();
		
		try {
			
			String levelsql = "update board set re_level+1 where ref = ? and re_level>?";
			pstmt = con.prepareStatement(levelsql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_level);
			pstmt.executeUpdate();
			
			String sql = "insert into board values(board_seq.nextval,?,?,?,?,sysdate,?,?,?,0,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());
			
			pstmt.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
}
