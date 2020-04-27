package spms.listeners;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import spms.controls.LogInController;
import spms.controls.LogOutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MySqlMemberDao;

/*
 * JNDI
 * tomcat서버에 자원에 대한 고유 이름 정의
 * 어플리케이션에서 tomcat의 자원을 접근할 때 사용
 * 1) java:comp/env 	 		- 응용 프로그램 환경 항목
 * 2) java:comp/env/jdbc 		- JDBC 데이터 소스 
 * 3) java:comp/ejb	     		- EJB 컴포넌트
 * 4) java:comp/UserTransaction - UserTransaction 객체
 * 5) java:comp/env/mail		- JavaMail 연결 객체
 * 6) java:comp/env/rul			- URL 정보
 * 7) java:comp/env/jms			- JMS 연결 객체
*/

public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// tomcat 서버가 관리하는 DataSource는
		// tomcat이 관리하므로 우리가 close할 필요 없다.
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			System.out.println("contextInitialized");

			ServletContext sc = event.getServletContext();

			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/studydb");

			// 다른 DBMS를 접속하려면 MemberDao를 상속받아서
			// 구현 클래스를 만들어서 이곳에서 주입해주면 된다
			
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
