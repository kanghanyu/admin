package com.khy.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khy.entity.User;
import com.khy.utils.SessionHolder;

@Controller
public class IndexController {

	
	@RequestMapping(path = { "/", "/manager" })
	public ModelAndView manager() {
		ModelAndView model = new ModelAndView("admin/manager");
		return model;
	}
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("admin/index");
		User user = SessionHolder.currentUser();
		if(user!=null){
			model.addObject("loginUser", user);
		}
		return model;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) throws UnsupportedEncodingException {
		String msg = request.getParameter("msg");
		ModelAndView model = new ModelAndView("admin/login");
		if(StringUtils.isNoneBlank(msg)){
			msg = URLDecoder.decode(msg, "UTF-8");
			model.addObject("msg",msg);
		}
		return model;
	}
	
	@RequestMapping("/register")
	public ModelAndView register(String msg) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("admin/register");
		if(StringUtils.isNoneBlank(msg)){
			msg = URLDecoder.decode(msg, "UTF-8");
			model.addObject("msg",msg);
		}
		model.addObject("msg",msg);
		return model;
	}
	
	@RequestMapping("/resetPassword")
	public ModelAndView resetPassword(String msg) throws UnsupportedEncodingException {
		ModelAndView model = new ModelAndView("admin/resetPassword");
		if(StringUtils.isNoneBlank(msg)){
			msg = URLDecoder.decode(msg, "UTF-8");
			model.addObject("msg",msg);
		}
		model.addObject("msg",msg);
		return model;
	}
}
