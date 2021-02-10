package com.spring.cjs2008.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cjs2008.dao.GuestDao;
import com.spring.cjs2008.vo.GuestVo;

@Service
public class GuestServiceImp implements GuestService {
	@Autowired
	GuestDao guestDao;

	@Override
	public void gInput(GuestVo vo) {
		guestDao.gInput(vo);
	}

	@Override
	public List<GuestVo> getGuestList(int startNo, int pageSize) {
		return guestDao.getGuestList(startNo, pageSize);
	}

	@Override
	public void gUpdate(GuestVo vo) {
		guestDao.gUpdate(vo);
	}

	@Override
	public void gDelete(int idx) {
		guestDao.gDelete(idx);
	}

	@Override
	public GuestVo getUpdate(int idx) {
		return guestDao.getUpdate(idx);
	}

	@Override
	public int totRecCnt() {
		return guestDao.totRecCnt();
	}
}
