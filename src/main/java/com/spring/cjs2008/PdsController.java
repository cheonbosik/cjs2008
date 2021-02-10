package com.spring.cjs2008;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2008.pagination.PageProcess;
import com.spring.cjs2008.pagination.PageVo;
import com.spring.cjs2008.service.PdsService;
import com.spring.cjs2008.vo.PdsVo;

@Controller
@RequestMapping("/pds")
public class PdsController {
	String msgFlag = "";
	
	@Autowired
	PdsService pdsService;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value="/pList", method=RequestMethod.GET)
	public String pListGet(HttpServletRequest request, @RequestParam(name="part", defaultValue="전체", required=false) String part, Model model) {
		//String part = request.getParameter("part")==null ? "전체" : request.getParameter("part");
		int pag = request.getParameter("pag")==null? 1 : Integer.parseInt(request.getParameter("pag"));
		//int pageSize = request.getParameter("pageSize")==null? 5 : Integer.parseInt(request.getParameter("pageSize"));
		
		PageVo pageVo = pageProcess.pagination(pag, 5, "pds", part);
		
		List<PdsVo> vos = pdsService.getPList(pageVo.getStartNo(), pageVo.getPageSize(), part);
		
		model.addAttribute("vos", vos);
		model.addAttribute("part", part);
		model.addAttribute("pageVo", pageVo);
		
		return "pds/pList";
	}
	
	@RequestMapping(value="/pInput", method=RequestMethod.GET)
	public String pInputGet() {
		return "pds/pInput";
	}

	@RequestMapping(value="/pInput", method=RequestMethod.POST)
	public String pInputPost(MultipartFile file, PdsVo vo) {
		pdsService.setPdsUpload(file, vo);
		
		msgFlag = "pdsInputOk";
		
		return "redirect:/msg/" + msgFlag;
	}
	
}
