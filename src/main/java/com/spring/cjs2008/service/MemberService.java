package com.spring.cjs2008.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.cjs2008.vo.MemberVo;

public interface MemberService {

	public int mInput(MultipartFile file, MemberVo vo);

	public MemberVo getIdCheck(String mid);

	public MemberVo getNickCheck(String nickname);

	public MemberVo getPwdSearch(String mid, String email);

	public void setPwdChange(String mid, String pwd);

	public List<MemberVo> getMidSearch(String name, String email);

	public void mUpdate(MemberVo vo, MultipartFile mFile);

	public void memberDelete(String mid);

}
