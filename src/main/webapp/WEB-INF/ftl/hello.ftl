<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome!</title>
</head>
<body>
<#--注释部分-->
<#--下面使用插值-->
<h1>Welcome ${name}!</h1>
<p>We have these animals:</p>
<#include 'hello2.ftl'>
<ul>
<#--使用ＦＴＬ指令-->
<#list list as animal>
   <li>${animal.name} --- ${animal.price}</li>
</#list>
</ul>
</body>
</html>