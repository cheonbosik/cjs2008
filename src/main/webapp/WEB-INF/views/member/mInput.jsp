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
  <title>mInput.jsp</title>
  <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${contextPath}/resources/js/woo.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script>
    var idKey = 0;
    var nickKey = 0;
    // 아이디 중복처리(aJax처리)
    $(document).ready(function() {
    	$("#subm").attr("disabled","disabled");
    	$("#idCheck").click(function() {
    		if($("#mid").val().trim() == "") {
    			alert("아이디를 입력하세요!");
    			$("#mid").focus();
    			return false;
    		}
    		else if($("#mid").val().length<4 || $("#mid").val().length>20) {
    			alert("아이디는 4~20자로 하세요!");
    			$("#mid").focus();
    			return false;
    		}
    		
    		var query = {
    			mid : $("#mid").val() 
    		}
    		
	    	$.ajax({
	    		url : "${contextPath}/member/idCheck",
	    		type: "get",
	    		data: query,
	    		success : function(data) {
	    			if(data == "1") {
	    				alert("이미 사용중인 아이디 입니다.");
	    				$("#subm").attr("disabled","disabled");
	    				$("#mid").focus();
	    			}
	    			else {
	    				alert("사용 가능한 아이디 입니다.");
	    				idKey = 1;
	    				if(nickKey == 1) {
	    					$("#subm").removeAttr("disabled");
	    				}
    				  $("#mid").attr("readonly",true);
	    				$("#pwd").focus();
	    			}
	    		}
	    	}); // aJax 종료
    		
    	}); // jQuery 종료
    	
    	// 닉네임 aJax체크
    	$("#nickCheck").click(function() {
    		if($("#nickname").val().trim() == "") {
    			alert("닉네임을 입력하세요!");
    			$("#nickname").focus();
    			return false;
    		}
    		else if($("#nickname").val().length<2 || $("#nickname").val().length>20) {
    			alert("닉네임은 2~20자로 하세요!");
    			$("#nickname").focus();
    			return false;
    		}
    		
    		var query = {
    			nickname : $("#nickname").val() 
    		}
    		
	    	$.ajax({
	    		url : "${contextPath}/member/nickCheck",
	    		type: "get",
	    		data: query,
	    		success : function(data) {
	    			if(data == "1") {
	    				alert("이미 사용중인 닉네임 입니다.");
	    				$("#subm").attr("disabled","disabled");
	    				$("#nickname").focus();
	    			}
	    			else {
	    				alert("사용 가능한 닉네임 입니다.");
	    				nickKey = 1
	    				if(idKey == 1) {
	    					$("#subm").removeAttr("disabled");
	    				}
    					$("#nickname").attr("readonly",true);
	    			}
	    		}
	    	}); // aJax 종료
    		
    	}); // jQuery 종료
    	
    });  // javascript 종료
    
    function fCheck() {
    	var regExpId = /^[a-z|A-Z|0-9|_]*$/; //아이디 체크
      var regExpName = /^[가-힣]+$/;   // 이름은 한글이름만 가능
      var regExpTel = /^\d{2,3}-\d{3,4}-\d{4}$/;  // 전화번호체크
      var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;  // 이메일체크
      
      var mid = myform.mid.value;
      var pwd = myform.pwd.value;
      var tel = myform.tel1.value + "-" + myform.tel2.value + "-" + myform.tel3.value;
      var email = myform.email.value;
      var name = myform.name.value;
      var nickname = myform.nickname.value;
      var address = $("#sample4_postcode").val()+"/"+$("#sample4_roadAddress").val()+"/"+$("#sample4_extraAddress").val()+"/"+$("#sample4_detailAddress").val();
      if(address=="///") address="";
      var birthday = myform.birthday.value;
      var job = myform.job.value;
      
      // 업로드 사진 체크를 위한 변수들~
      var file = myform.file.value;  // 업로드 파일명
	  	var ext = file.substring(file.lastIndexOf(".")+1);  // 확장자 발췌
	  	var uExt = ext.toUpperCase();    // 확장자를 대문자로...
	  	var maxSize = 1024 * 1024 * 10;  // 최대 10MByte
      if(mid.length<4 || mid.length>20 || !regExpId.test(mid)) {
    	  alert("아이디는 영문/숫자 조합, 4~20자 이내로 작성하세요!");
    	  myform.mid.focus();
    	  return false;
      }
      else if(pwd.length < 4 || pwd.length > 20) {
    	  alert("비밀번호는 4~20자 이내로 작성하세요");
    	  myform.pwd.focus();
    	  return false;
      }
      else if(!regExpName.test(name)) {
    	  alert("성명은 한글만 가능합니다.");
    	  myform.name.focus();
    	  return false;
      }
      else if(nickname=="" || nickname.length < 2 || nickname.length > 20) {
    	  alert("닉네임은 2~20자 이내로 작성하세요");
    	  myform.nickname.focus();
    	  return false;
      }
      else if(!regExpTel.test(tel)) {
    	  alert("전화번호를 확인하세요.");
    	  myform.tel2.focus();
    	  return false;
      }
      else if(email == "") {
    	  alert("이메일은 필수 입력입니다.");
    	  myform.email.focus();
    	  return false;
      }
      else if(email != "" && !regExpEmail.test(email)) {
    	  alert("이메일 형식을 확인하세요.");
    	  myform.email.focus();
    	  return false;
      }
      else {
    	  if(file != "") {
	    	  var fileSize = document.getElementById("file").files[0].size;
	      	//myform.file.value = fileSize;
	      	
	      	if(uExt != "ZIP" && uExt != "JPG" && uExt != "GIF" && uExt != "PNG") {
	      		alert("업로드 가능한 파일은 'zip/jpg/gif/png'파일만 가능합니다.");
	      		return false;
	      	}
	      	else if(fileSize > maxSize) {
	      		alert("업로드 파일의 최대용량은 10MByte입니다.");
	      		return false;
	      	}
	      	else if(file.indexOf(" ") != -1) {
	      		alert("업로드할 파일명에는 공백을 포함할 수 없습니다.");
	      		return false;
	      	}
    	  }
    	  var hobbys = "";
    	  for(i=0; i<8; i++) {
    		  if(myform.hobbys[i].checked == true)
    			  hobbys += myform.hobbys[i].value + "/";
    	  }
    	  
    	  if(job == "") myform.job.value = "기타";
    	  myform.tel.value = tel;
    	  myform.hobby.value = hobbys;
    	  myform.address.value = address;
    	  
    	  //alert("hobbys:"+hobbys+"/tel:"+tel+"/file:"+file+"/birthday:"+birthday+"/address:"+address);
    	  //alert("file:" + file);
    	  
    	  myform.submit();
      }
      
    }
  </script>
  <style>
    table, td {
      margin : 0px auto;
      width : 650px;
      border : 1px solid #ccc;
      /* padding: 12px; */
      /* height: 30px; */
      border-collapse: collapse;
      vertical-align: middle;
    }
    .ltbl {
      text-align:center;
      background-color:#ddd;
      vertical-align: middle;
      border:1px solid #ccc;
    }
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
<form name="myform" method="post" enctype="multipart/form-data">
  <table class="table table-borderless" style="border:none;">
    <tr>
      <td style="text-align:center; border:none;"><h2>회 원 가 입</h2></td>
    </tr>
  </table>
  ▷ 필수입력
  <table class="table">
    <tr>
      <td class="ltbl" style="width:20%;">아이디</td>
      <td>
        <div class="input-group mb-3">
			    <input type="text" class="form-control" name="mid" id="mid" placeholder="아이디를 입력하세요" autofocus>
			    <div class="input-group-append">
			      <button class="btn btn-secondary" id="idCheck" type="button">아이디중복체크</button>  
			     </div>
			  </div>
      </td>
    </tr>
    <tr>
      <td class="ltbl"><label for="pwd">비밀번호</label></td>
      <td>
        <div class="form-group">
				  <input type="password" class="form-control" id="pwd" name="pwd">
				</div>
      </td>
    </tr>
    <tr>
      <td class="ltbl"><label for="name">성명</label></td>
      <td>
        <input type="text" class="form-control" id="name" name="name">
      </td>
    </tr>
    <tr>
      <td class="ltbl">별명</td>
      <td>
        <div class="input-group mb-3">
			    <input type="text" class="form-control" name="nickname" id="nickname" placeholder="닉네임을 입력하세요">
			    <div class="input-group-append">
			      <button class="btn btn-secondary" id="nickCheck" type="button">닉네임중복체크</button>  
			     </div>
			  </div>
      </td>
    </tr>
    <tr>
      <td class="ltbl">성별</td>
      <td>
        <div class="form-check-inline">
	        <label class="form-check-label">
				    <input type="radio" class="form-check-input" name="gender" value="남자" checked>남자 &nbsp;
				  </label>
	        <label class="form-check-label">
				    <input type="radio" class="form-check-input" name="gender" value="여자">여자
				  </label>
			  </div>
      </td>
    </tr>
    <tr>
      <td class="ltbl">생일</td>
      <c:set var="now" value="<%=new java.util.Date() %>"/>
      <c:set var="birthday"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/></c:set>
      <td><input type="date" name="birthday" id="birthday" value="${birthday}"/></td>
    </tr>
    <tr>
      <td class="ltbl">연락처</td>
      <td>
        <div class="input-group mt-3 mb-3">
	        <select name="tel1">
	          <option value="" disabled selected>선택</option>
	          <option value="010">010</option>
	          <option value="02">서울</option>
	          <option value="031">경기</option>
	          <option value="032">인천</option>
	          <option value="041">충남</option>
	          <option value="042">대전</option>
	          <option value="043">충북</option>
	          <option value="051">부산</option>
	          <option value="055">경남</option>
	          <option value="061">전남</option>
	          <option value="062">광주</option>
	          <option value="063">전북</option>
	          <option value="064">제주</option>
	        </select>&nbsp;-&nbsp;
	        <input type="text" class="form-control" name="tel2" maxlength=4 placeholder="국번">&nbsp;-&nbsp;
				  <input type="text" class="form-control" name="tel3" maxlength=4 placeholder="전화번호">
        </div>
      </td>
    </tr>
    <tr>
      <td class="ltbl"><label for="email">이메일</label></td>
      <td>
        <div class="form-group">
				  <input type="text" class="form-control" id="email" name="email">
				</div>
      </td>
    </tr>
  </table>
  ▷ 선택사항
  <table class="table">
    <tr>
      <td class="ltbl"><label for="homepage">홈페이지</label></td>
      <td>
        <div class="form-group">
				  <input type="text" class="form-control" id="homepage" name="homepage">
				</div>
      </td>
    </tr>
    <tr>
      <th class="ltbl">주소</th>
      <td>
      	<div class="form-group">
	        <input type="text" id="sample4_postcode" placeholder="우편번호">
					<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기"><br/>
					<input type="text" class="form-control"  id="sample4_roadAddress" placeholder="도로명주소">
					<input type="text" class="form-control"  id="sample4_extraAddress" placeholder="참고항목">
					<input type="text" class="form-control"  id="sample4_detailAddress" placeholder="상세주소">
					<!-- <input type="text" id="jibunAddress" placeholder="지번주소"> -->
					<!-- <span id="guide" style="color:#999;display:none"></span> -->
					<!-- <input type="text" id="extraAddress" placeholder="참고항목"> -->
				</div>
      </td>
    </tr>
    <tr>
      <th class="ltbl">직업</th>
      <td>
        <select name="job" size=1 class="form-control">
	        <option value="" disabled selected>직업선택</option>
			    <option value="학생">학생</option>
			    <option value="회사원">회사원</option>
			    <option value="공무원">공무원</option>
			    <option value="군인">군인</option>
			    <option value="자영업">자영업</option>
			    <option value="프리랜서">프리랜서</option>
			    <option value="기타">기타</option>
	      </select>
      </td>
    </tr>
    <tr>
      <th class="ltbl">취미</th>
      <td>
        <input type="checkbox" name="hobbys" value="등산"/>등산 &nbsp;
        <input type="checkbox" name="hobbys" value="낚시"/>낚시 &nbsp;
        <input type="checkbox" name="hobbys" value="바둑"/>바둑 &nbsp;
        <input type="checkbox" name="hobbys" value="영화감상"/>영화감상 &nbsp;
        <input type="checkbox" name="hobbys" value="승마"/>승마 &nbsp;
        <input type="checkbox" name="hobbys" value="여행"/>여행 &nbsp;
        <input type="checkbox" name="hobbys" value="수집"/>수집 &nbsp;
        <input type="checkbox" name="hobbys" value="기타"/>기타
      </td>
    </tr>
    <tr>
      <td class="ltbl" style="width:20%;"><label for="content">자기소개</label></td>
      <td>
        <div class="form-group">
				  <textarea class="form-control" rows="5" id="content" name="content"></textarea>
				</div>
      </td>
    </tr>
    <tr>
      <td class="ltbl" style="width:20%;">사진</td>
      <td>
        <div>
          <input type="file" id="file" name="file" accept=".gif,.jpg,.png">
        </div>
      </td>
    </tr>
    <tr>
      <td class="ltbl">정보공개</td>
      <td>
        <div class="form-check-inline">
	        <label class="form-check-label">
				    <input type="radio" class="form-check-input" name="userInfor" value="OK" checked>공개 &nbsp;
				  </label>
	        <label class="form-check-label">
				    <input type="radio" class="form-check-input" name="userInfor" value="NO">비공개
				  </label>
			  </div>
      </td>
    </tr>
    <tr>
      <td colspan=2 style="text-align:center">
        <button type="button" class="btn btn-secondary" id="subm" onclick="fCheck()">회원가입</button>
        <button type="button" class="btn btn-secondary" onclick="location.reload();">다시입력</button>
        <button type="button" class="btn btn-secondary" onclick="location.href='${contextPath}/member/mLogin';">돌아가기</button>
      </td>
    </tr>
  </table>
  <input type="hidden" name="tel"/>
  <input type="hidden" name="hobby"/>
  <input type="hidden" name="address"/>
</form>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>