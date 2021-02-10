<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>woo.jsp</title>
  <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${contextPath}/resources/js/woo.js"></script>
  <script>
    function wooCheck() {
    	var sample4_postcode = document.getElementById("sample4_postcode").value;
    	var sample4_roadAddress = document.getElementById("sample4_roadAddress").value;
    	var sample4_extraAddress = document.getElementById("sample4_extraAddress").value;
    	var sample4_detailAddress = document.getElementById("sample4_detailAddress").value;
    	
    	if(sample4_postcode=="") {
    		alert("우편번호를 등록하세요!");
    	}
    	else {
	    	var address = "("+sample4_postcode+") "+sample4_roadAddress+" "+sample4_extraAddress+" "+sample4_detailAddress;
	    	alert("주소 : \n"+address);
    	}
    }
  </script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <h2>다음 우편변호 API</h2>
  <hr/>
  <form name="name" method="post">
    <input type="text" id="sample4_postcode" placeholder="우편번호">
		<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br>
		<input type="text" id="sample4_roadAddress" placeholder="도로명주소">
		<!-- <input type="text" id="sample4_jibunAddress" placeholder="지번주소"> -->
		<span id="guide" style="color:#999;display:none"></span>
		<input type="text" id="sample4_extraAddress" placeholder="참고항목"><br/>
		<input type="text" id="sample4_detailAddress" placeholder="상세주소" size="44">
		<p>
		  <input type="button" value="등록" onclick="wooCheck()"/>
		  <input type="reset" value="다시입력"/>
		</p>
  </form>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>