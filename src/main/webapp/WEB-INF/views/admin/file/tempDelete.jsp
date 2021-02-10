<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>tempDelete.jsp</title>
</head>
<body>
<p><br/></p>
<div class="container">
  <h2>임시파일 삭제 처리</h2>
  <hr/>
  <p><a href="${contextPath}/admin/file/boardTempDelete">게시판 임시파일 삭제</a></p>
  <hr/>
</div>
</body>
</html>