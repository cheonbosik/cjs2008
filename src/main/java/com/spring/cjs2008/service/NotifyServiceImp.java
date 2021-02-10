package com.spring.cjs2008.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cjs2008.dao.NotifyDao;
import com.spring.cjs2008.vo.NotifyVo;

@Service
public class NotifyServiceImp implements NotifyService {
	@Autowired
	NotifyDao notifyDao;

	@Override
	public void nInput(NotifyVo vo) {
		notifyDao.nInput(vo);
	}

	@Override
	public List<NotifyVo> getNotifyList() {
		return notifyDao.getNotifyList();
	}

	@Override
	public void setDelete(int idx) {
		notifyDao.setDelete(idx);
	}

	@Override
	public NotifyVo getNUpdate(int idx) {
		return notifyDao.getNUpdate(idx);
	}

	@Override
	public void setNUpdateOk(NotifyVo vo) {
		notifyDao.setNUpdateOk(vo);
	}

	@Override
	public int setpopupCheckUpdate(int idx, String popupSw) {
		notifyDao.setpopupCheckUpdate(idx, popupSw);
		return 1;
	}
	
}
