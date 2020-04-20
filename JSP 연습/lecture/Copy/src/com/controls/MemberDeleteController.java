package com.controls;

import java.util.Map;

import com.annotation.Component;
import com.bind.DataBinding;
import com.dao.MySqlMemberDao;

@Component("/member/delete.do")
public class MemberDeleteController implements Controller, DataBinding{

	MySqlMemberDao memberDao;

	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class
		};
	}



	@Override
	public String excute(Map<String, Object> model) throws Exception {

		memberDao.delete((int) model.get("no"));

		return "redirect:list.do";
	}

}
