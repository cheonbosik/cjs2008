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
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script>
    // 댓글 보이기/가리기
    $(document).ready(function(){
    	$("#reply").show();         // 처음 로딩시에 '전체 댓글'은 출력시킨다.
    	$("#replyViewBtn").hide();  // '댓글보이기'버튼은 감춘다.
    	//$(".replyBoxClose").hide(); // 모든 댓글 '닫기' 버튼 감춘다.
    	
    	$("#replyViewBtn").click(function(){  // 댓글 보이기 버튼 클릭시에...
    		$("#reply").slideDown(500);           // 댓글 리스트를 출력한다.
    		$("#replyViewBtn").hide();    // 댓글보이기 버튼은 감춘다.
    		$("#replyHiddenBtn").show();  // 댓글감추기 버튼은 보여준다.
    	});
    	$("#replyHiddenBtn").click(function(){  // 댓글 감추기 버튼 클릭시에...
    		$("#reply").slideUp(500);           // 댓글 리스트를 감춘다.
    		$("#replyViewBtn").show();    // 댓글보이기 버튼은 보여준다.
    		$("#replyHiddenBtn").hide();  // 댓글감추기 버튼은 감춘다.
    	});
    });
  
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
  	
  	// 댓글 삭제처리
  	function replyDel(idx) {
  		var ans = confirm("댓글을 삭제할까요?");
  		if(!ans) {
  			return false;
  		}
  		var query = {idx : idx}
  		$.ajax({
  			url   : "${contextPath}/board/bReplyDelete",
  			type  : "get",
  			data  : query,
  			success : function(data) {
  				if(data == 1) {
  					location.reload();
  				}
  			}
  		});
  	}
  	
  	// 대댓글 입력처리('대댓글 입력폼' 보이기처리)
  	function insertReply(idx,level,levelOrder,nickname) {
  		var insReply = "";
  		insReply += "<table style='width:90%'>";
  		insReply += "<tr>";
  		insReply += "<td style='border:none;width:85%;text-align:right;padding:0px;' class='reply_cont'>";
  		insReply += "<div class='form-group'>";
  		insReply += "<label for='content'>답변 댓글 달기:</label> &nbsp;";
  		insReply += "<input type='text' name='nickname' size='6' value='${snickname}' readonly/>";
  		insReply += "<textarea rows='3' class='form-control' id='content"+idx+"' name='content'>@"+nickname+"\n</textarea>";
  		insReply += "</div>";
  		insReply += "</td>";
  		insReply += "<td style='border:none;width:15%;text-align:left;padding:0px;' class='reply_cont'><br/><br/>";
  		insReply += "<input type='button' value='댓글달기' onclick='replyCheck("+idx+","+level+","+levelOrder+")'/>";
  		insReply += "</td>";
  		insReply += "</tr>";
  		insReply += "</table>";
  		insReply += "<hr style='margin:0px;'/>";
  		
  		$("#replyBoxOpenBtn"+idx).hide();    // '답글'버튼 가리기
  		$("#replyBoxCloseBtn"+idx).show();   // '닫기'버튼 보이기
  		//$("#replyBox"+idx).show();
  		$("#replyBox"+idx).slideDown(500);
  		$("#replyBox"+idx).html(insReply);
  	}
  	
  	// 대댓글 입력폼 닫기 버튼 클릭시 수행할내용
  	function closeReply(idx) {
  		$("#replyBoxOpenBtn"+idx).slideDown(500);    // '답글'버튼 보이기
  		$("#replyBoxCloseBtn"+idx).hide();   // '닫기'버튼 가리기
  		$("#replyBox"+idx).slideUp(500);           // 대댓글 입력폼 닫기
  	}
  	
  	// 대댓글입력처리(부모댓글에 대한 답변글입력)
  	function replyCheck(idx,level,levelOrder) {
  		var boardIdx = "${vo.idx}";
  		var mid = "${smid}";
  		var nickname = "${snickname}";
  		var hostip = "<%=request.getRemoteAddr()%>";
  		var content = "content"+idx;
  		var contentVal = document.getElementById(content).value;
  		
  		if(contentVal == "") {
  			alert("부모 댓글의 답변내용을 입력하세요.");
  			$("#"+content).focus();
  			return false;
  		}
  		else {
  			var query = {
  					boardIdx : boardIdx,
  					mid      : mid,
  					nickname : nickname,
  					hostip   : hostip,
  					content  : contentVal,
  					level    : level,
  					levelOrder : levelOrder
  			}
  			$.ajax({
  				url   : "${contextPath}/board/bContReplyInsert",
  				type  : "get",
  				data  : query,
  				success : function(data) {
  					location.reload();
  				}
  			});
  		}
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
    <input type="button" value="댓글보이기" id="replyViewBtn" class="btn btn-secondary">
    <input type="button" value="댓글가리기" id="replyHiddenBtn" class="btn btn-secondary">
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
        <c:if test="${cVo.level > 0}">  <!-- 대댓글(부모댓글의 대글들)의 경우는 모두 배경색 변경한다. -->
          <tr style="background-color:#eef;text-align:left;">
        </c:if>
        <c:if test="${cVo.level <= 0}">
	        <tr style="background-color:#eee;text-align:left;">
        </c:if>
          <c:if test="${cVo.level > 0}">  <!-- 대댓글(부모댓글의 대글들)의 경우는 모두 들여쓰기한다. -->
            <td style="text-align:left;">
              <c:forEach var="i" begin="1" end="${cVo.level}">&nbsp;&nbsp; </c:forEach>
                └ ${cVo.nickname}
          </c:if>
          <c:if test="${cVo.level <= 0}">
            <td style="text-align:left;">
              ${cVo.nickname}
          </c:if>
          <c:if test="${smid == cVo.mid || slevel == 0}">
            <a href="javascript:replyDel(${cVo.idx})"><i class="glyphicon glyphicon-remove"></i></a>
          </c:if>
          </td>
          <td style="text-align:left;">${fn:replace(cVo.content,newLine,"<br/>")}</td>
          <td>
            <c:if test="${vo.diffTime <= 24}">${fn:substring(cVo.wdate,11,19)}</c:if>
	        	<c:if test="${vo.diffTime > 24}">${fn:substring(cVo.wdate,0,10)}</c:if>
          </td>
          <td>${cVo.hostip}</td>
          <td>
            <input type="button" value="답글" id="replyBoxOpenBtn${cVo.idx}" onclick="insertReply('${cVo.idx}','${cVo.level}','${cVo.levelOrder}','${cVo.nickname}')"/>
            <input type="button" value="닫기" id="replyBoxCloseBtn${cVo.idx}" onclick="closeReply(${cVo.idx})" class="replyBoxClose" style="display:none;"/>
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