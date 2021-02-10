package com.spring.cjs2008;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2008.service.StudyService;

@Controller
@RequestMapping("/study")
public class StudyController {
	String msgFlag = "";
	
	@Autowired
	StudyService studyService;
	
	@RequestMapping(value="/calendar", method=RequestMethod.GET)
	public String calendarGet() {
		studyService.getCalendar();
		return "study/calendar/calendar";
	}
	
	@RequestMapping(value="/woo", method=RequestMethod.GET)
	public String wooGet() {
		return "study/woo/woo";
	}
	
	@RequestMapping(value="/fileUpload", method=RequestMethod.GET)
	public String fileUploadGet() {
		return "study/fileupload/fileUpload";
	}
	
	@RequestMapping(value="/fileUpload", method=RequestMethod.POST)
	public String fileUploadPost(MultipartFile mFile, String file) {
		int res = studyService.fileUpload(mFile, file);
		if(res == 1) {
			msgFlag = "fileUploadOk";
		}
		else {
			msgFlag = "fileUploadNo";
		}
		
		return "redirect:/msg/" + msgFlag;
	}
}
