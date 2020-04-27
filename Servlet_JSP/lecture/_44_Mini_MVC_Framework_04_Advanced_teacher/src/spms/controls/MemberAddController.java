package spms.controls;

import java.util.Map;

import spms.dao.MemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller{

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		// get
		if(model.get("member") == null) {
			return "/member/MemberForm.jsp";
		}
		// post
		else {
			MemberDao memberDao = 
					(MemberDao)model.get("memberDao");
			Member member = (Member)model.get("member");
			memberDao.insert(member);
			
			return "redirect:list.do";
		}
	}
}





