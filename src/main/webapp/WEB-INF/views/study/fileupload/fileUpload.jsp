<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>fileUpload.jsp</title>
  <script>
    function imgCheck() {
    	// 자바스크립트로 파일 공백, 크기와, 확장자명을 체크한다. 
    	myform.submit();
    }
  </script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<div class="container">
  <h2>파일 업로드 연습</h2>
  <form name="myform" method="post" enctype="multipart/form-data">
    <p>파일명 :
      <input type="file" name="mFile" id="mFile" class="form-controll-flie border" accept=".gif,.jpg,.png"/>
    </p>
    <p>
      <input type="button" value="등록" onclick="imgCheck()" class="btn btn-secondary"/>
      <input type="reset" value="다시선택" class="btn btn-secondary"/>
    </p>
  </form>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>