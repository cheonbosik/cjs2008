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
  <title>calendar.jsp</title>
  <style>
		/*달력의 매주 일요일 텍스트 컬러 적용 부분*/
		#td1,#td8,#td15,#td22,#td29,#td36 {
			color: hotpink;
		}
		
		/*달력의 매주 토요일 텍스트 컬러 적용 부분*/
		#td7,#td14,#td21,#td28,#td35,#td42 {
			color: skyblue;
		}
		
		/*오늘날자에 배경색 표시  #f94e3f */
		.today {
			/*background-color: #f94e3f;*/
			font-size: 1.5em;
			font-family: cursive;
			color: #fff;
			text-align: center;
			-webkit-animation: glow 1s ease-in-out infinite alternate;
			-moz-animation: glow 1s ease-in-out infinite alternate;
			animation: glow 1s ease-in-out infinite alternate;
		}
		@-webkit-keyframes glow {
		  from {
		    text-shadow: 0 0 10px #fff, 0 0 20px #fff, 0 0 30px #e60073, 0 0 40px #e60073, 0 0 50px #e60073, 0 0 60px #e60073, 0 0 70px #e60073;
		  }
		  to {
		    text-shadow: 0 0 20px #fff, 0 0 30px #ff4da6, 0 0 40px #ff4da6, 0 0 50px #ff4da6, 0 0 60px #ff4da6, 0 0 70px #ff4da6, 0 0 80px #ff4da6;
		  }
		}
	</style>
</head>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<div class="container">
	<div class="container-fluid" style="margin-top: 30px;margin-bottom: 30px;width:90%;">
		<div class="row">
		  <div class="col-sm-12" style="background-color:#1c140d;font-size: 3em;text-align: center;">
		  	<label class="today">
		  	  ${yy}년 ${mm+1}월
		  	  (<button class="btn btn-secondary" onclick="location.href='${contextPath}/study/calendar'" title="오늘날짜"><i class="fa fa-home"></i></button>)
		  	</label>
		  </div>
		</div>
		<div class="row">
		  <div class="col-sm-12" style="background-color:#1c140d;text-align: center;">
		  	<br><!-- 공백 -->
		  </div>
		</div>
		<div class="row">
		  <div class="col-sm-1" style="background-color:#1c140d;text-align:center;padding-top: 350px;">
		  	<button class="btn btn-info" onclick="location.href='${contextPath}/study/calendar?yy=${yy-1}&mm=${mm}'" title="이전년도">&lt;&lt;</button>
		  </div>
		  <div class="col-sm-1" style="background-color:#1c140d;text-align: center;padding-top: 350px;">
		  	<button class="btn btn-info" onclick="location.href='${contextPath}/study/calendar?yy=${yy}&mm=${mm-1}'" title="이전월">&lt;</button>
		  </div>
		  <!-- 달력시작 -->
		  <div class="col-sm-8" style="background-color:#1c140d;font-size: 1em;text-align: left;height:700px;">
		  	<table class="table table-dark table-bordered" style="height:100%">
		  		<tr style="text-align: center;font-size: 2em;background-color: #263959;">
					<th style="color:hotpink;width:14%;vertical-align: middle;">일</th>
					<th style="width:14%;vertical-align: middle;">월</th>
					<th style="width:14%;vertical-align: middle;">화</th>
					<th style="width:14%;vertical-align: middle;">수</th>
					<th style="width:14%;vertical-align: middle;">목</th>
					<th style="width:14%;vertical-align: middle;">금</th>
					<th style="color:skyblue;width:14%;vertical-align: middle;">토</th>
				</tr>
				<tr>
					<!-- cnt 변수로  일주일마다 줄바꿈을 하기위해 선언-->
					<c:set var="cnt" value="1"/>
					
					<!-- 이전월 날자 출력부분  -->
				  	<c:forEach var="preDay" begin="${preLastDay - (startWeek - 2) }" end="${preLastDay}">
					  	<c:set var="ymd">${preYear}-${preMonth+1}-${preDay}</c:set>
					  	<td id="td${cnt}" style="color: #c8c8a9;">
					  		${preYear}-${preMonth+1}-${preDay}
				  		</td>
				  		<c:set var="cnt" value="${cnt=cnt+1}"/>
				  	</c:forEach>
				  	
				  	<!-- 해당월 날자 반복문 -->
				  	<c:forEach begin="1" end="${lastDay}" varStatus="i">
				  		<!-- 오늘날자와 동일한지 체크한후 todaySw에 값을 저장후  해당 td에 클래스 이름을 today 라고 부여 -->
					  	<c:set var="ymd">${yy}-${mm+1}-${i.count}</c:set>
					  	<c:set var="todaySw" value="${yy == toYear && mm == toMonth && i.count == toDay ? 1 : 0}"/>
					  	<td id="td${cnt}" ${todaySw == 1 ? 'class=today' : '' } onclick="ymdCheck('${cnt}','${ymd}')">
					  		${i.count} <c:if test="${todaySw == 1 }"><label class="today">오늘</label></c:if>
					  		<br><font color="#67D5B5"><fmt:formatNumber value="${ipkumArr[i.count-1] > 0 ? ipkumArr[i.count-1] : ''}" type="currency"/></font>
					  		<br><font color="#EE7785"><fmt:formatNumber value="${jichulArr[i.count-1] > 0 ? jichulArr[i.count-1] : ''}" type="currency"/></font>
				  		</td>
				  		<c:if test="${cnt % 7 == 0 }"> 
				  			</tr><tr>
				  		</c:if>
				  		<c:set var="cnt" value="${cnt=cnt+1}"/>
				  	</c:forEach>
				  	
				  	<!-- 다음월 날자 출력부분 -->
				  	 <c:forEach begin="${nextStartWeek}" end="7" varStatus="nextDay">
					  	<c:set var="ymd">${nextYear}-${nextMonth+1}-${nextDay.count}</c:set>
					  	<td id="td${cnt}" style="color: #c8c8a9;">
					  		${nextYear}-${nextMonth+1}-${nextDay.count}
				  		</td>
				  		<c:set var="cnt" value="${cnt=cnt+1}"/>
				  	</c:forEach>
				  	
				</tr>
		  	</table>
		  </div>
		  <!-- 달력끝 -->
		  <div class="col-sm-1" style="background-color:#1c140d;text-align: center;padding-top: 350px;">
		  	<button class="btn btn-info" onclick="location.href='${contextPath}/study/calendar?yy=${yy}&mm=${mm+1}'" title="다음월">&gt;</button>
		  </div>
		  <div class="col-sm-1" style="background-color:#1c140d;text-align: center;padding-top: 350px;">
		  	<button class="btn btn-info" onclick="location.href='${contextPath}/study/calendar?yy=${yy+1}&mm=${mm}'" title="다음년도">&gt;&gt;</button>
		  </div>
		</div>
		<div class="row">
		  <div class="col-sm-12" style="background-color:#1c140d;text-align: center;">
		  	<br><!-- 공백 -->
		  </div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>