package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.vo.Project;

//@Component("projectDao")
public class MySqlProjectDao implements ProjectDao {
	DataSource ds;

	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<Project> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelect = "SELECT pno,pname,sta_date,end_date,state FROM projects ORDER BY pno DESC";

		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelect);

			ArrayList<Project> projects = new ArrayList<Project>();
			while (rs.next()) {
				projects.add(new Project().setNo(rs.getInt("pno")).setTitle(rs.getString("pname"))
						.setStartDate(rs.getDate("sta_date")).setEndDate(rs.getDate("end_date"))
						.setState(rs.getInt("state")));
			}

			return projects;

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int insert(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		final String sqlInsert = "INSERT INTO projects(pname,content,sta_date,end_date,state,cre_date,tags) VALUES(?,?,?,?,0,NOW(),?)";

		try {
			connection = ds.getConnection();
			pstmt = connection.prepareStatement(sqlInsert);
			pstmt.setString(1, project.getTitle());
			pstmt.setString(2, project.getContent());
			pstmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			pstmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			pstmt.setString(5, project.getTags());

			return pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Project selectOne(int no) throws Exception {

		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelectOne = "SELECT pno,pname,content,sta_date,end_date,state,cre_date,tags FROM projects WHERE pno="
				+ no;
		try {

			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelectOne);

			if (rs.next()) {
				return new Project().setNo(rs.getInt("pno")).setTitle(rs.getString("pname"))
						.setContent(rs.getString("content")).setStartDate(rs.getDate("sta_date"))
						.setEndDate(rs.getDate("end_date")).setState(rs.getInt("state"))
						.setCreatedDate(rs.getDate("cre_date")).setTags(rs.getString("tags"));
			} else {
				throw new Exception("해당 번호의 프로젝트 못찾음");
			}

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int update(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		final String sqlUpdate = "UPDATE projects SET pname=?, content=?,sta_date=?, end_date=?, state=?, tags=? WHERE pno=?";

		try {

			connection = ds.getConnection();
			pstmt = connection.prepareStatement(sqlUpdate);
			pstmt.setString(1, project.getTitle());
			pstmt.setString(2, project.getContent());
			pstmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			pstmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			pstmt.setInt(5, project.getState());
			pstmt.setString(6, project.getTags());
			pstmt.setInt(7, project.getNo());

			return pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int delete(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		final String sqlDelete = "DELETE FROM projects WHERE pno=" + no;

		try {

			connection = ds.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate(sqlDelete);

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
