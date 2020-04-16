package spms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import spms.vo.Member;

public class MemberDao {

	Connection connection;

	/*의존성 제어(DI, Dependency Injection)
	 * 역제어(IoC, Inversion of Control)*/	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public List<Member> selectList() throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelect="SELECT mno, mname, email, cre_date FROM members ORDER BY mno ASC";

		try {
			stmt=connection.createStatement();
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
}
