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
	
	
	// 너 준비해줘야 할 vo객체가 뭐니?
	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class,	// int.class,
			"member", spms.vo.Member.class
		};
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		
		Member member = (Member)model.get("member");

		if (member.getEmail() == null) {
			Integer no = (Integer) model.get("no");
			Member detailInfo = memberDao.selectOne(no);
			model.put("member", detailInfo);
			return "/member/MemberUpdateForm.jsp";

		} else {
			memberDao.update(member);
			return "redirect:list.do";
		}
	}
}
