package spms.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import spms.dao.MemberDao;
import spms.util.DBConnectionPool;

public class ContextLoaderListener implements ServletContextListener {

	DBConnectionPool connPool;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		connPool.closeAll();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			System.out.println("contextInitialized");

			ServletContext sc = event.getServletContext();

			connPool = new DBConnectionPool(sc.getInitParameter("driver"), sc.getInitParameter("url"),
					sc.getInitParameter("username"), sc.getInitParameter("password"));

			MemberDao memberDao = new MemberDao();
			memberDao.setDbConnectionPool(connPool);

			sc.setAttribute("memberDao", memberDao);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
