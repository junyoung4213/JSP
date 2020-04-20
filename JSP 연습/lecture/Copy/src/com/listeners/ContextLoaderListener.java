package com.listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import com.controls.LogInController;
import com.controls.LogOutController;
import com.controls.MemberAddController;
import com.controls.MemberDeleteController;
import com.controls.MemberListController;
import com.controls.MemberUpdateController;
import com.dao.MySqlMemberDao;

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

			MySqlMemberDao memberDao = new MySqlMemberDao();
			memberDao.setDataSource(ds);

			sc.setAttribute("/auth/login.do", new LogInController().setMemberDao(memberDao));
			sc.setAttribute("/auth/logout.do", new LogOutController());
			sc.setAttribute("/member/add.do", new MemberAddController().setMemberDao(memberDao));
			sc.setAttribute("/member/delete.do", new MemberDeleteController().setMemberDao(memberDao));
			sc.setAttribute("/member/list.do", new MemberListController().setMemberDao(memberDao));
			sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
			// 향후 자동화 작업을 위해서 이렇게 변경한다
			// MemberDao는 Controller클래스 객체에 DI 작업을 한다

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
