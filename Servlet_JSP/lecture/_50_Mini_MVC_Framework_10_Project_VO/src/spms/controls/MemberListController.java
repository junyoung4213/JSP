package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.dao.MemberDao;

@Component("/member/list.do")
public class MemberListController implements Controller{

	/*
	 * DI로 바꾼 이유는
	 * 1) 클래스간의 의존성을 낮추기 위해서
	 * 2) MemberDao 인터페이스 선언과 상속을 통해
	 * 	  다른 데이터베이스도 DI를 통해 유연한 사용이 가능하게 하려고
	 * 3) 후에 자동화 작업을 하기 위해서
	*/
	
	MemberDao memberDao = null;
	
	public MemberListController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		model.put("members", memberDao.selectList());
		return "/member/MemberList.jsp";
	}
	
	
}
