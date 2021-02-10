package com.spring.cjs2008.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2008.dao.MemberDao;
import com.spring.cjs2008.vo.MemberVo;

@Service
public class MemberServiceImp implements MemberService {
	@Autowired
	MemberDao memberDao;

	@Override
	public int mInput(MultipartFile file, MemberVo vo) {
		//System.out.println("1.vo : " + vo);
		int res = 0;
		try {
			//if(vo.getPhoto() != null) {
			String oFileName = file.getOriginalFilename();
			System.out.println("file명 : " + oFileName);
			if(oFileName != "") {
				// 파일업로드 처리
				
				UUID uid = UUID.randomUUID(); // UUID객체를 통한 고유번호를 생성
				
				String saveFileName = uid + "_" + oFileName;
				
				writeFile(file, saveFileName);
				vo.setPhoto(saveFileName);
			}
			else {
				if(vo.getGender().equals("남자")) {
					vo.setPhoto("male.jpg");
				}
				else {
					vo.setPhoto("female.jpg");
				}
			}
			//System.out.println("2.vo : " + vo);
			memberDao.mInput(vo); // 파일이 업로드 성공시에 DB에 정보를 저장한다.
			res = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private void writeFile(MultipartFile mFile, String saveFileName) throws IOException {
		byte[] data = mFile.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/member/");
		
		FileOutputStream fos = new FileOutputStream(uploadPath + saveFileName);
		fos.write(data);
		
		fos.close();
	}

	@Override
	public MemberVo getIdCheck(String mid) {
		return memberDao.getIdCheck(mid);
	}

	@Override
	public MemberVo getNickCheck(String nickname) {
		return memberDao.getNickCheck(nickname);
	}

	@Override
	public MemberVo getPwdSearch(String mid, String email) {
		return memberDao.getPwdSearch(mid, email);
	}

	@Override
	public void setPwdChange(String mid, String pwd) {
		memberDao.setPwdChange(mid, pwd);
	}

	@Override
	public List<MemberVo> getMidSearch(String name, String email) {
		return memberDao.getMidSearch(name, email);
	}

	@Override
	public void mUpdate(MemberVo vo, MultipartFile mFile) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest(); // AOP에서 request객체를 사용(선언)하기위한 방법
			String directory = request.getSession().getServletContext().getRealPath("/resources/member/");
			
			// 파일변경시 파일 삭제 및 업로드 처리
			String oFileName = mFile.getOriginalFilename();
			
			System.out.println("vo.getPhoto :" + vo.getPhoto());     
			System.out.println("oFileName :" + oFileName);
			if(oFileName != "" && oFileName != null) {
				// 기존 파일 삭제하기
				if(!vo.getPhoto().equals("mail.jpg") && !vo.getPhoto().equals("femail.jpg")) {
					new File(directory + vo.getPhoto()).delete();  // 원본파일삭제
				}
				
			  UUID uid = UUID.randomUUID(); // UUID객체를 통한 고유번호를 생성
			
			  String saveFileName = uid + "_" + oFileName;
			
			  writeFile(mFile, saveFileName);
			  vo.setPhoto(saveFileName);
			}
			System.out.println("33.vo : " + vo);
			memberDao.mUpdate(vo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void memberDelete(String mid) {
		memberDao.memberDelete(mid);
	}


}
