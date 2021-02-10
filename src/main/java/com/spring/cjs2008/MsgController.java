package com.spring.cjs2008;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MsgController {
	@RequestMapping(value="/msg/{msgFlag}", method=RequestMethod.GET)
	public String msgGet(@PathVariable String msgFlag, Model model, HttpSession session) {
		String nickname = session.getAttribute("snickname")==null ? "" : (String) session.getAttribute("snickname");
		String strLevel = session.getAttribute("sStrLevel")==null ? "" : (String) session.getAttribute("sStrLevel");
		
		if(msgFlag.equals("fileUploadOk")) {
			model.addAttribute("msg", "파일이 업로드 되었습니다.");
			model.addAttribute("url", "study/fileUpload");
		}
		else if(msgFlag.equals("fileUploadNo")) {
			model.addAttribute("msg", "파일이 업로드 실패~~");
			model.addAttribute("url", "study/fileUpload");
		}
		else if(msgFlag.equals("mInputOk")) {
			model.addAttribute("msg", "회원에 가입되셨습니다.\\n로그인후 사용해 주세요.");
			model.addAttribute("url", "member/mLogin");
		}
		else if(msgFlag.equals("mInputOk")) {
			model.addAttribute("msg", "회원에 가입 실패~~");
			model.addAttribute("url", "member/mInput");
		}
		else if(msgFlag.equals("mLoginOk")) {
			model.addAttribute("msg", nickname+"님("+strLevel+") 로그인 되셨습니다.");
			model.addAttribute("url", "main");
		}
		else if(msgFlag.equals("mLoginNo")) {
			model.addAttribute("msg", "로그인 실패~~");
			model.addAttribute("url", "member/mLogin");
		}
		else if(msgFlag.equals("mLogoutOk")) {
			session.invalidate();   // 모든 세션 종료
			model.addAttribute("msg", nickname+"님 로그아웃 되었습니다.");
			model.addAttribute("url", "main");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("msg", "메일이 정상적으로 발송되었습니다.");
			model.addAttribute("url", "mail/mailForm");
		}
		else if(msgFlag.equals("pwdConfirmNo")) {
			model.addAttribute("msg", "가입된 내역이 없습니다.");
			model.addAttribute("url", "member/mPwdSearch");
		}
		else if(msgFlag.equals("pwdConfirmOk")) {
			model.addAttribute("msg", "임시비밀번호를 발급하여 메일로 전송하였습니다.");
			model.addAttribute("url", "member/mLogin");
		}
		else if(msgFlag.equals("passCheckNo")) {
			model.addAttribute("msg", "비밀번호가 틀립니다. 확인하세요.");
			model.addAttribute("url", "member/mLogin");
		}
		else if(msgFlag.equals("mNicknameNo")) {
			model.addAttribute("msg", "현재 사용중인 닉네임입니다. 확인하세요.");
			model.addAttribute("url", "member/mUpdateCheck");
		}
		else if(msgFlag.equals("mUpdateOk")) {
			model.addAttribute("msg", "회원정보가 수정되었습니다.");
			model.addAttribute("url", "main");
		}
		else if(msgFlag.equals("memberDeleteOk")) {
			session.invalidate();
			model.addAttribute("msg", "탈퇴 되셨습니다.");
			model.addAttribute("url", "main");
		}
		else if(msgFlag.equals("adminMemberDeleteOk")) {
			model.addAttribute("msg", "삭제처리 하였습니다.");
			model.addAttribute("url", "admin/member/aMList");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("msg", "관리자 구역입니다.");
			model.addAttribute("url", "");
		}
		else if(msgFlag.equals("useNo")) {
			model.addAttribute("msg", "서비스를 사용하실 수 없습니다.\\n로그인후 사용하세요.");
			model.addAttribute("url", "member/mLogin");
		}
		else if(msgFlag.equals("use4No")) {
			model.addAttribute("msg", "정회원 이상만 사용하실수 있습니다.");
			model.addAttribute("url", "");
		}
		else if(msgFlag.equals("nInputOk")) {
			model.addAttribute("msg", "공지사항이 등록되었습니다.");
			model.addAttribute("url", "notify/nList");
		}
		else if(msgFlag.equals("nUpdateOk")) {
			model.addAttribute("msg", "공지사항이 수정되었습니다.");
			model.addAttribute("url", "notify/nList");
		}
		else if(msgFlag.equals("bInputOk")) {
			model.addAttribute("msg", "게시글이 저장 되었습니다.");
			model.addAttribute("url", "board/bList");
		}
		else if(msgFlag.equals("pdsInputOk")) {
			model.addAttribute("msg", "자료실에 자료파일이 저장 되었습니다.");
			model.addAttribute("url", "pds/pList");
		}

		
		
		
		
		// 예) msgFlag = "imgDeleteOk$"+fileCnt;
		// 앞의 예에서 특정 매개변수에 추가로 매개값이 넘어왔을때는 아래와 같이 처리한다.
		else if(msgFlag.substring(0, 11).equals("imgDeleteOk")) {
			model.addAttribute("msg", "게시판의 임시 그림파일("+msgFlag.substring(12)+"개)이 모두 삭제되었습니다.");
			model.addAttribute("url", "admin/file/tempDelete");
		}
		else if(msgFlag.substring(0, 10).equals("boardUpdNo")) {
			model.addAttribute("msg", "게시글의 비밀번호가 틀립니다. 확인하세요.");
			model.addAttribute("url", "board/bContent?"+msgFlag.substring(11));
		}
		else if(msgFlag.substring(0, 13).equals("boardUpdateOk")) {
			model.addAttribute("msg", "게시글의 내용이 수정되었습니다.");
			model.addAttribute("url", "board/bContent?"+msgFlag.substring(14));
		}
		else if(msgFlag.substring(0, 10).equals("boardDelNo")) {
			model.addAttribute("msg", "게시글이 삭제 실패");
			model.addAttribute("url", "board/bContent?"+msgFlag.substring(11));
		}
		else if(msgFlag.substring(0, 9).equals("bDeleteOk")) {
			model.addAttribute("msg", "게시글이 삭제되었습니다.");
			model.addAttribute("url", "board/bList?"+msgFlag.substring(10));
		}
		
		return "include/msg";
	}
}
