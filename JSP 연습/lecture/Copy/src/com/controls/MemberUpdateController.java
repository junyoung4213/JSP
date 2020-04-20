package com.controls;

import java.util.Map;

import com.bind.DataBinding;
import com.dao.MySqlMemberDao;
import com.vo.Member;

public class MemberUpdateController implements Controller, DataBinding {

	MySqlMemberDao memberDao;

	public MemberUpdateController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] { "no", Integer.class, "member", com.vo.Member.class, };
	}

	@Override
	public String excute(Map<String, Object> model) throws Exception {

		Member member = (Member) model.get("member");

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
