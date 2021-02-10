package com.spring.cjs2008.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2008.vo.MemberVo;

public interface MemberDao {

	public void mInput(@Param("vo") MemberVo vo);

	public MemberVo getIdCheck(@Param("mid") String mid);

	public MemberVo getNickCheck(@Param("nickname") String nickname);

	public MemberVo getPwdSearch(@Param("mid") String mid, @Param("email") String email);

	public void setPwdChange(@Param("mid") String mid, @Param("pwd") String pwd);

	public List<MemberVo> getMidSearch(@Param("name") String name, @Param("email") String email);

	public void mUpdate(@Param("vo") MemberVo vo);

	public void memberDelete(@Param("mid") String mid);

}
