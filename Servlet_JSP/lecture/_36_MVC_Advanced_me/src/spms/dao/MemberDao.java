package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import spms.vo.Member;

public class MemberDao {

	Connection connection;

	/*
	 * 의존성 제어(DI, Dependency Injection) 역제어(IoC, Inversion of Control)
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// 회원정보를 뽑아서 List에 Member 객체로 담아서 리턴하는 메서드
	public List<Member> selectList() throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelect = "SELECT mno, mname, email, cre_date FROM members ORDER BY mno ASC";

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelect);

			ArrayList<Member> members = new ArrayList<Member>();

			// db에서 꺼내서 member 객체에 담자
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
		}
	}

	// 회원가입을 시도하는 회원의 정보를 Member 객체로 받아서 mysql에 insert 하는 메서드
	public int insert(Member member) throws Exception {
		PreparedStatement stmt = null;
		int result = 0;
		final String sqlInsert = "INSERT INTO members(email,pwd," + "\r\n" + "mname,cre_date,mod_date)" + "\r\n"
				+ "VALUES(?, ?, ?, NOW(), NOW())";

		try {
			stmt = connection.prepareStatement(sqlInsert);
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			result = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 삭제하고 싶은 회원의 번호(PK)를 받아서 mysql에 삭제요청하는 메서드
	public int delete(int no) throws Exception {
		Statement stmt = null;
		int result = 0;

		try {
			stmt = connection.createStatement();
			stmt.executeUpdate("DELETE FROM members WHERE mno=" + no);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 위에서 삭제 처리를 하기 전에, 회원번호(PK)가 존재하는지 확인하고 존재한다면 그 회원의 정보를 Member 객체에 담아 리턴하는 메서드
	public Member selectOne(int no) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		Member member;
		final String sqlSelect = "SELECT mno,email,mname,cre_date" + "\r\n" + "FROM members WHERE mno=" + "\r\n" + no;

		try {

			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			if (rs.next()) {
				member = new Member().setNo(rs.getInt("mno")).setEmail(rs.getString("email"))
						.setName(rs.getString("mname")).setCreatedDate(rs.getDate("cre_date"));
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다");
			}

			return member;

		} catch (Exception e) {
			throw new ServletException(e);
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
		}
	}

	// 정보를 수정하기 위해 Member 객체를 받아서 mysql에 update 요청을 하는 메서드
	public int update(Member member) throws Exception {

		PreparedStatement stmt = null;
		final String sqlUpdate = "UPDATE members SET email=?," + "\r\n" + "mname=?,mod_date=now() WHERE mno=?";
		int result = 0;
		try {

			stmt = connection.prepareStatement(sqlUpdate);
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			result = stmt.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 이메일과 비밀번호를 매개변수로 받아서 로그인 시 그 회원의 정보가 있는지를 확인하는 메서드
	public Member exist(String email, String password) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		final String sqlLogIn = "SELECT mname,email FROM members WHERE email=? AND pwd=?";

		try {
			pstmt = connection.prepareStatement(sqlLogIn);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			// 로그인 성공
			if (rs.next()) {
				// vo객체에 로그인 정보를 저장한다
				member = new Member().setEmail(rs.getString("email")).setName(rs.getString("mname"));
				return member;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}
}
