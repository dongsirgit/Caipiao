<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String basepath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basepath%>/js/jquery-1.6.2.js"></script>
<title>结果</title>
</head>
<body>
<div style="width: 500px">
	<h3 style="color: red;">北京11选5   开奖结果：</h3>
	<table border="1" cellspacing="0" width="100%">
		<thead>
			<tr>
				<td align="center">期次</td>
				<td align="center">开奖时间</td>
				<td align="center">开奖号码</td>
			</tr>
		</thead>
		<c:forEach items="${list}" var="cp">
			<tr >
				<td align="center"><c:out value="${cp.expect}"/></td>
				<td align="center"><c:out value="${cp.openTime}"/></td>
				<td align="center"><c:out value="${cp.openCode}"/></td>
			</tr>
		</c:forEach>
		<tr>
			<td align="center"><span style="color: red">本期首号码推荐：</span></td>
			<td colspan="2" align="center"><c:out value="${numONE}"/></td>
		</tr>
	</table>
</div>
</body>
</html>