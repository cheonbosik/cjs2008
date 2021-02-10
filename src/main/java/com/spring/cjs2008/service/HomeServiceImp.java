package com.spring.cjs2008.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.cjs2008.dao.HomeDao;
import com.spring.cjs2008.vo.NotifyVo;

@Service
public class HomeServiceImp implements HomeService {
	@Autowired
	HomeDao homeDao;

	@Override
	public List<NotifyVo> getNotifyPopup() {
		return homeDao.getNotifyPopup();
	}
}
