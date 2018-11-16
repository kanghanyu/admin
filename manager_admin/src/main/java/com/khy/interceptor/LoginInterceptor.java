package com.khy.interceptor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.JSONObject;
import com.khy.entity.User;
import com.khy.utils.SessionHolder;
@Configuration
public class LoginInterceptor extends WebMvcConfigurerAdapter{

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static List<String> list = new ArrayList<String>(){{
		add("/");
		add("/error");
		add("/manager");
		add("/login");
		add("/register");
		add("/resetPassword");
		add("/user/login");
		add("/user/getMessageCode");
		add("/user/validate/img");
		add("/user/register");
		add("/user/resetPassword");
	}};
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptor() {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				HttpSession session = request.getSession();
				User user = (User) session.getAttribute("loginUser");
				if(null == user){
					String requestURI = request.getRequestURI();
					for (String url : list) {
						if(requestURI.equals(url)){
							return true;
						}
					}
					String scheme = request.getScheme();
					int port = request.getLocalPort();
					response.sendRedirect(scheme + "://" + request.getServerName() + ":" + port + "/login");
				}else{
					SessionHolder.setUser(user);
				}
				return true;
			}
			
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
				
			}
			
			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
					throws Exception {
				if(ex != null){
					JSONObject json = new JSONObject();
					json.put("url", request.getRequestURI());
					json.put("msg",ex.toString());
					json.put("time", sdf.format(new Date()));
					json.put("code", 2000);
					LOGGER.error(json.toString());
				}
			}
		}).addPathPatterns("/**");
	}

	
}
