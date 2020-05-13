package com.ajaxstudy.contact.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;

/* java 객체 <-> json 데이터
 * 
*/
public class Converter {
	private static Gson gson;
	static {
		gson = new Gson();
	}
	
	// Java Obj -> Json 문자열 변환
	public static String convertToJson(Object obj) {
		return gson.toJson(obj);
	}
	
	// json 문자열 -> (해당 클래스 타입의) Java Obj로 변환
	public static <T> T convertFromJson(String json, Class<T> type) {
		return gson.fromJson(json, type);
	}
	
	// 클라이언트와 연결된 스트림에 데이터를 읽어서 
	// Java Obj로 변환
	public static <T> T convertFromJSONStream(InputStream is, Class<T> type) {
		try {
			Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			return gson.fromJson(reader, type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
