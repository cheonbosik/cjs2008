package com.spring.cjs2008;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.cjs2008.service.GuestService;
import com.spring.cjs2008.vo.GuestVo;

@Controller
@RequestMapping("/guest")
public class GuestController {
	@Autowired
	GuestService guestService;
	
	//방명록 작성폼 불러오기
	@RequestMapping(value="/gInput",method=RequestMethod.GET)
	public String gInputGet() {
		return "guest/gInput";
	}
	
	//방명록 작성하기
	@RequestMapping(value="/gInput",method=RequestMethod.POST)
	public String gInputPost(GuestVo vo) {
		guestService.gInput(vo);
		return "redirect:/guest/gList";
	}
	
	//방명록 리스트
	@RequestMapping(value="/gList",method=RequestMethod.GET)
	public String gListGet(HttpServletRequest request, Model model) {
	  // 블록페이지를 위한 준비 시작
    int pag = request.getParameter("pag")==null ? 1 : Integer.parseInt(request.getParameter("pag"));
    int pageSize = request.getParameter("pageSize")==null ? 5 : Integer.parseInt(request.getParameter("pageSize"));
    int totRecCnt = guestService.totRecCnt();
    int totPage = (totRecCnt % pageSize) == 0 ? totRecCnt/pageSize : (int) totRecCnt/pageSize + 1;
    int startNo = (pag - 1) * pageSize;
    int curScrNo = totRecCnt - startNo;
    int blockSize = 3; // 블록의 크기를 3으로 지정함.
    // 블록페이지를 위한 준비 끝
    
    List<GuestVo> vos = guestService.getGuestList(startNo, pageSize);
    
    model.addAttribute("vos", vos);
    
    // 블록페이지를 위해 넘겨주는 변수들
    request.setAttribute("pag", pag);
    request.setAttribute("totPage", totPage);
    request.setAttribute("curScrNo", curScrNo);
    request.setAttribute("blockSize", blockSize);
		
		return "guest/gList";
	}
	
	//방명록 수정폼 불러오기
	@RequestMapping(value="/gUpdate",method=RequestMethod.GET)
	public String  gUpdateGet(int idx,Model model) {
		GuestVo vo = guestService.getUpdate(idx);
		model.addAttribute("vo",vo);
		return "guest/gUpdate";
	}
	
	//방명록 수정하기
	@RequestMapping(value="/gUpdate",method=RequestMethod.POST)
	public String gUpdatePost(GuestVo vo) {
		guestService.gUpdate(vo);
		return "redirect:/guest/gList";
	}
	
	@RequestMapping(value="gDelete")
	public String gDeleteGet(int idx) {
		guestService.gDelete(idx);
		return "redirect:/guest/gList";
	}

	
}
