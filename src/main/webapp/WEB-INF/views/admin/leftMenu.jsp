<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>leftMenu.jsp</title>
  <script>
    function logoutCheck() {
    	parent.location.href = "${contextPath}/admin/adminOut";
    }
  </script>
</head>
<body bgcolor="#ddd">
<p><br/></p>
<div style="text-align:center;">
  <h3><a href="${contextPath}/admin/aContent" target="content">관리자메뉴</a></h3>
  <hr/>
  <p><a href="${contextPath}/admin/guest/aGList" target="content">방명록관리</a></p>
  <hr/>
  <p><a href="${contextPath}/admin/member/aMList" target="content">회원관리</a></p>
  <hr/>
  <p><a href="${contextPath}/notify/nList" target="content">공지사항</a></p>
  <hr/>
  <p><a href="${contextPath}/admin/file/tempDelete" target="content">임시파일삭제</a></p>
  <hr/>
  <p><a href="javascript:logoutCheck()">홈으로</a></p>
</div>
</body>
</html>