<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>bList.jsp</title>
  <script>
    function pageSizeCheck() {
    	var pageSize = pageForm.pageSize.value;
    	location.href="${contextPath}/board/bList?pag=${pageVo.pag}&pageSize="+pageSize;
    }
  </script>
  <style>
    .tblBody {
      text-align:center;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <table class="table table-borderless">
    <tr>
      <td colspan="2" style="text-align:center;"><h2>게 시 판 리 스 트</h2></td>
    </tr>
    <tr>
      <td>
        <button type="button" onclick="location.href='${contextPath}/board/bInput';" class="btn btn-secondary">글올리기</button>
      </td>
      <td style="text-align:right;">
        <form name="pageForm">
          <select name="pageSize" onchange="pageSizeCheck()">
            <option value="5" <c:if test="${pageVo.pageSize == 5}"> selected </c:if>>5건</option>
            <option value="10" <c:if test="${pageVo.pageSize == 10}"> selected </c:if>>10건</option>
            <option value="15" <c:if test="${pageVo.pageSize == 15}"> selected </c:if>>15건</option>
            <option value="20" <c:if test="${pageVo.pageSize == 20}"> selected </c:if>>20건</option>
          </select>
        </form>
      </td>
    </tr>
  </table>
  <table class="table table-hover">
    <thead class="thead-light">
    <tr style="text-align:center;">
      <th>글번호</th>
      <th>글제목</th>
      <th>글쓴이</th>
      <th>글쓴날자</th>
      <th>조회수</th>
      <th>댓글수</th>
    </tr>
    </thead>
    <c:forEach var="vo" items="${vos}">
	    <tr>
	      <td class="tblBody">${vo.idx}</td>
	      <td>
	        <a href="${contextPath}/board/bContent?idx=${vo.idx}&pag=${pageVo.pag}&pageSize=${pageVo.pageSize}">${vo.title}</a>
	        <c:if test="${vo.diffTime <= 24}"><img src="${contextPath}/resources/images/new.gif"/></c:if>
	      </td>
	      <td class="tblBody">${vo.name}</td>
	      <td class="tblBody">
	        <c:if test="${vo.diffTime <= 24}">${fn:substring(vo.wdate,11,19)}</c:if>
	        <c:if test="${vo.diffTime > 24}">${fn:substring(vo.wdate,0,10)}</c:if>
	      </td>
	      <td class="tblBody">${vo.readnum}</td>
	      <td class="tblBody">${vo.replyNum}</td>
	    </tr>
    </c:forEach>
  </table>
  <!-- 블록 페이징처리 시작 -->
	<div class="container" style="text-align:center">
	  <ul class="pagination justify-content-center">
		  <c:set var="startPageNum" value="${pageVo.pag - (pageVo.pag-1)%pageVo.blockSize}"/>
		  <c:if test="${pageVo.pag != 1}">
		    <li class="page-item"><a href="${contextPath}/board/bList?pag=1&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">◁◁</a></li>
		    <li class="page-item"><a href="${contextPath}/board/bList?pag=${pageVo.pag-1}&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">◀</a></li>
		  </c:if>
		  <c:forEach var="i" begin="0" end="2">
		    <c:if test="${(startPageNum+i) <= pageVo.totPage}">
		      <c:if test="${pageVo.pag == (startPageNum+i)}">
		        <li class="page-item active"><a href="${contextPath}/board/bList?pag=${startPageNum+i}&pageSize=${pageVo.pageSize}" class="page-link btn btn-secondary active" style="color:#666"><font color="#fff"><b>${startPageNum+i}</b></font></a></li>
		      </c:if>
		      <c:if test="${pageVo.pag != (startPageNum+i)}">
		        <li class="page-item"><a href="${contextPath}/board/bList?pag=${startPageNum+i}&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">${startPageNum+i}</a></li>
		      </c:if>
		    </c:if>
		  </c:forEach>
		  <c:if test="${pageVo.pag != pageVo.totPage}">
		    <li class="page-item"><a href="${contextPath}/board/bList?pag=${pageVo.pag+1}&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">▶</a></li>
		    <li class="page-item"><a href="${contextPath}/board/bList?pag=${pageVo.totPage}&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">▷▷</a></li>
		  </c:if>
		</ul>
	</div>
	<!-- 블록 페이징처리 끝 -->
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>