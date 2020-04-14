package com.servlet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.servlet.dto.StudyDTO;

public class StudyDAO {
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3307/studydb?serverTimezone=UTC";
	String id = "study";
	String pw = "study";
	
	public StudyDAO() {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<StudyDTO> select(){
		
		ArrayList<StudyDTO> list = new ArrayList<StudyDTO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			con=DriverManager.getConnection(url,id,pw);
			String sql = "SELECT * FROM members";
			pstmt=con.prepareStatement(sql);
			res=pstmt.executeQuery();
			
			while(res.next()) {
				String email=res.getString("email");
				String pwd=res.getString("pwd");
				String mname=res.getString("mname");
				
				StudyDTO studyDTO = new StudyDTO(email, pwd, mname);
				list.add(studyDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(res != null) res.close();
				if(pstmt != null) pstmt.close();
				if(con!=null)con.close();
			} catch (Exception e2) {
				e2.getStackTrace();
			}
		}
		
		return list;
		
	}
}
