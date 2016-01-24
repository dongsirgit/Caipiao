<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String basepath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<script type="text/javascript" src="js/jquery-1.6.2.js"></script>
<script type="text/javascript">
$(function(){
	$('#queryCp').click(function(){
		window.location.href="<%=basepath%>/queryCp";
	})
})
</script>
</head>
<body>
<input id="queryCp" type="button" value="查询北京11选5开奖结果">

</body>
</html>