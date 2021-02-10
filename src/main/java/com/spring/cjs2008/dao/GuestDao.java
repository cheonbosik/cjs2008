package com.spring.cjs2008.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2008.vo.GuestVo;

public interface GuestDao {

	public void gInput(@Param("vo") GuestVo vo);

	public List<GuestVo> getGuestList(@Param("startNo") int startNo, @Param("pageSize") int pageSize);

	public void gUpdate(@Param("vo") GuestVo vo);

	public void gDelete(@Param("idx") int idx);

	public GuestVo getUpdate(@Param("idx") int idx);

	public int totRecCnt();

}
