package com.cip.sale.controller;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cip.sale.bean.User;
import com.cip.sale.util.EmailUtil;
import com.cip.sale.util.MD5Util;
import com.cip.sale.util.StringUtil;
import com.cip.user.service.LoginServiceInf;
import com.cip.user.service.RegistServiceInf;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.gson.Gson;

@Controller
public class UserController {
	@Autowired
   LoginServiceInf loginService;
	@Autowired
	RegistServiceInf registService;
	@Autowired  
	private Producer captchaProducer = null;  

@RequestMapping("goto_regist")
public String goto_regist() {
	return "user/regist";
}

@RequestMapping("goto_login")
public String goto_login() {
	return "user/login";
}

@RequestMapping("/safeEmail")
public String safeEmail() {
	return "user/safeEmail";
}

@RequestMapping("/forgetPassword")
public String forgetpassword() {
	return "user/forget";
}
@ResponseBody
@RequestMapping("/findPassword1")
public Object findPassword1(String name) {
	Map<String, Object> resultMap = new HashMap<String, Object>();
	String checkName = registService.checkName(name);
	if(checkName!=null){
		resultMap.put("success",true);
		return resultMap;
	}
	else {
		resultMap.put("err", "用户名或密码错误");
		return resultMap;
	}
}

@RequestMapping("/forgetPassword2")
public String findPassword2(String email,String name,ModelMap map) {
	String user_id = registService.checkUser(email,name);
	if(user_id!=null){
		return "user/changePassword";
	}
	else {
		map.put("err", "邮箱错误");
		return "user/safeEmail";
	}
}

@RequestMapping("/login")
public String login( HttpServletResponse response, String auto_login, String name,String password,
		HttpSession session,
		ModelMap map) {
	User user_login = null;
	User user = new User();
	user.setName(name);
	user.setPassword(MD5Util.digest(password));
	user_login = loginService.login(user);
	if (user_login != null) {
		if (auto_login != null) {
			auto_login(user_login, response);
		}
		session.setAttribute("user", user_login);

		// 同步cookie和db中的用户数据
		//cookie_car_to_db_session(user_login, cookie_list_car, response, session);

		return "redirect:/index.do";
	} else {
		map.put("err", "用户名或密码错误");
		return "user/login";
	}

} 

public void auto_login(User user_login, HttpServletResponse response) {
	Gson gson = new Gson();
	String json = gson.toJson(user_login);

	try {
		json = URLEncoder.encode(json, "utf-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	Cookie cookie = new Cookie("sale_cookie_user", json);
	cookie.setMaxAge(60 * 60 * 24 * 1);
	response.addCookie(cookie);
}

@ResponseBody
@RequestMapping("/regist")
public Object regist(String name,String password,String repwd,String email){
	Map<String, Object> resultMap = new HashMap<String, Object>();
	  try {
		  User user = new User();
		  user.setName(name);
		  user.setPassword(MD5Util.digest(password));
		  user.setEmail(email);
		  String creatUUID = StringUtil.creatUUID();
		  user.setUuid(creatUUID);
		 String regist = registService.regist(user);
		 if(regist.equals("success")){
			 String url="http://localhost:38080/cip_sale/checkActivation/"+creatUUID;
			  EmailUtil.createMimeMessage("admin@kewei.com", "ds", "ds",url, email);
			  resultMap.put("success", true);
		 }
	} catch (Exception e) {
		e.printStackTrace();
		resultMap.put("success", false);
	}
	  return resultMap;
}

//用户名注册时重名检查
@ResponseBody
@RequestMapping("/checkName")
public Object checkName(String name){
	Map<String, Object> resultMap = new HashMap<String, Object>();
	try {
	String checkName = registService.checkName(name);
		resultMap.put("success", checkName==null);
	} catch (Exception e) {
		e.printStackTrace();
		resultMap.put("success", false);
	}
	return resultMap;
}

//用户名注册时邮箱检查
@ResponseBody
@RequestMapping("/checkEmail")
public Object checkEmail(String email){
	Map<String, Object> resultMap = new HashMap<String, Object>();
	try {
		String checkEmail = registService.checkEmail(email);
		resultMap.put("success", checkEmail==null);
	} catch (Exception e) {
		e.printStackTrace();
		resultMap.put("success", false);
	}
	return resultMap;
}

//邮件激活（限制24小时之内激活；激活过的不能再次激活）
@ResponseBody
@RequestMapping("/checkActivation/{uuid}")
public String checkActivation(@PathVariable("uuid")String uuid,ModelMap map) throws ParseException {
	User user = registService.queryUserStatus(uuid);
	if(user.getStatus()==0){
	Date now = new Date();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String formatNow = format.format(now);
	registService.activation(uuid,formatNow);
	Calendar c = Calendar.getInstance();
	c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatNow));
	long activationInMillis = c.getTimeInMillis();
	c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.getRegist_time()));
	long registInMillis = c.getTimeInMillis();
	if(activationInMillis-registInMillis<=86400000){
		registService.setActivationStatus(uuid);
		map.put("status", 1);
	}
	if(activationInMillis-registInMillis>86400000){
		map.put("status", 2);//过期不能激活
	}
	}
	else{
		 map.put("status", 0);
	}
	return "success";
}

//加载谷歌插件验证码
@RequestMapping(value="/captcha/getCaptchaImage.htm")  
public ModelAndView getCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {  
    HttpSession session = request.getSession();  
    String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);  
    System.out.println("******************验证码是: " + code + "******************");  
      
    response.setDateHeader("Expires", 0);  
      
    // Set standard HTTP/1.1 no-cache headers.  
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
      
    // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
      
    // Set standard HTTP/1.0 no-cache header.  
    response.setHeader("Pragma", "no-cache");  
      
    // return a jpeg  
    response.setContentType("image/jpeg");  
      
    // create the text for the image  
    String capText = captchaProducer.createText();  
      
    // store the text in the session  
    session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
      
    // create the image with the text  
    BufferedImage bi = captchaProducer.createImage(capText);  
    ServletOutputStream out = response.getOutputStream();  
      
    // write the data out  
    ImageIO.write(bi, "jpg", out);  
    try {  
        out.flush();  
    } finally {  
        out.close();  
    }  
    return null;  
}  

//检查验证码是否正确
@ResponseBody
@RequestMapping("/captcha/checkCode.htm")
public Object checkCode(String verifyCode,HttpServletRequest request){
	Map<String, Object> resultMap = new HashMap<String, Object>();
	try {
		String kaptchaExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY); 		
		
		resultMap.put("success", kaptchaExpected.equals(verifyCode));
	} catch (Exception e) {
		e.printStackTrace();
		resultMap.put("success", false);
	}
	return resultMap;
}

}

