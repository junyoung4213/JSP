package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.dao.MemberDao;

@Component("/member/list.do")
public class MemberListController implements Controller{
	
	/* DI로 변경한 이유
	 * 1) 클래스간의 의존성을 낮추기 위해
	 * 2) MemberDao 인터페이스를 선언하고 상속구현함으로써
	 *   다른 DBMS사용을 유연하게 하려고
	 * 3) 나중에 변경할 자동화 작업
	 * */
	
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








