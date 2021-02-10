package com.spring.cjs2008.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2008.dao.PdsDao;
import com.spring.cjs2008.vo.PdsVo;

@Service
public class PdsServiceImp implements PdsService {
	@Autowired
	PdsDao pdsDao;

	@Override
	public List<PdsVo> getPList(int startNo, int pageSize, String part) {
		return pdsDao.getPList(startNo, pageSize, part);
	}

	@Override
	public void setPdsUpload(MultipartFile file, PdsVo vo) {
		try {
			String oFileName = file.getOriginalFilename();
			String preFileName = oFileName.substring(0,oFileName.lastIndexOf(".")); // 순수한 파일명
			String extFileName = oFileName.substring(oFileName.lastIndexOf(".")+1); // 확장자명
			
			// 서버에 저장되는 실제파일의 중복을 배제하기위해 파일명 뒤에 '년/월/일/시/분/초'을 추가해서 뒤에 확장자를 붙여서 저장한다.
			String saveFileName = saveFileName(preFileName, extFileName);
			
			// 실제 서버에 파일을 저장시킨다.(/resoruces/pds 폴더에 저장)
			writeFile(file, saveFileName);
			
			// DB에 자료를 올리기 위한 준비 및 자료 저장시키기
			vo.setFname(oFileName);
			vo.setRfname(saveFileName);
			vo.setFsize((int) file.getSize());
			pdsDao.setPdsUpload(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeFile(MultipartFile file, String saveFileName) throws IOException {
		byte[] data = file.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		//request.getRealPath("/resources/pds/");
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/pds/");
		
		FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
		fos.write(data);
		
		fos.close();
	}

	private String saveFileName(String preFileName, String extFileName) {
		String fileName = "";
		
		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += "_" + preFileName + "." + extFileName;
		
		return fileName;
	}
	
}
