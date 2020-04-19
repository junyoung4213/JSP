package com.controls;

import java.util.Map;

import com.dao.MemberDao;

public class MemberListController implements Controller {

	@Override
	public String excute(Map<String, Object> model) throws Exception {
		
		MemberDao memberDao = (MemberDao) model.get("memberDao");
		model.put("members", memberDao.selectList());
		
		return "/member/MemberList.jsp";
	}

}
