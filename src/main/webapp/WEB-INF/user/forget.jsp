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
 用户名<input class="itxt" type="text"  placeholder="请输入用户名" autocomplete="off" tabindex="1" name="name" />
   验证码<input id="verifyCode" name="verifyCode" class="form-yz login_3" type="text" onchange="checkCode()">
   <img src="${pageContext.request.contextPath}/captcha/getCaptchaImage.htm" id="kaptchaImage"  style="cursor: pointer;width:80px;height:32px;" onclick="changeCode()" title="看不清，点击换一张">
   <div class="box_01_both_2">
						<button id="next">下一步</button>
						</div>
   <script src="/cip_sale/static/js/jquery/jquery-3.0.0.js"></script>
    <script src="/cip_sale/static/js/jquery/jquery-form.min.js"></script>
    <script type="text/javascript">
    function changeCode() {  //刷新
        $('#kaptchaImage').hide().attr('src', '${pageContext.request.contextPath}/captcha/getCaptchaImage.htm?' + Math.floor(Math.random()*100) ).fadeIn();  
  }  
    function checkCode() {  //校验验证码
    	var verifyCode = $("#verifyCode").val();
    $.ajax({
    	type: "post",
        url: "${pageContext.request.contextPath}/captcha/checkCode.htm",
        data: {"verifyCode":verifyCode},
        success : function( r ) {
        	if(!r.success){
       		 alert("验证码错误，请重新输入")
       	 }
        }
    })
    } 
    $("#next").click(function(){
    	var name = $("[name='name']").val();
    	$.ajax({
	            type: "post",
	            url: "findPassword1.do",
             data: {"name":name},
             success:function(r){
                 if (r.success) {//根据返回值进行跳转
                     window.location.href = 'safeEmail.do';
                 }
                 else{
                	 alert("用户名不存在");
                 }
             }
	        });
    	
    })
    
    </script>
</body>
</html>