package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import spms.dao.MemberDao;

public class MemberDeleteController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {

		MemberDao memberDao = (MemberDao) model.get("memberDao");

		memberDao.delete((int)model.get("no"));

		return "redirect:list.do";
	}

}
