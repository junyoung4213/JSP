package com.ajaxstudy.contact.util;

import java.util.ArrayList;
import java.util.List;

import com.ajaxstudy.contact.domain.Contact;
import com.ajaxstudy.contact.domain.ContactList;

/* 연락처 리스트를 저장
 * DB에 연결하지 않고 자체 객체에 리스트를 내장
 * */
public class SampleDAO {
	private static String namelist = "김수현,송중기,송혜교,김지원,조진웅,박해진," + "진구,설현,하니,소진,혜리,원더걸스,베이비복스,"
			+ "소녀시대,레드벨벳,원더우먼,슈퍼맨,배트맨," + "헐크,이소룡,성룡,이연걸";
	private static List<Contact> contacts;
	private static long nextNo = 0;

	// 데이터 초기화(주소록 정보 생성)
	static {
		String[] names = namelist.split(",");
		contacts = new ArrayList<Contact>();
		for (int i = 0; i < names.length; i++) {
			nextNo++;
			String tel = "010-1111-22" + (i + 10);
			Contact c = new Contact(nextNo, names[i], tel, "서울시");
			contacts.add(0, c);
		}
	}

	// 전체 주소 요청
	public static ContactList getContacts() {
		ContactList cList = new ContactList(0, 0, contacts.size(), contacts);
		return cList;
	}

	// 특정 페이지와 페이지에 포함될 주소 개수 요청
	// 일부 데이터만 전송
	public static ContactList getContacts(int pageno, int pagesize) {
		int startIndex = (pageno - 1) * pagesize;
		int endIndex = startIndex + pagesize;

		List<Contact> temps = null;

		// 범위를 벗어난 요청을 한다면
		if (startIndex > contacts.size() - 1 || startIndex < 0 || pagesize < 1) {
			// 비어있는 객체를 생성
			temps = new ArrayList<Contact>();
		}
		// 정상적인 범위의 데이터를 요청하면
		else {
			// 마지막 데이터가 주소록 크기를 넘어서면
			if (endIndex > contacts.size()) {
				endIndex = contacts.size();
			}

			temps = contacts.subList(startIndex, endIndex);
		}

		ContactList cList = new ContactList(pageno, pagesize, contacts.size(), temps);
		return cList;
	}

	// 주소록에 주소 추가
	public static void addContact(Contact c) {
		nextNo++;
		c.setNo(nextNo);
		contacts.add(0, c);
	}

	// 주소 삭제
	public static int deleteContact(long no) {
		int count = 0;
		for (int i = 0; i < contacts.size(); i++) {
			Contact c = contacts.get(i);
			if (c.getNo() == no) {
				contacts.remove(i);
				count++;
				break;
			}
		}
		return count++;
	}

	// 주소 수정
	public static int updateContact(Contact c1) {
		long no = c1.getNo();
		int count = 0;
		for (int i = 0; i < contacts.size(); i++) {
			Contact c = contacts.get(i);
			if (c.getNo() == no) {
				contacts.set(i, c1);
				count++;
				break;
			}
		}
		return count++;
	}

	// 여러개의 주소들을 업데이트
	public static int updateBatch(ContactList contactList) {
		int count = 0;
		List<Contact> list = contactList.getContacts();
		if (list.size() > 0) {
			for (Contact c : list) {
				SampleDAO.updateContact(c);
				count++;
			}
		}
		return count;
	}
}
