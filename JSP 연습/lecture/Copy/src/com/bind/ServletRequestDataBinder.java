package com.bind;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

public class ServletRequestDataBinder {

	public static Object bind(ServletRequest request, Class<?> dataType, String dataName) throws Exception {

		if (isPrimitiveType(dataType)) {
			return createValueObject(dataType, request.getParameter(dataName));
		}

		// 일반 vo객체일 경우
		// 클라이언트가 전송한 매개변수들의 이름들
		Set<String> paramNames = request.getParameterMap().keySet();

		// 데이터 타입대로 객체를 생성
		Object dataObject = dataType.newInstance();
		// 클라이언트 매개변수를 객체의 필드에 저장
		// setter메서드로 저장
		Method m = null; // setter 메서드를 저장
		// 클라이언트가 전송한 매개변수
		for (String paramName : paramNames) {
			// 클래스의 매개변수에 해당하는 setter메서드 얻기
			// ex) no 매개변수 -> setNo 찾기
			// ex) email 매개변수 -> setEmail 찾기
			m = findSetter(dataType, paramName);
			if(m!=null) {
				m.invoke(dataObject, createValueObject(m.getParameterTypes()[0],request.getParameter(paramName)));
			}
		}
		return dataObject;
	}

	public static boolean isPrimitiveType(Class<?> type) {
		if (type.getName().equals("int") || type == Integer.class || type.getName().equals("long") || type == Long.class
				|| type.getName().equals("float") || type == Float.class || type.getName().equals("double")
				|| type == Double.class || type.getName().equals("boolean") || type == Boolean.class
				|| type == Date.class || type == String.class) {
			return true;
		} else {
			return false;
		}

	}

	public static Object createValueObject(Class<?> type, String value) throws ParseException {
		if (type.getName().equals("int") || type == Integer.class) {
			return new Integer(value);
		} else if (type.getName().equals("long") || type == Long.class) {
			return new Long(value);
		} else if (type.getName().equals("float") || type == Float.class) {
			return new Float(value);
		} else if (type.getName().equals("double") || type == Double.class) {
			return new Double(value);
		} else if (type.getName().equals("boolean") || type == Boolean.class) {
			return new Boolean(value);
		} else if (type.getName().equals("long") || type == Long.class) {
			return new Long(value);
		} else if (type == Date.class) {
			final String DATE_PATTERN = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
			Date date = sdf.parse(value);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			return new java.util.Date(java.sql.Date.valueOf(sdf.format(cal.getTime())).getTime());
		} else {
			return value;
		}
	}

	private static Method findSetter(Class<?> type, String name) {
		Method[] methods = type.getMethods();
		
		String propName = null;
		for(Method m : methods) {
			if(!m.getName().startsWith("set")) {
				continue;
			}
			// 메서드 시작이름이 set일 때 
			// set을 제외한 이름 == 프로퍼티
			propName = m.getName().substring(3);
			propName = propName.toLowerCase();
			if(propName.equals(name.toLowerCase())) {
				// 해당 프로퍼티의 setter를 찾으면 그 메서드를 리턴
				return m;
			}
			
		}
		return null;
	}
}
