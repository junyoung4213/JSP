package spms.listeners;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp.BasicDataSource;

import spms.dao.MemberDao;

public class ContextLoaderListener implements ServletContextListener {

	BasicDataSource ds;
	// DBConnectionPool connPool;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			if (ds != null)
				ds.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// connPool.closeAll();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			System.out.println("contextInitialized");

			ServletContext sc = event.getServletContext();
			
			ds = new BasicDataSource();
			ds.setDriverClassName(sc.getInitParameter("driver"));
			ds.setUrl(sc.getInitParameter("url"));
			ds.setUsername(sc.getInitParameter("username"));
			ds.setPassword(sc.getInitParameter("password"));
			
			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);

			/*
			 * connPool = new DBConnectionPool(sc.getInitParameter("driver"),
			 * sc.getInitParameter("url"), sc.getInitParameter("username"),
			 * sc.getInitParameter("password"));
			 * 
			 * MemberDao memberDao = new MemberDao();
			 * memberDao.setDbConnectionPool(connPool);
			 */
			sc.setAttribute("memberDao", memberDao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
