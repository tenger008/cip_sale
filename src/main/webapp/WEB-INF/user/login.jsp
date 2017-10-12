<%@include file="/WEB-INF/common/css.jsp"%>
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
    <%@include file="/WEB-INF/common/script.jsp"%>
    <script type="text/javascript">
    $("#sub_btn").click(function(){
    	$("#login_login_form").submit();
    })
    </script>
</body>
</html>