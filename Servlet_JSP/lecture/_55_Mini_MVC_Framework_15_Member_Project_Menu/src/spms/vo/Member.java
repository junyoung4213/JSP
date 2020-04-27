package spms.vo;

import java.util.Date;

/*
VO(Value Object)
1) DB의 테이블/테이블 뷰와 1:1 관계
2) 객체간의 매개 변수(DTO)
  ; Data Transfer Object
3) View의 페이지에 보여줘야 할 데이터 그룹
*/  

/*
setter의 리턴형이 void이면 아래처럼 호출한다

member.setNo(10);
member.setName("hong");
member.setEmail("hong@daum.net");
	  
하지만 이렇게 chain형식으로 호출하기 위해
setter의 리턴값을 Member로 바꾸겠다.

member.setNo(10)
	  .setName("hong")
	  .setEmail("hong@daum.net");

*/
public class Member {
	protected int no;
	protected String name;
	protected String email;
	protected String password;
	protected Date createdDate;
	protected Date modifiedDate;
	
	public int getNo() {
		return no;
	}
	public Member setNo(int no) {
		this.no = no;
		return this;
	}
	public String getName() {
		return name;
	}
	public Member setName(String name) {
		this.name = name;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public Member setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public Member setPassword(String password) {
		this.password = password;
		return this;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Member setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		return this;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public Member setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
		return this;
	}
	
	
}







