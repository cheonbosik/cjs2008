<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <meta charset="UTF-8">
  <title>aMList.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs.jsp" %>
  <script>
    function nWin(mid) {
    	var url = "${contextPath}/admin/mInfor?mid="+mid;
    	window.open(url,"mInfor","width=600px,height=600px");
    }
    
    function levelCheck() {
    	alert("회원정보를 변경하시려면 '정보변경'버튼을 클릭하세요!");
    }
    
  	function inforCheck(mid) {
  		var level = $("#level"+mid).val();
  		var query = {
				mid : mid,
				level : level
  		}
  		
  		$.ajax({
  			url : "${contextPath}/admin/levelCheck",
  			type: "get",
  			data: query,
  			success : function(data) {
  				if(data == 1) {
  					alert("정보변경 완료!");
  				}
  			}
  		}); // aJax종료
  	}  // inforCheck함수 종료
    
    //function delMemberCheck(idx) {
    //	var ans = confirm("삭제 하시겠습니까?");
    //	if(ans)
    //		location.href="${contextPath}/admin/memberDelete?idx="+idx;
    //}
    function mSelectDelCheck() {
    	var ans = confirm("선택한 항목을 모두 삭제 하시겠습니까?");
    	if(ans) {
    		var delItems = "";
    		for(var i=0; i<myform.chk.length; i++) {
    			if(myform.chk[i].checked==true) delItems += myform.chk[i].value + "/";
    		}
    		//alert("선택된 항목의 목록값? " + delItems);
    		myform.delItems.value = delItems;
    		myform.submit();
    	}
    }
    //전체 선택
    $(function() {
    	$("#checkAll").click(function() {
    		if($("#checkAll").prop("checked")) {
    		  $(".chk").prop("checked",true);
    	  }
    	  else {
    		  $(".chk").prop("checked",false);
    	  }
    	});
    });
    
    // 선택항목의 반전
    $(function() {
    	$("#reverseAll").click(function() {
    		$(".chk").prop("checked", function() {
    			return !$(this).prop("checked");
    		});
    	});
    });
  </script>
  <style>
    table, h2 {
      margin : 0 auto;
      text-align : center;
    }
  </style>
</head>
<body>
<p><br/></p>
<div class="container">
<form name="myform" method="post">
  <h2>전체 회원 리스트</h2>
  <table class="table table-hover">
    <thead class="thead-light">
	    <tr>
	      <td colspan=9 style="text-align:left;border:none;">
	        <input type="checkbox" id="checkAll"/>전체선택/해제 &nbsp; &nbsp;
	        <input type="checkbox" id="reverseAll"/>전체반전 &nbsp; &nbsp;
	        <input type="button" value="선택항목 삭제" onclick="mSelectDelCheck()"/>
	      </td>
	    </tr>
      <tr style="text-align:center;">
        <th>선택</th>
        <th>번호</th>
        <th>아이디</th>
        <th>별명</th>
        <th>성명</th>
        <th>연락처</th>
        <th>가입일</th>
        <th>등급</th>
        <th>탈퇴유무</th>
      </tr>
    </thead>
    <c:forEach var="vo" items="${vos}">
      <tr style="text-align:center;">
        <td>
            <input type="checkbox" class="chk" name="chk" value="${vo.idx}"/>
        </td>
        <td>${curScrNo}</td>
        <td>
          <a href="javascript:nWin('${vo.mid}')">${vo.mid}</a>
        </td>
        <td>${vo.nickname}</td>
        <td>
          <c:if test="${vo.userInfor eq 'OK' || slevel == 0 || smid == vo.mid}">${vo.name}</c:if>
          <c:if test="${vo.userInfor ne 'OK' && slevel != 0 && smid != vo.mid}">비공개</c:if>
        </td>
        <td>
          <c:if test="${vo.userInfor eq 'OK' || slevel == 0 || smid == vo.mid}">${vo.tel}</c:if>
          <c:if test="${vo.userInfor ne 'OK' && slevel != 0 && smid != vo.mid}">비공개</c:if>
        </td>
        <td>
          <c:if test="${vo.userInfor eq 'OK' || slevel == 0 || smid == vo.mid}">${fn:substring(vo.joinday,0,10)}</c:if>
          <c:if test="${vo.userInfor ne 'OK' && slevel != 0 && smid != vo.mid}">비공개</c:if>
        </td>
        <td>
        <%-- <form name="myLevelForm" method="get" action="<%=request.getContextPath()%>/admin/levelCheck"> --%>
        <form name="myLevelForm">
          <select name="level" id="level${vo.mid}" onchange="levelCheck()">
            <option value="4" <c:if test="${vo.level == 4}"> selected </c:if>>준회원</option>
            <option value="3" <c:if test="${vo.level == 3}"> selected </c:if>>정회원</option>
            <option value="2" <c:if test="${vo.level == 2}"> selected </c:if>>우수회원</option>
            <option value="1" <c:if test="${vo.level == 1}"> selected </c:if>>특별회원</option>
            <option value="0" <c:if test="${vo.level == 0}"> selected </c:if>>관리자</option>
          </select>
          <input type="button" value="정보변경" onclick="inforCheck('${vo.mid}')"/>
          <input type="hidden" name="mid" id="mid" value="${vo.mid}"/>
        </form>
      </td>
        <td>
          <c:if test="${vo.userDel == 'OK'}"><font color="red">탈퇴신청</font></c:if>
          <c:if test="${vo.userDel != 'OK'}">정상</c:if>
        </td>
      </tr>
      <c:set var="curScrNo" value="${curScrNo - 1}"/>
    </c:forEach>
  </table>
  <input type="hidden" name="delItems"/>
</form>
</div>
<br/>
<!-- 아래로 블록페이징 처리 -->
<div class="container">
  <c:set var="pageSize" value="${pageSize}"/>
  <c:set var="blockSize" value="${blockSize}"/>
  <c:set var="pag" value="${pag}"/>
  <c:set var="totPage" value="${totPage}"/>
  <c:set var="startPageNum" value="${pag - (pag-1)%blockSize}"/>
  <ul class="pagination justify-content-center pagination-sm">
    <c:if test="${pag != 1}">
      <li class="page-item"><a href="amList?pag=1" class="page-link" style="color:#777">첫페이지</a></li>
      <li class="page-item"><a href="amList?pag=${pag-1}" class="page-link" style="color:#777">이전</a></li>
    </c:if>
    <c:forEach var="i" begin="0" end="2">  <!-- 하나의 블록페이지의 갯수를 3개로 지정함. -->
      <c:if test="${(startPageNum+i) <= totPage}">
        <c:if test="${pag == (startPageNum+i)}">
          <li class="page-item"><a href="amList?pag=${startPageNum+i}" class="page-link btn btn-secondary active" style="color:#fff">${startPageNum+i}</a></li>
        </c:if>
        <c:if test="${pag != (startPageNum+i)}">
          <li class="page-item"><a href="amList?pag=${startPageNum+i}" class="page-link" style="color:#777">${startPageNum+i}</a></li>
        </c:if>
      </c:if>
    </c:forEach>
    <c:if test="${pag != totPage}">
      <li class="page-item"><a href="amList?pag=${pag+1}" class="page-link" style="color:#777">다음</a></li>
      <li class="page-item"><a href="amList?pag=${totPage}" class="page-link" style="color:#777">마지막페이지</a></li>
    </c:if>
  </ul>
</div>
<!-- 여기까지 블록페이징 처리 -->
<br/>
<p><br/></p>
</body>
</html>