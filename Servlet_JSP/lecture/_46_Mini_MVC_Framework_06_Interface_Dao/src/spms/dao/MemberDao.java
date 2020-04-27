package spms.dao;

import java.util.List;

import spms.vo.Member;

// 다른 DBMS와의 연결을 유연하게 해주기 위해서 인터페이스 생성
public interface MemberDao {

	public List<Member> selectList() throws Exception;

	public int insert(Member member) throws Exception;

	public int delete(int no) throws Exception;

	public Member selectOne(int no) throws Exception;

	public int update(Member member) throws Exception;

	public Member exist(String email, String password) throws Exception;
}
