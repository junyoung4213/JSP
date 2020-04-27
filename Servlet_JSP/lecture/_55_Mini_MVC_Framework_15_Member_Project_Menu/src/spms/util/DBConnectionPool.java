package spms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
	String url;
	String username;
	String password;
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	public DBConnectionPool(String driver, String url,
			String username, String password) throws Exception{
		this.url = url;
		this.username = username;
		this.password = password;
		
		Class.forName(driver);
	}
	
	// Connection 객체를 요청하면 전달
	public Connection getConnection() throws Exception{
		
		// 여유분이 있는 경우
		if(connList.size() > 0) {
			Connection conn = connList.remove(0);
			// 현재 DBMS와 접속이 잘 되어 있는 경우
			// DBMS에서 일정시간 아무 요청이 없으면
			// timeout에 의해 연결이 끊어진다.
			if(conn.isValid(10)) {
				return conn;
			}
		}
		
		// Connection 객체 여유분이 없는 경우
		return DriverManager.getConnection(url, username, password);
	}

	// 다 사용한 Connection 객체를 반납
	public void returnConnection(Connection conn) throws Exception{
		connList.add(conn);
	}
	
	// 종료시 모든 Connection 객체 닫기
	public void closeAll() {
		for(Connection conn : connList) {
			try {
				conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}


















