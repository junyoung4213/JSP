package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

/* DataBinding 인터페이스를 pageController 클래스에 
 * 상속시키는 이유는 pageController 클래스내에서
 * 스스로 사용할 vo객체에 대한 정보를 가지고 있게 하기
 * 위함이다 
 * */

@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding{
	
	MemberDao memberDao = null;
	
	public MemberAddController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	

	@Override
	public Object[] getDataBinders() {
		// key값 이름, 자동으로 생성해야 할 클래스 타입
		return new Object[] {
			"member", spms.vo.Member.class
		};
	}


	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member)model.get("member");
		// get
		if(member.getEmail() == null) {
			return "/member/MemberForm.jsp";
		}
		// post
		else {
			memberDao.insert(member);			
			return "redirect:list.do";
		}
	}
}





