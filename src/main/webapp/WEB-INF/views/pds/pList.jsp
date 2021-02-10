<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>pList.jsp</title>
  <script>
    function partCheck() {
    	var part = partForm.part.value;
    	location.href="${contextPath}/pds/pList?pag=${pageVo.pag}&part="+part;
    }
  </script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <table class="table">
    <tr>
      <td colspan="2" style="text-align:center;border:none;"><h2>자 료 실 리 스 트</h2></td>
    </tr>
    <tr>
  		<td style="text-align:left;border:none;width:15%;">
  		  <form name="partForm">
  		    <select name="part" onchange="partCheck()" class="form-control">
  		      <option value="전체" ${part=='전체' ? 'selected' : ''}>전체</option>
  		      <option value="학습" ${part=='학습' ? 'selected' : ''}>학습</option>
  		      <option value="여행" ${part=='여행' ? 'selected' : ''}>여행</option>
  		      <option value="음식" ${part=='음식' ? 'selected' : ''}>음식</option>
  		      <option value="기타" ${part=='기타' ? 'selected' : ''}>기타</option>
  		    </select>
  		  </form>
  		</td>
  		<td style="text-align:right;border:none;">
  		  <button type="button" onclick="location.href='${contextPath}/pds/pInput';" class="btn btn-secondary">자료올리기</button>
  		</td>
    </tr>
  </table>
  <table class="table table-hover">
    <tr style="text-align:center;background-color:#ccc;">
      <th>번호</th>
      <th>자료제목</th>
      <th>올린이</th>
      <th>올린날자</th>
      <th>파일명(용량)</th>
      <th>분류</th>
      <th>다운횟수</th>
      <th>다운로드</th>
    </tr>
    <c:set var="curScrNo" value="${pageVo.curScrNo}"/>
    <c:forEach var="vo" items="${vos}">
	    <tr>
	      <td>${curScrNo}</td>
	      <td>${vo.title}</td>
	      <td>${vo.nickname}</td>
	      <td>${vo.fdate}</td>
	      <td>${vo.fname}(<fmt:formatNumber value="${vo.fsize / 1024}" pattern="#,###.#"/>KB)</td>
	      <td>${vo.part}</td>
	      <td>${vo.downnum}</td>
	      <td>
	        <a href="${contextPath}/resources/pds/${vo.rfname}" download>다운</a>
	      </td>
	    </tr>
	    <c:set var="curScrNo" value="${curScrNo-1}"/>
    </c:forEach>
  </table>
</div>
<!-- 블록 페이징처리 시작 -->
<div class="container" style="text-align:center">
  <ul class="pagination justify-content-center">
	  <c:set var="startPageNum" value="${pageVo.pag - (pageVo.pag-1)%pageVo.blockSize}"/>
	  <c:if test="${pageVo.pag != 1}">
	    <li class="page-item"><a href="${contextPath}/pds/pList?pag=1&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">◁◁</a></li>
	    <li class="page-item"><a href="${contextPath}/pds/pList?pag=${pageVo.pag-1}&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">◀</a></li>
	  </c:if>
	  <c:forEach var="i" begin="0" end="2">
	    <c:if test="${(startPageNum+i) <= pageVo.totPage}">
	      <c:if test="${pageVo.pag == (startPageNum+i)}">
	        <li class="page-item active"><a href="${contextPath}/pds/pList?pag=${startPageNum+i}&pageSize=${pageVo.pageSize}" class="page-link btn btn-secondary active" style="color:#666"><font color="#fff"><b>${startPageNum+i}</b></font></a></li>
	      </c:if>
	      <c:if test="${pageVo.pag != (startPageNum+i)}">
	        <li class="page-item"><a href="${contextPath}/pds/pList?pag=${startPageNum+i}&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">${startPageNum+i}</a></li>
	      </c:if>
	    </c:if>
	  </c:forEach>
	  <c:if test="${pageVo.pag != pageVo.totPage}">
	    <li class="page-item"><a href="${contextPath}/pds/pList?pag=${pageVo.pag+1}&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">▶</a></li>
	    <li class="page-item"><a href="${contextPath}/pds/pList?pag=${pageVo.totPage}&pageSize=${pageVo.pageSize}" class="page-link" style="color:#666">▷▷</a></li>
	  </c:if>
	</ul>
</div>
<!-- 블록 페이징처리 끝 -->
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>