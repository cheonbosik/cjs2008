<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>mMidSearch.jsp</title>
  <script>
		function loginCheck(){
			if(loginForm.name.value==""){
				alert('성명을 입력해주세요');
				loginForm.name.focus();
			}
			else if(loginForm.email.value==""){
				alert('이메일을 입력해주세요');
				loginForm.email.focus();
			}
			else{
				loginForm.submit();
			}
		}
	</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<div><br/></div>
<div class="container">
	<form name="loginForm" method="post">
		<h1 class="text-center">아 이 디 찾 기</h1>
		<br/>
		<div class="row">
			<div class="col-3">
			</div>
			<div class="col-2 text-center">
				성명
			</div>
			<div class="col-3">
				<input type="text" class="form-control" name="name" id="name" placeholder="성명을 입력하세요" autofocus>
			</div>
			<div class="col-4">
			</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-3">
			</div>
			<div class="col-2 text-center">
				이메일
			</div>
			<div class="col-4">
				<input type="text" class="form-control" name="email" id="email" placeholder="메일주소를 입력하세요">
			</div>
			<div class="col-3">
			</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-12 text-center">
				<input type="button" class="btn btn-outline-secondary" onclick="loginCheck()" value="인증">
				<input type="button" class="btn btn-secondary" value="돌아가기" onclick="location.href='${contextPath}/member/mLogin'">
			</div>
		</div>
	</form>
</div>
<div><br/><br/></div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>