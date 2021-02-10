package com.spring.cjs2008.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2008.vo.MemberVo;

public interface AdminDao {

	public int totMemberRecCnt();

	public List<MemberVo> mList(@Param("startNo") int startNo, @Param("pageSize") int pageSize);

	public void levelCheck(@Param("mid") String mid, @Param("level") int level);

	public MemberVo mSearchList(@Param("mid") String mid);

	public void memberDelete(@Param("idx") int idx);

}
