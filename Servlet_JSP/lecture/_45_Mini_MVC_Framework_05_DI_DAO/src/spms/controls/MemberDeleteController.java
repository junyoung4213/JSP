package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import spms.dao.MemberDao;

public class MemberDeleteController implements Controller {

	// DI_DAO 주입
	MemberDao memberDao = null;

	public MemberDeleteController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		memberDao.delete((int)model.get("no"));
		return "redirect:list.do";
	}

}
