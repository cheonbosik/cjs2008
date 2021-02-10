package com.spring.cjs2008.service;

import java.util.List;

import com.spring.cjs2008.vo.GuestVo;

public interface GuestService {

	public void gInput(GuestVo vo);

	public List<GuestVo> getGuestList(int startNo, int pageSize);

	public void gUpdate(GuestVo vo);

	public void gDelete(int idx);

	public GuestVo getUpdate(int idx);

	public int totRecCnt();

}
