package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

@Component("/member/update.do")
public class MemberUpdateController implements Controller, DataBinding {

	MemberDao memberDao = null;

	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	// vo 객체를 준비해준다.
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
				"no",Integer.class,
				"member",spms.vo.Member.class
		};
	}



	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
		Member member = (Member)model.get("member");
		
		
		// get
		if (member.getEmail() == null) {
			Integer no = (Integer) model.get("no");
			Member detailInfo = memberDao.selectOne(no);
			model.put("member", detailInfo);
			return "/member/MemberUpdateForm.jsp";
		}
		// post
		else {
			memberDao.update(member);
			return "redirect:list.do";
		}
	}

}
