package com.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.annotation.Component;

@Component("/auth/logout.do")
public class LogOutController implements Controller {

	@Override
	public String excute(Map<String, Object> model) throws Exception {
		
		HttpSession session = (HttpSession)model.get("session");
		
		session.invalidate();
		
		return "redirect:login.do";
	}

}
