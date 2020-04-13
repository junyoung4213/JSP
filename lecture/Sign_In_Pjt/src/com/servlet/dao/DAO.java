package com.servlet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;

import com.servlet.dto.DTO;

@WebServlet("/DAO")
public class DAO {
	ServletContext sc; // 외부에서 들어온 ServletContext를 저장하기 위한 공간 선언.
	
//	@Override
//	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//		try {
//			Class.forName(this.getInitParameter("driver"));
//
//		} catch (Exception e) {
//		}
//	}
	
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
				String email = rs.getString("email");
				String pwd = rs.getString("pwd");
				String mname = rs.getString("mname");

				DTO dto = new DTO(email, pwd, mname);
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

}
