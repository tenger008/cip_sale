package com.cip.sale.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cip.sale.bean.User;
import com.google.gson.Gson;

@Controller
public class IndexController {
   
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpSession session){
		//获取浏览器中的cookie
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			String sale_cookie_user = "";
			for (int i = 0; i < cookies.length; i++) {
				//验证cookies中是否包含着cip用户
				if ((cookies[i].getName()).equals("sale_cookie_user")) {
					sale_cookie_user = cookies[i].getValue();
				}
			}

			try {
				sale_cookie_user = URLDecoder.decode(sale_cookie_user, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //如果cip用户存在并不为空，就将cookie中的用户放到session中
			if (sale_cookie_user != null && !sale_cookie_user.equals("")) {
				Gson gson = new Gson();
				User fromJson = gson.fromJson(sale_cookie_user, User.class);
				session.setAttribute("user", fromJson);
			}
		}

		return "sale_index";
	}
	
}
