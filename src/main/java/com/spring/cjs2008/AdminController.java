package com.spring.cjs2008;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.cjs2008.service.AdminService;
import com.spring.cjs2008.vo.MemberVo;

@Controller
@RequestMapping("/admin")
public class AdminController {
	String msgFlag = "";
	
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value="/aCheck", method=RequestMethod.GET)
	public String aCheckGet() {
		return "admin/admin";
	}
	
	@RequestMapping(value="/aLeft", method=RequestMethod.GET)
	public String aLeftGet() {
		return "admin/leftMenu";
	}
	
	@RequestMapping(value="/aContent", method=RequestMethod.GET)
	public String aContentGet() {
		return "admin/content";
	}
	
	@RequestMapping(value="/guest/aGList", method=RequestMethod.GET)
	public String aGListGet() {
		return "admin/guest/aGList";
	}
	
	@RequestMapping(value="/member/aMList", method=RequestMethod.GET)
	public String aMListGet(HttpServletRequest request, HttpServletResponse response, Model model) {
	  // 페이징처리 준비 시작...
			int totRecCnt = adminService.totMemberRecCnt();  // 총레코드건수 구하기
			int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize")); // 한페이지 분량
			int blockSize = 3; // 한개 블록의 크기는 3으로 지정
			int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag"));  // 현재 페이지 번호
			int totPage = (totRecCnt % pageSize)==0 ? totRecCnt/pageSize : (int)(totRecCnt/pageSize) + 1; // 총페이지 수
			int startNo = (pag - 1) * pageSize; // 시작 인덱스 번호
			int curScrNo = totRecCnt - startNo; // 해당 페이지의 시작문항의 번호
			// 이까지 페이징 처리 준비 완료..
			
			List<MemberVo> vos = adminService.mList(startNo, pageSize);
			
			model.addAttribute("vos", vos);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("blockSize", blockSize);
			model.addAttribute("pag", pag);
			model.addAttribute("totPage", totPage);
			model.addAttribute("curScrNo", curScrNo);
			
		return "admin/member/aMList";
	}

	@RequestMapping(value="/member/aMList", method=RequestMethod.POST)
	public String amListPost(String delItems) {
		String[] idxs = delItems.split("/");
		
		for(String idx : idxs) {
			adminService.memberDelete(Integer.parseInt(idx));
		}
		
    msgFlag = "adminMemberDeleteOk";
		
		return "redirect:/msg/" + msgFlag;
	}
	
	@RequestMapping(value="/adminOut", method=RequestMethod.GET)
	public String adminOutGet() {
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value="/levelCheck", method=RequestMethod.GET)
	public int levelCheckGet(String mid, int level) {
		adminService.levelCheck(mid, level);
		
		return 1;
	}	
	
	@RequestMapping(value="/mInfor", method=RequestMethod.GET)
	public String mInforGet(String mid, Model model) {
		MemberVo vo = adminService.mSearchList(mid);
		
		model.addAttribute("vo", vo);
		
		return "admin/member/amInfor";
	}
	
	@RequestMapping(value="/file/tempDelete", method=RequestMethod.GET)
	public String tempDeleteGet() {
		return "admin/file/tempDelete";
	}
	
	
	@RequestMapping(value="/file/boardTempDelete", method=RequestMethod.GET)
	public String boardTempDeleteGet(HttpServletRequest request) {
		// board작업시에 생성된 'ckeditor/images'폴더의 모든 그림파일들을 삭제처리시킨다.
		@SuppressWarnings("deprecation")
		String uploadPath = request.getRealPath("/resources/ckeditor/images/");
		int fileCnt = adminService.imgDelete(uploadPath);
		
		msgFlag = "imgDeleteOk$"+fileCnt;
		
		return "redirect:/msg/" + msgFlag;
	}
	
}
