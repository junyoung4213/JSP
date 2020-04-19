package com.listeners;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.dao.MemberDao;

public class ContextLoaderListener implements ServletContextListener {
	
	BasicDataSource ds = null;

//	DBConnectionPool connPool;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed 마무리...");

		try {
			if(ds!=null)
				ds.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("contextInitialized 준비...");
		try {
			ServletContext sc = sce.getServletContext();
			
			ds = new BasicDataSource();
			ds.setDriverClassName(sc.getInitParameter("driver"));
			ds.setUrl(sc.getInitParameter("url"));
			ds.setUsername(sc.getInitParameter("username"));
			ds.setPassword(sc.getInitParameter("password"));
			
			/*
			 * connPool = new
			 * DBConnectionPool(sc.getInitParameter("driver"),sc.getInitParameter("url"),
			 * sc.getInitParameter("username"), sc.getInitParameter("password"));
			 */
			
			MemberDao memberDao = new MemberDao();
			memberDao.setDataSource(ds);

			sc.setAttribute("memberDao", memberDao);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
