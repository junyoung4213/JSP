package com.controls;

import java.util.Map;

import com.dao.MemberDao;

public class MemberDeleteController implements Controller {

	MemberDao memberDao;

	public MemberDeleteController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String excute(Map<String, Object> model) throws Exception {

		memberDao.delete((int) model.get("no"));

		return "redirect:list.do";
	}

}
