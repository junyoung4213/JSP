package com.servlet.dto;

public class StudyDTO {

	String email;
	String pwd;
	String mname;
	public StudyDTO(String email, String pwd, String mname) {
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
