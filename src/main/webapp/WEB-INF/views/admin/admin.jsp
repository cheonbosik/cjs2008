<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>관리자 메뉴</title>
  <frameset cols="120px,*">
    <frame src="${contextPath}/admin/aLeft" frameborder=0>
    <frame src="${contextPath}/admin/aContent" name="content" frameborder=0>
  </frameset>
</head>
<body>

</body>
</html>