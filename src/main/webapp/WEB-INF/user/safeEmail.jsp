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
注册邮箱<input class="itxt" type="text"  placeholder="请输入注册邮箱" autocomplete="off" tabindex="1" name="email" />
   <div class="box_01_both_2">
						<button id="next">发送重置链接</button>
						</div>
						 <script src="/cip_sale/static/js/jquery/jquery-3.0.0.js"></script>
    <script src="/cip_sale/static/js/jquery/jquery-form.min.js"></script>
    <script type="text/javascript">
						$("#next").click(function(){
    	var email = $("[email='email']").val();
    	$.ajax({
	            type: "post",
	            url: "forgetPassword2.do",
             data: {"email":email}
	        });
    	
    })
    </script>
</body>
</html>