<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>mUpdateCheck.jsp</title>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <h2>회원 정보 체크</h2>
  <p>회원 정보를 수정하기 위해서는 비밀번호를 입력해 주세요</p>
  <form method="post" class="was-validated">
    <div class="form-group">
      <label for="pwd">비밀번호 :</label>
      <input type="password" class="form-control" name="pwd" id="pwd" placeholder="비밀번호를 입력하세요" required/>
      <div class="valid-feedback">정확한 비밀번호를 입력하세요.</div>
      <div class="invalid-feedback">비밀번호는 필수 입력사항입니다.</div>
    </div>
    
    <button type="submit" class="btn btn-secondary">인증하기</button>&nbsp;
    <button type="reset" class="btn btn-secondary">취소</button>&nbsp;
    <button type="button" class="btn btn-secondary" onclick="location.href='${contextPath}/';">돌아가기</button>&nbsp;
  </form>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>