package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.annotation.Component;
import com.vo.Member;

@Component("memberDao")
public class MySqlMemberDao implements MemberDao {
	
	DataSource ds = null;
	
	public void setDataSource(DataSource ds) {
		this.ds=ds;
	}
	
	
	
	/*
	 * DBConnectionPool connPool;
	 * 
	 * public void setDBConnectionPool(DBConnectionPool connPool) { this.connPool =
	 * connPool; }
	 */
	public List<Member> selectList() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		final String select = "SELECT mno,mname,email,cre_date FROM members";

		try {
			conn = ds.getConnection();
//			conn = connPool.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(select);
			ArrayList<Member> members = new ArrayList<Member>();
			while (rs.next()) {
				Member member = new Member().setNo(rs.getInt(1)).setName(rs.getString(2)).setEmail(rs.getString(3))
						.setCreatedDate(rs.getDate(4));
				members.add(member);
			}
			return members;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				/*
				 * DataSource 객체 내부의 Pool에서 대여한 후
				 * close()시 반납처리
				 * 여기서의 close()는 닫는 것이 아니라 반납
				*/
				if(conn!=null) conn.close();
				
				/*
				 * if (conn != null) connPool.returnConnection(conn);
				 */
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

	}

	public int insert(Member member) throws Exception {
		Connection conn = null;
		int result = 0;
		PreparedStatement pstmt = null;
		final String sql = "INSERT INTO members(email,pwd,mname,cre_date,mod_date) VALUES(?,?,?,now(),now())";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public Member selectOne(int no) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Member member = null;
		final String sqlSelect = "SELECT mno, email, mname, cre_date FROM members WHERE mno=" + no;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);

			if (rs.next()) {

				member = new Member().setNo(rs.getInt(1)).setEmail(rs.getString(2)).setName(rs.getString(3))
						.setCreatedDate(rs.getDate(4));

			} else {
				throw new Exception("해당 회원을 찾을 수 없습니다");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return member;
	}

	public int update(Member member) throws Exception {
		Connection conn = null;
		int result = 0;
		PreparedStatement pstmt = null;
		final String sqlUpdate = "UPDATE members SET email=?, mname=?, mod_date=now() WHERE mno=?";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getName());
			pstmt.setInt(3, member.getNo());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public int delete(int no) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		final String sqlDelete = "DELETE FROM members WHERE mno=?";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sqlDelete);

			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

	}

	public Member exist(String email, String password) throws Exception {
		Connection conn = null;
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final String sqlSelect = "SELECT mname,email FROM members WHERE email=? AND pwd=?";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sqlSelect);

			pstmt.setString(1, email);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				member = new Member().setName(rs.getString("mname")).setEmail(rs.getString("email"));

			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return member;
	}

}
