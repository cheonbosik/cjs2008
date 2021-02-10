<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>amInfor.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs.jsp" %>
  <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${contextPath}/resources/js/woo.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <style>
    table, th, td {
      margin : 0px auto;
      width : 550px;
      border : 1px solid #ccc;
      padding: 12px;
      border-collapse: collapse;
    }
    th {
      text-align:center;
    }
  </style>
</head>
<body>
<p><br/></p>
<div class="container">
  <table>
    <tr>
      <td style="text-align:center; border:none;"><h2>회 원 정 보 수 정</h2></td>
    </tr>
  </table>
  <table>
    <tr>
      <th>아이디</th>
      <td>${vo.mid}</td>
    </tr>
    <tr>
      <th>성명</th>
      <td>${vo.name}</td>
    </tr>
    <tr>
      <th>별명</th>
      <td>${vo.nickname}</td>
    </tr>
    <tr>
      <th>성별</th>
      <td>${vo.gender}</td>
    </tr>
    <tr>
      <th>연락처</th>
      <td>${vo.tel}</td>
    </tr>
    <tr>
      <th>취미</th>
      <td>${vo.hobby}</td>
    </tr>
    <tr>
      <th>이메일</th>
      <td>${vo.email}</td>
    </tr>
    <tr>
      <th>주소</th>
      <td>${vo.address}</td>
    </tr>
    <tr>
      <th>자기소개</th>
      <td>${vo.content}</td>
    </tr>
    <tr>
      <th>정보공개</th>
      <td>
        <c:if test="${vo.userInfor eq 'OK'}">정보공개</c:if>
        <c:if test="${vo.userInfor eq 'NO'}">정보 비공개</c:if>
      </td>
    </tr>
    <tr>
      <td colspan=2 style="text-align:center">
        <button type="button" onclick="window.close()">창닫기</button>
      </td>
    </tr>
  </table>
</div>
<p><br/></p>
</body>
</html>