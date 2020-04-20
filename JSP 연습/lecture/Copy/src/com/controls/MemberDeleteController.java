package com.controls;

import java.util.Map;

import com.dao.MySqlMemberDao;

public class MemberDeleteController implements Controller {

	MySqlMemberDao memberDao;

	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String excute(Map<String, Object> model) throws Exception {

		memberDao.delete((int) model.get("no"));

		return "redirect:list.do";
	}

}
