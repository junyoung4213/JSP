package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

/*
 * application-context.properties 파일을 읽어서 
 * 등록된 클래스 객체를 생성하는 역할
*/

public class ApplicationContext {

	// 생성된 객체들을 저장
	// path, 객체
	Hashtable<String, Object> objTable = new Hashtable<String, Object>();

	public Object getBean(String key) {
		return objTable.get(key);
	}

	public ApplicationContext(String propertiesPath) throws Exception {
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));

		prepareObjects(props); // 객체 생성
		injectDependency(); // memberDao 주입
	}

	private void prepareObjects(Properties props) throws Exception {
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		for (Object item : props.keySet()) {
			key = (String) item;
			value = props.getProperty(key);
			if (key.startsWith("jndi.")) {
				// tomcat의 DataSource 객체를 찾아서 얻어와서 저장
				objTable.put(key, ctx.lookup(value));
			} else {
				// 직접 클래스 이름을 가지고 객체를 생성
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}

	private void injectDependency() throws Exception {
		for (String key : objTable.keySet()) {
			if (!key.startsWith("jndi.")) {
				callSetter(objTable.get(key));

			}
		}
	}

	private void callSetter(Object obj) throws Exception {
		Object dependency = null;
		for (Method m : obj.getClass().getMethods()) {
			// 메서드 중에 setter를 찾아라
			if (m.getName().startsWith("set")) {
				// 현재 objTable로부터 첫번째 매개변수에
				// 해당하는 클래스 객체를 찾아와라
				dependency = findObjectByType(m.getParameterTypes()[0]);
				if (dependency != null) {
					// setter를 호출하고 dependency를 주입해라
					m.invoke(obj, dependency);
				}
			}
		}
	}

	private Object findObjectByType(Class<?> type) {
		for (Object obj : objTable.values()) {
			if (type.isInstance(obj)) {
				return obj;
			}
		}
		return null;
	}

}
