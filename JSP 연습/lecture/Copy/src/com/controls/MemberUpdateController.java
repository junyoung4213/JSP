package com.controls;

import java.util.Map;

import com.dao.MemberDao;
import com.vo.Member;

public class MemberUpdateController implements Controller {

	@Override
	public String excute(Map<String, Object> model) throws Exception {

		if (model.get("member") == null) {

			MemberDao memberDao = (MemberDao) model.get("memberDao");
			Member member = memberDao.selectOne((int) model.get("no"));
			model.put("member", member);
			return "/member/MemberUpdateForm.jsp";
		} else {

			MemberDao memberDao = (MemberDao) model.get("memberDao");
			Member member = (Member) model.get("member");
			memberDao.update(member);

			return "redirect:list.do";
		}

	}

}
