package spms.listeners;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import spms.context.ApplicationContext;

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

	static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// tomcat 서버가 관리하는 DataSource는
		// tomcat이 관리하므로 우리가 close할 필요 없다.
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			System.out.println("contextInitialized");
			
			applicationContext = new ApplicationContext();
			
			// sqlSessionFactory객체를 만들 때 사용하는 환경 설정 파일
			String resource = "spms/dao/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			
			SqlSessionFactory sqlSessionFactory= new SqlSessionFactoryBuilder().build(inputStream);
			
			applicationContext.addBean("sqlSessionFactory", sqlSessionFactory);

			ServletContext sc = event.getServletContext();

			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));

			applicationContext.prepareObjectsByProperties(propertiesPath);
			applicationContext.prepareObjectsByAnnotation("");
			applicationContext.injectDependency();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
