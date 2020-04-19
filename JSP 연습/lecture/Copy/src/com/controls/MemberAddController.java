package com.controls;

import java.util.Map;

import com.dao.MemberDao;
import com.vo.Member;

public class MemberAddController implements Controller {

	@Override
	public String excute(Map<String, Object> model) throws Exception {

		if (model.get("member") == null) {
			return "/member/MemberForm.jsp";

		} else {
			MemberDao memberDao = (MemberDao) model.get("memberDao");
			Member member = (Member) model.get("member");
			memberDao.insert(member);

			return "redirect:list.do";
		}

	}

}
