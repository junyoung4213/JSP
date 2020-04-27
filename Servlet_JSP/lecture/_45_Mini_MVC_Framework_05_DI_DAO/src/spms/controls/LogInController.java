package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MemberDao;
import spms.vo.Member;

public class LogInController implements Controller {

	// DI_DAO 주입
	MemberDao memberDao = null;

	public LogInController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("loginInfo") == null) {
			return "/auth/LogInForm.jsp";
		} else {
			Member loginInfo = (Member) model.get("loginInfo");
			Member member;
			HttpSession session = (HttpSession) model.get("session");
			member = memberDao.exist(loginInfo.getEmail(), loginInfo.getPassword());
			if (member != null) {
				session.setAttribute("member", member);
				model.put("session", session);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LogInFail.jsp";
			}

		}

	}
}
