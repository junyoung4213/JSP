package com.listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import com.dao.MemberDao;

public class ContextLoaderListener implements ServletContextListener {

//	BasicDataSource ds = null;

//	DBConnectionPool connPool;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 톰캣 서버에서 자동으로 커넥션을 관리해주므로 필요없다.
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			System.out.println("contextInitialized 준비...");
			ServletContext sc = sce.getServletContext();

			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/studydb");

			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);

			sc.setAttribute("memberDao", memberDao);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
