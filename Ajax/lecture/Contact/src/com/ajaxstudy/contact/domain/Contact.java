package com.ajaxstudy.contact.domain;

/*{"no":1,"name":"홍길동",tel:"010-1111-1111","address":"서울시"}
 * 
 * [
 * 		{"no":1,"name":"홍길동",tel:"010-1111-1111","address":"서울시"},
 * 		{"no":2,"name":"임꺽정",tel:"010-1111-1111","address":"서울시"},
 * 		{"no":3,"name":"장길산",tel:"010-1111-1111","address":"서울시"}
 * ]
 * 

*/
public class Contact {
	private long no;
	private String name;
	private String tel;
	private String address;

	public Contact() {
		super();
	}

	public Contact(long no, String name, String tel, String address) {
		super();
		this.no = no;
		this.name = name;
		this.tel = tel;
		this.address = address;
	}

	public long getNo() {
		return no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Contact [no=" + no + ", name=" + name + ", tel=" + tel + ", address=" + address + "]";
	}

}
