package com.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dao.MemberDao;
import com.vo.Member;

public class LogInController implements Controller {

	@Override
	public String excute(Map<String, Object> model) throws Exception {

		if (model.get("login") == null) {

			return "/auth/LogInForm.jsp";
		} else {

			MemberDao memberDao = (MemberDao) model.get("memberDao");

			Member login = (Member) model.get("login");

			Member member = memberDao.exist(login.getEmail(), login.getPassword());

			if (member != null) {
				HttpSession session = (HttpSession) model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LogInFail.jsp";

			}

		}

	}

}
