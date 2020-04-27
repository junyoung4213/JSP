package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import spms.annotation.Component;


/* application-context.properties 파일에 등록된 클래스나
 * @Component 애노테이션이 설정된 클래스 객체를 생성하는 역할
 * */
public class ApplicationContext {

	// 생성된 객체들을 저장
	// path, 객체
	Hashtable<String, Object> objTable =
			new Hashtable<String, Object>();
	
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	public ApplicationContext(String propertiesPath) throws Exception{
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		prepareObjects(props);	// 프로퍼티 파일에 등록된 클래스 객체 생성
		prepareAnnotationObjects();	// 애노테이션 포함 클래스 객체 생성
		injectDependency();		// dataSource, memberDao 주입
	}
	
	private void prepareObjects(Properties props) throws Exception{
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		
		for(Object item : props.keySet()) {
			key = (String)item;
			value = props.getProperty(key);
			if(key.startsWith("jndi.")) {
				// tomcat의 DataSource객체를 찾아서 얻어와서 저장
				objTable.put(key, ctx.lookup(value));
			}else {
				// 직접 클래스 이름을 가지고 객체를 생성
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	private void prepareAnnotationObjects()
								throws Exception{
		/* Reflections(패키지) : 해당 패키지 하위를 모두 찾는다
		 * Reflections("spms") : spms 하위를 모두 찾는다
		 * Reflections("spms.controls") : spms.controls 하위를 모두 찾는다
		 * Reflections("") : classpath 하위를 모두 찾는다*/
		Reflections reflector = new Reflections("");
		
		// @Component 애노테이션을 가진 type만 추출
		Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
		String key = null;
		for(Class<?> clazz : list) {
			key = clazz.getAnnotation(Component.class).value();
			objTable.put(key, clazz.newInstance());
		}
	}
	
	private void injectDependency() throws Exception{
		for(String key : objTable.keySet()) {
			if(!key.startsWith("jndi.")) {
				callSetter(objTable.get(key));
			}
		}
	}
	
	private void callSetter(Object obj) throws Exception{
		Object dependency = null;
		for(Method m : obj.getClass().getMethods()) {
			// 메서드 중에 setter를 찾아라
			if(m.getName().startsWith("set")) {
				// 현재 objTable로부터 첫번째 매개변수에
				// 해당하는 클래스 객체를 찾아와라
				dependency = findObjectByType(m.getParameterTypes()[0]);
				if(dependency != null) {
					// setter를 호출하고 dependency를 주입해라
					m.invoke(obj, dependency);
				}					
			}
		}
	}
	
	private Object findObjectByType(Class<?> type) {
		for(Object obj : objTable.values()) {
			if(type.isInstance(obj)) {
				return obj;
			}
		}
		return null;
	}
}










