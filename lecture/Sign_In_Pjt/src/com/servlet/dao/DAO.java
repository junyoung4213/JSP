package com.servlet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import com.servlet.dto.DTO;

public class DAO {
	ServletContext sc; // 외부에서 들어온 ServletContext를 저장하기 위한 공간 선언.

	public DAO(ServletContext sc){	//DAO 생성시, ServletContext를 매개변수로 받아서 필드에 저장한다.
		this.sc=sc;	
	}

	public ArrayList<DTO> select() {
		ArrayList<DTO> list = new ArrayList<DTO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	

		try {
			Class.forName(sc.getInitParameter("driver"));
			con = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("id"), sc.getInitParameter("pw"));
			String sql = "SELECT * FROM members";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String mname = rs.getString("mname");
				String birth_date = rs.getString("birth_date");

				DTO dto = new DTO(id, pw, mname,birth_date);
				list.add(dto);

			}

		} catch (Exception e) {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.getStackTrace();
			}
		}

		return list;

	}
	
	public int add(DTO dto){
	
		
		Connection con = null;
		PreparedStatement pstmt = null;
		int result=0;
	

		try {
			Class.forName(sc.getInitParameter("driver"));
			con = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("id"), sc.getInitParameter("pw"));
			String sql = "INSERT INTO members(id,pw,mname,birth_date) VALUES(?,?,?,?);";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getMname());
			pstmt.setString(4, dto.getBirth_date());
			
			result = pstmt.executeUpdate();
			System.out.println(result);

			

		} catch (Exception e) {
			try {

				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e2) {
				e2.getStackTrace();
			}
		}
		
		return result;
	}

}
