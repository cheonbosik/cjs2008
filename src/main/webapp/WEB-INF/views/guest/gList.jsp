<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>gList.jsp</title>
    <style>
    	h2 {font-family: "배달의민족 주아"}
		#content{
			width: 700px;
			margin: 0 auto;
			/* border: 5px solid rgba(163, 204, 163); */
			border: 5px solid #bbb;
			border-radius: 1em;
			padding-left: 30px;
			padding-right: 30px;
		}
    </style>
	
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<div class="container">
	<div style="text-align:center;">
		<p><br/></p>
		<h2>방 명 록</h2>
		<p><br/></p>
		<button type="button" class="btn btn-outline-secondary" onclick="location.href='${contextPath}/h'">홈으로 돌아가기</button>
		<button type="button" class="btn btn-secondary" onclick="location.href='${contextPath}/guest/gInput'">방명록 작성하기</button>
	</div>
	<br/>
	<c:forEach var="vo" items="${vos}">
		<div id ="content">
			<p><br/></p>
			<div style="text-align:left;color:gray;">
				글번호 : ${curScrNo}
			</div>
			<div>
				<b>${vo.name}</b> | ${fn:substring(vo.vdate,0,16)} | ${vo.email} | ${vo.homepage} 
			</div>
			<hr/>
			<div style="text-align:right;color:gray;">
				${vo.hostip}
			</div>
			<div>${fn:replace(vo.content,newLine,'<br/>')}</div>
			<p><br/></p>
			<hr/>
			<div><a href="${contextPath}/guest/gUpdate?idx=${vo.idx}">수정</a>/<a href="${contextPath}/guest/gDelete?idx=${vo.idx}">삭제</a></div>
			<p><br/></p>
		</div>
		<p><br/></p>
		<c:set var="curScrNo" value="${curScrNo-1}"/>
	</c:forEach>
	<!-- 블록 페이징처리 시작 -->
	<div class="container" style="text-align:center">
	  <ul class="pagination justify-content-center">
		  <c:set var="startPageNum" value="${pag - (pag-1)%blockSize}"/>
		  <c:if test="${pag != 1}">
		    <li class="page-item"><a href="${contextPath}/guest/gList?pag=1" class="page-link" style="color:#666">◁◁</a></li>
		    <li class="page-item"><a href="${contextPath}/guest/gList?pag=${pag-1}" class="page-link" style="color:#666">◀</a></li>
		  </c:if>
		  <c:forEach var="i" begin="0" end="2">
		    <c:if test="${(startPageNum+i) <= totPage}">
		      <c:if test="${pag == (startPageNum+i)}">
		        <li class="page-item active"><a href="${contextPath}/guest/gList?pag=${startPageNum+i}" class="page-link btn btn-secondary active" style="color:#666"><font color="#fff"><b>${startPageNum+i}</b></font></a></li>
		      </c:if>
		      <c:if test="${pag != (startPageNum+i)}">
		        <li class="page-item"><a href="${contextPath}/guest/gList?pag=${startPageNum+i}" class="page-link" style="color:#666">${startPageNum+i}</a></li>
		      </c:if>
		    </c:if>
		  </c:forEach>
		  <c:if test="${pag != totPage}">
		    <li class="page-item"><a href="${contextPath}/guest/gList?pag=${pag+1}" class="page-link" style="color:#666">▶</a></li>
		    <li class="page-item"><a href="${contextPath}/guest/gList?pag=${totPage}" class="page-link" style="color:#666">▷▷</a></li>
		  </c:if>
		</ul>
	</div>
	<!-- 블록 페이징처리 끝 -->
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>