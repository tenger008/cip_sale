<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="login.do" id="login_login_form" method="post">
用户名<input class="itxt" type="text"  placeholder="请输入用户名" autocomplete="off" tabindex="1" name="name" />
   <br />
   <br />
   登录密码<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password" />
   <br />
   <br />
   <input type="checkbox" class="box_01_box" name="auto_login" value="auto_login"/>
   <div class="box_01_both">
						<div class="box_01_both_1">自动登陆</div>
						<div class="box_01_both_2">
						<a href="forgetPassword.do">忘记密码？</a>
						</div>
	</div>
    <input type="submit" value="登录" id="sub_btn" />
    </form>
     <script src="/cip_sale/static/js/jquery/jquery-3.0.0.js"></script>
    <script src="/cip_sale/static/js/jquery/jquery-form.min.js"></script>
    <script type="text/javascript">
    $("#sub_btn").click(function(){
    	$("#login_login_form").submit();
    })
    </script>
</body>
</html>