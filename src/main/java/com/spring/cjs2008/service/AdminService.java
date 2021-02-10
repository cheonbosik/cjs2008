package com.spring.cjs2008.service;

import java.util.List;

import com.spring.cjs2008.vo.MemberVo;

public interface AdminService {

	public int totMemberRecCnt();

	public List<MemberVo> mList(int startNo, int pageSize);

	public void levelCheck(String mid, int level);

	public MemberVo mSearchList(String mid);

	public void memberDelete(int idx);

	public int imgDelete(String uploadPath);

}
