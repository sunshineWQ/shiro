<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link href="lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
<link href="h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="h-ui.admin/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>用户信息</title>
</head>
<body>
<div style="width: 50%;margin: 0 auto">
	<br><br>
	<p align="right"><button class="btn"><a href="/tologout">退出登录</a></button></p>
</div>
<h3 align="center">用户信息</h3>
<section>
	<table class="table" style="width: 20%;margin: 0 auto;">
		<tr>
			<td>用户编号：</td>
			<td>${Session["user"].userid!}</td>
		</tr>
		<tr>
			<td>用户名：</td>
			<td>${Session["user"].username!}</td>
		</tr><tr>
			<td>用户手机号：</td>
			<td>${Session["user"].userphone!}</td>
		</tr><tr>
			<td>秘文：</td>
			<td>${Session["user"].password!}</td>
		</tr><tr>
			<td>用户类别：</td>
			<#if Session["user"].usertype=="admin">
				<td>管理员</td>
			<#else>
				<td>普通用户</td>
			</#if>
		</tr>
	</table>

</section>
</body>
</html>