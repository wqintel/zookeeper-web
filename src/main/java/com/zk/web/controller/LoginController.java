package com.zk.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zk.web.util.AuthUtils;

@Controller
@RequestMapping("")
public class LoginController {

	/*
	 * @RequestMapping("/login") public String login() { return "login"; }
	 */

	@RequestMapping("/login")
	public String login(HttpServletRequest request, String username, String password) {
		AuthUtils.login(username, password);
		String referer = request.getHeader("referer");
		String cp = request.getContextPath();
		return "redirect:" + StringUtils.substringAfter(referer, cp);
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		AuthUtils.logout();
		String referer = request.getHeader("referer");
		String cp = request.getContextPath();
		return "redirect:" + StringUtils.substringAfter(referer, cp);
	}

}
