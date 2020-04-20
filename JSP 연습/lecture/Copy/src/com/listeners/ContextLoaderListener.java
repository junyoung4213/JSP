package com.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.context.ApplicationContext;

public class ContextLoaderListener implements ServletContextListener {

	static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 톰캣 서버에서 자동으로 커넥션을 관리해주므로 필요없다.
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			System.out.println("contextInitialized 준비...");
			ServletContext sc = sce.getServletContext();

			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));

			applicationContext = new ApplicationContext(propertiesPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
