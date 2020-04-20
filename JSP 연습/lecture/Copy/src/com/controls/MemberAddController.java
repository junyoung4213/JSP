package com.controls;

import java.util.Map;

import com.annotation.Component;
import com.bind.DataBinding;
import com.dao.MySqlMemberDao;
import com.vo.Member;

@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding {

	MySqlMemberDao memberDao;

	public MemberAddController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] { "member", com.vo.Member.class };
	}

	@Override
	public String excute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");
		if (member.getEmail() == null) {
			return "/member/MemberForm.jsp";

		} else {
			memberDao.insert(member);

			return "redirect:list.do";
		}

	}

}
