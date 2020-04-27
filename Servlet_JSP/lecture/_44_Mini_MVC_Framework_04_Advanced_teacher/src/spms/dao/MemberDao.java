package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.vo.Member;

public class MemberDao {
	
	DataSource ds = null;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}

//	DBConnectionPool connPool;
//
//	public void setDbConnectionPool(DBConnectionPool connPool) {
//		this.connPool = connPool;
//	}

	public List<Member> selectList() throws Exception {

		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelect = "SELECT mno,mname,email,cre_date" + "\r\n" + "FROM members" + "\r\n"
				+ "ORDER BY mno ASC";

		try {
			connection = ds.getConnection();
			//connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelect);

			ArrayList<Member> members = new ArrayList<Member>();

			while (rs.next()) {
				members.add(new Member().setNo(rs.getInt("mno")).setName(rs.getString("mname"))
						.setEmail(rs.getString("email")).setCreatedDate(rs.getDate("cre_date")));
			}

			return members;

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			/* DataSource 객체 내부의 Pool에서 대여한 후
			 * close()시 반납처리
			 * 여기서의 close()는 닫는 것이 아니라 반납
			 * */
			try {
				if(connection != null)
					connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
//			if (connection != null) {
//				connPool.returnConnection(connection);
//			}
		}
	}

	public int insert(Member member) throws Exception {
		Connection connection = null;
		int result = 0;
		PreparedStatement stmt = null;
		final String sqlInsert = "INSERT INTO members(email,pwd," + "\r\n" + "mname,cre_date,mod_date)" + "\r\n"
				+ "VALUES(?, ?, ?, NOW(), NOW())";

		try {
			connection = ds.getConnection();
			//connection = connPool.getConnection();
			stmt = connection.prepareStatement(sqlInsert);
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			result = stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(connection != null)
					connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}			
//			if (connection != null) {
//				connPool.returnConnection(connection);
//			}
		}

		return result;
	}

	public int delete(int no) throws Exception {
		Connection connection = null;
		int result = 0;
		Statement stmt = null;
		final String sqlDelete = "DELETE FROM MEMBERS WHERE MNO=";

		try {
			connection = ds.getConnection();
			//connection = connPool.getConnection();
			stmt = connection.createStatement();
			result = stmt.executeUpdate(sqlDelete + no);

		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if(connection != null)
					connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}			
//			if (connection != null) {
//				connPool.returnConnection(connection);
//			}
		}

		return result;
	}

	public Member selectOne(int no) throws Exception {
		Connection connection = null;
		Member member = null;
		Statement stmt = null;
		ResultSet rs = null;

		final String sqlSelectOne = "SELECT MNO,EMAIL,MNAME,CRE_DATE FROM MEMBERS" + " WHERE MNO=";

		try {
			connection = ds.getConnection();
			//connection = connPool.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelectOne + no);
			if (rs.next()) {
				member = new Member().setNo(rs.getInt("MNO")).setEmail(rs.getString("EMAIL"))
						.setName(rs.getString("MNAME")).setCreatedDate(rs.getDate("CRE_DATE"));

			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if(connection != null)
					connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}			
//			if (connection != null) {
//				connPool.returnConnection(connection);
//			}
		}

		return member;
	}

	public int update(Member member) throws Exception {
		Connection connection = null;
		int result = 0;
		PreparedStatement stmt = null;
		final String sqlUpdate = "UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=now()" + " WHERE MNO=?";
		try {
			connection = ds.getConnection();
			//connection = connPool.getConnection();
			stmt = connection.prepareStatement(sqlUpdate);
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			result = stmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if(connection != null)
					connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}			
//			if (connection != null) {
//				connPool.returnConnection(connection);
//			}
		}

		return result;
	}

	public Member exist(String email, String password) throws Exception {
		Connection connection = null;
		Member member = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		final String sqlExist = "SELECT MNAME,EMAIL FROM MEMBERS" + " WHERE EMAIL=? AND PWD=?";

		try {
			connection = ds.getConnection();
			//connection = connPool.getConnection();
			stmt = connection.prepareStatement(sqlExist);
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				member = new Member().setName(rs.getString("MNAME")).setEmail(rs.getString("EMAIL"));
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
			try {
				if(connection != null)
					connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}			
//			if (connection != null) {
//				connPool.returnConnection(connection);
//			}
		}

		return member;
	}
}
