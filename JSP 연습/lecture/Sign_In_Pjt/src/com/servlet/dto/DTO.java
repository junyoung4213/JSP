package com.servlet.dto;

public class DTO {

	String id;
	String pw;
	String mname;
	String birth_date;
	
	public DTO(String id, String pw, String mname,String birth_date) {
		this.id=id;
		this.pw=pw;
		this.mname=mname;
		this.birth_date=birth_date;
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public String getMname() {
		return mname;
	}

	public String getBirth_date() {
		return birth_date;
	}

	
	
}
