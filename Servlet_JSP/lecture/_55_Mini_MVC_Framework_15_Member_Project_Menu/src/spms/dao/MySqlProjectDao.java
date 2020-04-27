package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.annotation.Component;
import spms.vo.Project;

@Component("projectDao")
public class MySqlProjectDao implements ProjectDao{
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<Project> selectList() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelect = "SELECT pno,pname," + "\r\n"
				+ "sta_date,end_date,state FROM" + "\r\n"
				+ "projects ORDER BY pno DESC";
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			
			ArrayList<Project> projects = new ArrayList<Project>();
			while(rs.next()) {
				projects.add(new Project()
					.setNo(rs.getInt("pno"))
					.setTitle(rs.getString("pname"))
					.setStartDate(rs.getDate("sta_date"))
					.setEndDate(rs.getDate("end_date"))
					.setState(rs.getInt("state")));
			}
			
			return projects;
			
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(rs!=null) rs.close();}
			catch(Exception e) {e.printStackTrace();}
			try {if(stmt!=null) stmt.close();}
			catch(Exception e) {e.printStackTrace();}
			try {if(connection!=null) connection.close();}
			catch(Exception e) {e.printStackTrace();}
		}		
	}

	
	@Override
	public int insert(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		final String sqlInsert = "INSERT INTO projects" + "\r\n"
				+ "(pname,content,sta_date,end_date," + "\r\n"
				+ "state,cre_date,tags) VALUES" + "\r\n"
				+ "(?,?,?,?,0,NOW(),?)";
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(sqlInsert);
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			stmt.setString(5, project.getTags());
			
			return stmt.executeUpdate();
					
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(stmt!=null) stmt.close();}
			catch(Exception e) {e.printStackTrace();}
			try {if(connection!=null) connection.close();}
			catch(Exception e) {e.printStackTrace();}			
		}
	}

	@Override
	public Project selectOne(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		final String sqlSelectOne = "SELECT" + "\r\n"
				+ "pno,pname,content,sta_date," + "\r\n"
				+ "end_date,state,cre_date,tags" + "\r\n"
				+ "FROM projects WHERE pno=" + no;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelectOne);
			if(rs.next()) {
				return new Project()
					.setNo(rs.getInt("pno"))
					.setTitle(rs.getString("pname"))
					.setContent(rs.getString("content"))
					.setStartDate(rs.getDate("sta_date"))
					.setEndDate(rs.getDate("end_date"))
					.setState(rs.getInt("state"))
					.setCreatedDate(rs.getDate("cre_date"))
					.setTags(rs.getString("tags"));
			}else {
				throw new Exception("해당 번호의 프로젝트 못찾음");
			}
			
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(rs!=null) rs.close();}
			catch(Exception e) {e.printStackTrace();}
			try {if(stmt!=null) stmt.close();}
			catch(Exception e) {e.printStackTrace();}
			try {if(connection!=null) connection.close();}
			catch(Exception e) {e.printStackTrace();}
		}	
	}

	@Override
	public int update(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		final String sqlUpdate = "UPDATE projects SET" + "\r\n"
								+ "pname=?," + "\r\n"
								+ "content=?," + "\r\n"
								+ "sta_date=?," + "\r\n"
								+ "end_date=?," + "\r\n"
								+ "state=?," + "\r\n"
								+ "tags=?" + "\r\n"
								+ "WHERE pno=?";
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(sqlUpdate);
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(
						project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(
					project.getEndDate().getTime()));
			stmt.setInt(5, project.getState());
			stmt.setString(6, project.getTags());
			stmt.setInt(7, project.getNo());
			
			return stmt.executeUpdate();
			
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(stmt!=null) stmt.close();}
			catch(Exception e) {e.printStackTrace();}
			try {if(connection!=null) connection.close();}
			catch(Exception e) {e.printStackTrace();}
		}
	}

	@Override
	public int delete(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		final String sqlDelete = "DELETE FROM" + "\r\n"
						+ "projects WHERE pno=" + no;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate(sqlDelete);
		}catch(Exception e) {
			throw e;
		}finally {
			try {if(stmt!=null) stmt.close();}
			catch(Exception e) {e.printStackTrace();}
			try {if(connection!=null) connection.close();}
			catch(Exception e) {e.printStackTrace();}
		}
	}
}







