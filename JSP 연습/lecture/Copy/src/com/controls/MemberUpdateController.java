package com.controls;

import java.util.Map;

import com.dao.MemberDao;
import com.vo.Member;

public class MemberUpdateController implements Controller {

	MemberDao memberDao;

	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String excute(Map<String, Object> model) throws Exception {

		if (model.get("member") == null) {

			Member member = memberDao.selectOne((int) model.get("no"));
			model.put("member", member);
			return "/member/MemberUpdateForm.jsp";
		} else {

			Member member = (Member) model.get("member");
			memberDao.update(member);

			return "redirect:list.do";
		}

	}

}
