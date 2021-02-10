<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>bContent.jsp(글내용보기)</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script>
    // 게시글 업데이트
  	function updBoardCheck() {
  		var pwd = myform.pwd.value;
  		location.href = "${contextPath}/board/boardUpd?idx=${vo.idx}&pwd="+pwd+"&pag=${pag}&pageSize=${pageSize}";
  	}
  	
    // 게시글 삭제
  	function delBoardCheck() {
  		var ans = confirm("현재 게시글을 삭제 하시겠습니까?");
  		if(ans) {
	  		var pwd = myform.pwd.value;
	  		location.href = "${contextPath}/board/boardDel?idx=${vo.idx}&pwd="+pwd+"&pag=${pag}&pageSize=${pageSize}";
  		}
  	}
  	
  	// 게시글의 댓글 입력
  	function reCheck() {
  		var boardIdx = "${vo.idx}";
  		var mid = "${smid}";
  		var nickname = "${snickname}";
  		var hostip = "<%=request.getRemoteAddr()%>";
  		var content = contForm.content.value;
  		if(content == "") {
  			alert("댓글을 입력하세요!");
  			conForm.content.focus();
  			return false;
  		}
  		var query = {
  				boardIdx : boardIdx,
  				mid      : mid,
  				nickname : nickname,
  				hostip   : hostip,
  				content  : content
  		}
  		$.ajax({
  			url  : "${contextPath}/board/bReplyInsert",
  			type : "get",
  			data : query,
  			success : function(data) {
  				if(data == 1) {
  					//alert("댓글 입력완료!!");
	  				location.reload();
  				}
  			}
  		});
  	}
  </script>
  <style>
    table, h2 {
      width: 80%;
      margin: 0 auto;
    }
    th, td {
      padding : 10px;
      border : 1px solid #ccc;
      text-align: center;
    }
    th {
      background-color : gray;
    }
    .tblLeft {
      text-align:left;
    }
    .tblCenter {
      text-align:center;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<div class="container">
  <h2>글 내 용 보 기</h2>
  <form name="myform">
    <table class="table">
      <tr>
        <th class="tblCenter" style="width:20%;">글쓴이</th>
        <td>${vo.name}</td>
        <th class="tblCenter">글쓴날자</th>
        <td>${vo.wdate}</td>
      </tr>
      <tr>
        <th class="tblCenter">전자우편</th>
        <td>${vo.email}</td>
        <th class="tblCenter">접속IP</th>
        <td>${vo.hostip}</td>
      </tr>
      <tr>
        <th class="tblCenter">조회수</th>
        <td>${vo.readnum}</td>
        <th class="tblCenter">홈페이지</th>
        <td>${vo.homepage}</td>
      </tr>
      <tr>
        <th class="tblCenter">글제목</th>
        <td colspan="3" class="tblLeft">${vo.title}</td>
      </tr>
      <tr>
        <th class="tblCenter">글내용</th>
        <td colspan="3" class="tblLeft">
          ${fn:replace(vo.content, newLine, "<br/>")}
        </td>
      </tr>
      <tr>
        <th>비밀번호</th>
        <td colspan="3" class="tblLeft">
          <input type="password" name="pwd"/>
        </td>
      </tr>
      <tr>
        <td colspan="4">
          <c:if test="${smid == vo.mid}">
	          <input type="button" value="수정" onclick="updBoardCheck()"/> &nbsp;
	          <input type="button" value="삭제" onclick="delBoardCheck()"/> &nbsp;
          </c:if>
          <input type="button" value="돌아가기" onclick="location.href='${contextPath}/board/bList?pag=${pag}&pageSize=${pageSize}';"/> &nbsp;
        </td>
      </tr>
    </table>
  </form>
</div>
<!-- 댓글 출력처리 시작 -->
<div class="container">
  <div>
    <input type="button" value="댓글보이기" id="replyView" class="btn btn-secondary">
    <input type="button" value="댓글가리기" id="replyHidden" class="btn btn-secondary">
  </div>
  <br/>
  <div id="reply">
    <table class="table table-borderless table-hover table-sm">
      <tr>
        <th>작성자</th>
        <th>댓글내용</th>
        <th>작성일자</th>
        <th>접속IP</th>
        <th>답글</th>
      </tr>
      <c:forEach var="cVo" items="${cVos}">  <!-- 댓글 -->
        <tr>
          <td>${cVo.nickname}</td>
          <td>${fn:replace(cVo.content,newLine,"<br/>")}</td>
          <td>${cVo.wdate}</td>
          <td>${cVo.hostip}</td>
          <td>
            <input type="button" value="답글" id="" onclick=""/>
          </td>
        </tr>
        <tr>
          <td colspan="5"><div id="replyBox${cVo.idx}"></div></td>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>
<!-- 댓글 출력처리 끝 -->
<!-- <hr align="center" style="width:80%;"/> -->
<hr/>
<!-- 댓글 입력처리 시작 -->
<div class="container">
  <form name="contForm" method="post">
    <table class="table table-sm">
      <tr>
        <td style="border:none; width:90%; text-align:left;">
          <div class="form-group">
            <label for="content">댓 글 달 기</label> &nbsp;
            <input type="text" name="nickname" value="${snickname}" readonly/>
            <textarea rows="4" name="content" id="content" class="form-control"></textarea>
          </div>
        </td>
        <td style="border:none;"><br/><br/>
          <input type="button" value="댓글달기" onclick="reCheck()"/><br/><br/>
          <input type="button" value="돌아가기" onclick="location.href='${contextPath}/board/bList?pag=${pag}&pageSize=${pageSize}';"/>
        </td>
      </tr>
    </table>
    <input type="hidden" name="idx" value="${vo.idx}"/> <!-- 원본글의 고유번호(idx) -->
    <%-- <input type="hidden" name="hostip" value="<%=request.getRemoteAddr()%>"/> --%>
    <input type="hidden" name="pag" value="${pag}"/>
    <input type="hidden" name="pageSize" value="${pageSize}"/>
  </form>
</div>
<!-- 댓글 입력처리 끝 -->
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>