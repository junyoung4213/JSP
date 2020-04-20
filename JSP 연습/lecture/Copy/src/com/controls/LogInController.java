package com.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.bind.DataBinding;
import com.dao.MySqlMemberDao;
import com.vo.Member;

public class LogInController implements Controller, DataBinding {

	MySqlMemberDao memberDao;

	public LogInController setMemberDao(MySqlMemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] { "login", com.vo.Member.class };
	}

	@Override
	public String excute(Map<String, Object> model) throws Exception {
		Member login = (Member) model.get("login");
		if (login.getEmail() == null) {

			return "/auth/LogInForm.jsp";
		} else {

			Member member = memberDao.exist(login.getEmail(), login.getPassword());

			if (member != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				model.put("session", session);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LogInFail.jsp";

			}

		}

	}

}
