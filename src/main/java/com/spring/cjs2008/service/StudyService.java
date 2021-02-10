package com.spring.cjs2008.service;

import org.springframework.web.multipart.MultipartFile;

public interface StudyService {

	public void getCalendar();

	public int fileUpload(MultipartFile mFile, String file);

}
