<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
  </head>
  <body>
   用户名<input class="itxt" type="text" onchange="usernameComplete()" placeholder="请输入用户名" autocomplete="off" tabindex="1" name="username" />
   <br />
   <br />
   登录密码<input class="itxt" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password" />
   <br />
   <br />
   确认密码<input class="itxt" type="password" placeholder="确认密码" autocomplete="off" tabindex="1" name="repwd" />
   <br />
   <br />
   邮箱<input class="itxt" type="text" placeholder="请输入邮箱地址" autocomplete="off" tabindex="1" name="email" />
   <br />
   <br />
   验证码<input id="verifyCode" name="verifyCode" class="form-yz login_3" type="text" onchange="checkCode()">
   <img src="${pageContext.request.contextPath}/captcha/getCaptchaImage.htm" id="kaptchaImage"  style="cursor: pointer;width:80px;height:32px;" onclick="changeCode()" title="看不清，点击换一张">
   <br />
   <br />
   <input type="submit" value="注册" id="sub_btn" />
    <script src="/cip_sale/static/js/jquery/jquery-3.0.0.js"></script>
    <script src="/cip_sale/static/js/jquery/jquery-form.min.js"></script>
    <script type="text/javascript">

    function changeCode() {  //刷新
          $('#kaptchaImage').hide().attr('src', '${pageContext.request.contextPath}/captcha/getCaptchaImage.htm?' + Math.floor(Math.random()*100) ).fadeIn();  
           /*  event.cancelBubble=true; */  
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

    function usernameComplete(){
    	var name = $("[name='username']").val();
    	$.ajax({
            type: "post",
            url: "checkName.do",
            data: {"name":name},
            success : function( r ) {
            	if(!r.success){
           		 alert("用户名已存在，请重新注册")
           	 }
            }
        });
    }
	$(function(){
		//给按钮绑定一个单击响应函数
		$("#sub_btn").click(function(){
			//获取用户输入用户名、密码、确认密码、电子邮箱、验证码；
			var name = $("[name='username']").val();
			var password = $("[name='password']").val();
			var repwd = $("[name='repwd']").val();
			var email = $("[name='email']").val();
			//alert(name+"--"+password+"--"+repwd+"--"+email+"--"+code);
			//验证用户名、密码是否符合规则（正则表达式）
			//验证用户名
			var nameReg = /^[a-z0-9_]{6,20}$/;
			if(!nameReg.test(name)){
				//用户名格式不正确
				alert("请输入含有字母、数字的6~20的用户名");
				
				//取消默认行为
				return false;
			}
			//验证密码
			var pewReg = /^[a-z0-9_-]{6,18}$/;
			if(!pewReg.test(password)){
				//密码格式不正确
				alert("请输入含有字母、数字、下划线、-的6~18的密码");
				//取消默认行为
				return false;
			}
			//验证确认密码
			if(repwd!=password){
				//两次密码输入不一致
				alert("两次密码输入不一致,请重新输入");
				$("[name='repwd']").val("");
				//取消默认行为
				return false;
			}
			//验证邮箱
			var emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!emailReg.test(email)){
				//邮箱格式不正确
				alert("邮箱格式不正确");
				//取消默认行为
				return false;
			}
			 $.ajax({
 	            type: "post",
 	            url: "regist.do",
                 data: {"name":name,"password":password,"repwd":repwd,"email":email},
                 success : function( r ) {
                	 if(r.success){
                		 alert("注册成功，请前往注册邮箱完成激活")
                	 }
                 }
 	        });
		});
	});
    </script>
  </body>
</html>
