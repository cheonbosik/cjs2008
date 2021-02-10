package com.spring.cjs2008;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.cjs2008.service.MemberService;
import com.spring.cjs2008.vo.MailVo;
import com.spring.cjs2008.vo.MemberVo;

@Controller
@RequestMapping("/member")
public class MemberController {
  String msgFlag = "";
  
  @Autowired
  MemberService memberService;
  
  @Autowired
	BCryptPasswordEncoder passwordEncoder;
	
  // 로그인폼 호출
  @RequestMapping(value="/mLogin", method=RequestMethod.GET)
  public String mLoginGet() {
  	return "member/mLogin";
  }
  
  // 로그인 인증하기
  @RequestMapping(value="/mLogin", method=RequestMethod.POST)
  public String mLoginPost(HttpServletRequest request, HttpServletResponse response, String mid, String pwd, HttpSession session) {
  	MemberVo vo = memberService.getIdCheck(mid);
  	
  	if(vo != null && passwordEncoder.matches(pwd, vo.getPwd()) && vo.getUserDel().equals("NO")) {  // passwordEncoder.matches(입력받은 pwd, DB안의 pwd)
  		String strLevel = "";
  		if(vo.getLevel()==0) strLevel = "관리자";
  		else if(vo.getLevel()==1) strLevel = "특별회원";
  		else if(vo.getLevel()==2) strLevel = "우수회원";
  		else if(vo.getLevel()==3) strLevel = "정회원";
  		else if(vo.getLevel()==4) strLevel = "준회원";
  		else strLevel = "비회원";
  		
  		session.setAttribute("smid", mid);
  		session.setAttribute("snickname", vo.getNickname());
  		session.setAttribute("slevel", vo.getLevel());
  		session.setAttribute("sStrLevel", strLevel);
  		
  		// 아이디 쿠키로 저장,삭제 하기
  		String idCheck = request.getParameter("idCheck")==null ? "" : request.getParameter("idCheck");
  		if(idCheck.equals("on")) {  // 쿠키에 cmid 쿠키명를 저장
  			Cookie cookie = new Cookie("cmid", mid);
  			cookie.setMaxAge(60*60*24*3);
  			response.addCookie(cookie);
  		}
  		else {  // 저장된 cmid 쿠키를 삭제
  			Cookie[] cookies = request.getCookies();
  			for(int i=0; i<cookies.length; i++) {
  				String str = cookies[i].getName();
  				if(str.equals("cmid")) {
  					cookies[i].setMaxAge(0);
  					response.addCookie(cookies[i]);
  				}
  			}
  		}
  		
  		msgFlag = "mLoginOk";
  	}
  	else {
  		msgFlag = "mLoginNo";
  	}
  	
  	return "redirect:/msg/" + msgFlag;
  }
  
  @RequestMapping(value="/mLogout", method=RequestMethod.GET)
  public String memberGet() {
  	msgFlag = "mLogoutOk";
  	return "redirect:/msg/" + msgFlag;
  }
  
  @RequestMapping(value="/mInput", method=RequestMethod.GET)
  public String mInputGet() {
  	return "member/mInput";
  }
  
  @RequestMapping(value="/mInput", method=RequestMethod.POST)
  public String mInputPost(MultipartFile file, MemberVo vo) {
  	// 아이디 중복체크
  	if(memberService.getIdCheck(vo.getMid()) != null) {
  		msgFlag = "mInputNo";
  		return "redirect:/msg/" + msgFlag;
  	}
  	//닉네임 중복체크
  	if(memberService.getNickCheck(vo.getNickname()) != null) {
  		msgFlag = "mInputNo";
  		return "redirect:/msg/" + msgFlag;
  	}
  	
  	// 비밀번호 암호화 처리
  	vo.setPwd(passwordEncoder.encode(vo.getPwd()));
  	
  	int res = memberService.mInput(file, vo);
  	if(res == 1) {
			msgFlag = "mInputOk";
		}
		else {
			msgFlag = "mInputNo";
		}
		
		return "redirect:/msg/" + msgFlag;
  }
  
  @ResponseBody
  @RequestMapping(value="/idCheck", method=RequestMethod.GET)
  public String idCheckGet(String mid) {
  	String res = "0";
  	MemberVo vo = memberService.getIdCheck(mid);
  	if(vo != null) res = "1";
  	
  	return res;
  }

  @ResponseBody
  @RequestMapping(value="/nickCheck", method=RequestMethod.GET)
  public String nickCheckGet(String nickname) {
  	String res = "0";
  	MemberVo vo = memberService.getNickCheck(nickname);
  	if(vo != null) res = "1";
  	
  	return res;
  }
  
  @RequestMapping(value="/mPwdSearch", method=RequestMethod.GET)
  public String mPwdSearchGet() {
  	return "member/mPwdSearch";
  }
  
  /*
  @RequestMapping(value="/mPwdSearch", method=RequestMethod.POST)
  public String mPwdSearchPost(String mid, String email) {
  	String pwd = "";
  	
  	MemberVo vo = memberService.getPwdSearch(mid, email);
  	if(vo != null) {
  		// 임시비밀번호를 발급한다.
  		UUID uid = UUID.randomUUID();
  		pwd = uid.toString().substring(0,6);
  		memberService.setPwdChange(mid, passwordEncoder.encode(pwd));
  		String toMail = email;
  		String content = pwd;
  		
  		return "redirect:/mail/pwdConfirmMailForm/"+toMail+"/"+content+"/";
  	}
  	else {
	  	msgFlag = "pwdConfirmNo";
	  	return "redirect:/msg/" + msgFlag;
  	}
  }
  */
  
  // 1.사용자의 아이디와 메일주소가 일치하면 UUID기법을 이용하여 새로운 임시비밀번호를 만든다.(이때 생성된 비밀번호 앞자리6개만 사용함)
  // 2.생성된 비밀번호 6자리를 다시 암호화 시켜서 DB에 저장시켜준다.
  // 앞의 방법은 매개변수를 통해서 값을 넘겨서 매일을 보냈으나,
  // 3. 아래 방법은 객체를 통해서 값을 넘긴다. 이때 사용하는 RedirectAttributes객체는 매개변수는 물론, 객체까지도 넘길수 있게 한다.
  // 'RedirectAttributes'객체는 redirect에서의 값을 전달하는데 사용되는데, 이것의 메소드인 'addFlashAttribute'을 사용하면
  // 넘어가는 매개값이 '주소창'에 표시되지 않고 마치 post방식처럼 넘어간다.
//@RequestMapping(value="/mPwdSearch", method=RequestMethod.POST)
//public String mPwdSearchPost(RedirectAttributes attributes, MailVo mailVo) {
//	System.out.println("mailVo : " + mailVo);
//	MemberVo vo = memberService.getPwdSearch(mailVo.getMid(), mailVo.getToMail());
//	if(vo != null) {
//		// 임시비밀번호를 발급한다.
//		UUID uid = UUID.randomUUID();
//		mailVo.setPwd(uid.toString().substring(0,6));
//		memberService.setPwdChange(mailVo.getMid(), passwordEncoder.encode(mailVo.getPwd()));
//		
//		//attributes.addAttribute("mailVo", mailVo);
//		attributes.addFlashAttribute("mailVo", mailVo);
//		return "redirect:/mail/pwdConfirmMailForm";
//	}
//	else {
//  	msgFlag = "pwdConfirmNo";
//  	return "redirect:/msg/" + msgFlag;
//	}
//}

  // RedirectAttributes객체를 이용한 값의 전달
  @RequestMapping(value="/mPwdSearch", method=RequestMethod.POST)
  public String mPwdSearchPost(MailVo mailVo, RedirectAttributes redirectAttributes) {
  	MemberVo vo = memberService.getPwdSearch(mailVo.getMid(), mailVo.getToMail());
  	if(vo != null) {
  		// 임시비밀번호를 발급한다.
  		UUID uid = UUID.randomUUID();
  		mailVo.setPwd(uid.toString().substring(0,6));  // UUID에 의해서 새롭게 생성한 6자리 비밀번호
  		memberService.setPwdChange(mailVo.getMid(), passwordEncoder.encode(mailVo.getPwd())); // 새로 생성한 비밀번호를 암호화 시켜서 DB에 다시 저장시킨다.
  		redirectAttributes.addFlashAttribute("mailVo", mailVo);
  		
  		return "redirect:/mail/pwdConfirmMailForm";
  	}
  	else {
	  	msgFlag = "pwdConfirmNo";
	  	return "redirect:/msg/" + msgFlag;
  	}
  }

  // 아이디를 잊었을때 처리하는 메소드(화면) 
  @RequestMapping(value="/mMidSearch", method=RequestMethod.GET)
	public String mMidSearchGET() {
		return "member/mMidSearch";
	}
  
  // 아이디를 잊었을때 처리하는 메소드
	@RequestMapping(value="/mMidSearch", method=RequestMethod.POST)
	public String mMidSearchPOST(Model model, String name, String email) {
		List<MemberVo> mvos = memberService.getMidSearch(name,email);
		String foundMid[] = new String[mvos.size()];  // 검색해온 mid를 담아주기위한 배열(같은 메일주소로 여러개의 아이디가 존재할 수 있기에 배열처리함)
		MemberVo mvo = new MemberVo();
		for(int i=0; i<mvos.size(); i++) {
			mvo = mvos.get(i);
			foundMid[i] = mvo.getMid().substring(0,mvo.getMid().length()-2)+"**";
		}
		model.addAttribute("foundMid",foundMid);
		model.addAttribute("name",name);
		return "member/mSearchedMid";
	}
	
	// 회원 정보수정을 위한 비밀번호 체크 폼
	@RequestMapping(value="/mUpdateCheck", method=RequestMethod.GET)
	public String mUpdateCheckGet() {
		return "member/mUpdateCheck";
	}
	
  //회원 정보수정을 위한 비밀번호 체크
	@RequestMapping(value="/mUpdateCheck", method=RequestMethod.POST)
	public String mUpdateCheckPost(String pwd, HttpSession session, RedirectAttributes redirectAttributes) {
		String mid = (String) session.getAttribute("smid");
		MemberVo vo = memberService.getIdCheck(mid);
		
		if(!passwordEncoder.matches(pwd, vo.getPwd())) {
			msgFlag = "passCheckNo";
			return "redirect:/msg/" + msgFlag;
		}
		
		//session.setAttribute("spwd", pwd);
		redirectAttributes.addAttribute("mid", mid);
		redirectAttributes.addAttribute("pwd", pwd);
		return "redirect:/member/mUpdate";
	}
	
  //회원 정보수정을 위한 폼
	@RequestMapping(value="/mUpdate", method=RequestMethod.GET)
	public String mUpdateGet(Model model, String mid, String pwd) {
		MemberVo vo = memberService.getIdCheck(mid);
		vo.setPwd(pwd);
		model.addAttribute("vo", vo);
		
		return "member/mUpdate";
	}
	
  //회원 정보수정
	@RequestMapping(value="/mUpdate", method=RequestMethod.POST)
	public String mUpdatePost(MultipartFile mFile, Model model, MemberVo vo, HttpSession session) {
		String mid = (String) session.getAttribute("smid");
		String nickname = (String) session.getAttribute("snickname");
		
		// 닉네임 중복체크
		if(!nickname.equals(vo.getNickname())) {
			if(memberService.getNickCheck(vo.getNickname()) != null) {
				msgFlag = "mNicknameNo";
				return "redirect:/msg/" + msgFlag;
			}
		}
		
		// 비밀번호 암호화 처리(수정작업시 비밀번호 입력후 인증을 마친 비밀번호를 다시 꺼내어서 암호화 시킨후 다시 vo에 set시킨다.)
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		System.out.println("vo : " + vo);
		
		memberService.mUpdate(vo, mFile);
		
		session.setAttribute("snickname", vo.getNickname());
		
		msgFlag = "mUpdateOk";
		return "redirect:/msg/" + msgFlag;
	}
	
	@RequestMapping(value="/memberDelete", method=RequestMethod.GET)
	public String memberDeleteGet(HttpSession session) {
		String mid = (String) session.getAttribute("smid");
		
		memberService.memberDelete(mid);
		msgFlag = "memberDeleteOk";
		
		return "redirect:/msg/" + msgFlag;
	}
	
	
}
