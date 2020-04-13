package com.servlet.dto;

public class DTO {

	String email;
	String pwd;
	String mname;
	
	public DTO(String email, String pwd, String mname) {
		this.email=email;
		this.pwd=pwd;
		this.mname=mname;
	}

	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}

	public String getMname() {
		return mname;
	}
	
}
