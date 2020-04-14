package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import com.dto.DTO;

public class DAO {
	ServletContext sc;

	public DAO(ServletContext sc) {
		this.sc = sc;
	}

	public ArrayList<DTO> select() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DTO> list = new ArrayList<DTO>();
		try {
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("id"), sc.getInitParameter("pw"));
			String sql = "SELECT email, pwd, mname FROM members";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String email = rs.getString("email");
				String pwd = rs.getString("pwd");
				String mname = rs.getString("mname");

				DTO dto = new DTO(email, pwd, mname);

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e2) {
			}
		}

		return list;
	}
}
